package guru.springframework.msscssm.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableStateMachineFactory //specifies to spring about building state machine factory
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent>{

	@Override
		public void configure(StateMachineStateConfigurer<PaymentState, PaymentEvent> states) throws Exception {
			// TODO Auto-generated method stub
			states.withStates() //create state m/c with all states
				.initial(PaymentState.NEW) //initial state
				.states(EnumSet.allOf(PaymentState.class)) //all PaymentState states
				.end(PaymentState.AUTH) //termination state
				.end(PaymentState.PRE_AUTH_ERROR) //failure termination state
				.end(PaymentState.AUTH_ERROR); //failure termination state
		}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<PaymentState, PaymentEvent> transitions) throws Exception {
		// TODO Auto-generated method stub
		//state transition from source to target upon receiving the specified event
		transitions.withExternal().source(PaymentState.NEW).target(PaymentState.NEW).event(PaymentEvent.PRE_AUTHORIZE)
			.and()
			.withExternal().source(PaymentState.NEW).target(PaymentState.PRE_AUTH).event(PaymentEvent.PRE_AUTH_APPROVED)
			.and()
			.withExternal().source(PaymentState.NEW).target(PaymentState.PRE_AUTH_ERROR).event(PaymentEvent.PRE_AUTH_DECLINED);
	}
	
	@Override
	public void configure(StateMachineConfigurationConfigurer<PaymentState, PaymentEvent> config) throws Exception {
		// TODO Auto-generated method stub
		StateMachineListenerAdapter<PaymentState, PaymentEvent> adapter = new StateMachineListenerAdapter<PaymentState, PaymentEvent>() {
			@Override
			public void stateChanged(State<PaymentState, PaymentEvent> from, State<PaymentState, PaymentEvent> to) {
				// TODO Auto-generated method stub
				log.info(String.format("stateChanged(from: %s, to: %s)", from, to));
			}
		};
		
		config.withConfiguration()
				.listener(adapter);
	}
}
