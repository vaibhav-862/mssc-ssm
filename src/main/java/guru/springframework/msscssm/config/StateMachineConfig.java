package guru.springframework.msscssm.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@EnableStateMachineFactory //specifies to spring about building state machine factory
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent>{

	//Spring intelligently auto-wires by bean name as there are multiple bean of the same type
	//if we change the name then we may have failure in Spring Bean injection
	private final Guard<PaymentState, PaymentEvent> paymentIdGuard;
	private final Action<PaymentState, PaymentEvent> preAuthAction;
	private final Action<PaymentState, PaymentEvent> preAuthApprovedAction;
	private final Action<PaymentState, PaymentEvent> preAuthDeclinedAction;
	private final Action<PaymentState, PaymentEvent> authAction;
	private final Action<PaymentState, PaymentEvent> authApprovedAction;
	private final Action<PaymentState, PaymentEvent> authDeclinedAction;
	
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
		transitions.withExternal().source(PaymentState.NEW).target(PaymentState.NEW).event(PaymentEvent.PRE_AUTHORIZE).action(preAuthAction).guard(paymentIdGuard)
			.and()
			.withExternal().source(PaymentState.NEW).target(PaymentState.PRE_AUTH).event(PaymentEvent.PRE_AUTH_APPROVED).action(preAuthApprovedAction)
			.and()
			.withExternal().source(PaymentState.NEW).target(PaymentState.PRE_AUTH_ERROR).event(PaymentEvent.PRE_AUTH_DECLINED).action(preAuthDeclinedAction)
			.and()
			.withExternal().source(PaymentState.PRE_AUTH).target(PaymentState.PRE_AUTH).event(PaymentEvent.AUTHORIZE).action(authAction)
			.and()
			.withExternal().source(PaymentState.PRE_AUTH).target(PaymentState.AUTH).event(PaymentEvent.AUTH_APPROVED).action(authApprovedAction)
			.and()
			.withExternal().source(PaymentState.PRE_AUTH).target(PaymentState.AUTH_ERROR).event(PaymentEvent.AUTH_DECLINED).action(authDeclinedAction);
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
