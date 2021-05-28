package guru.springframework.msscssm.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableStateMachineFactory //specifies to spring about building state machine factory
@Configuration
//public class StateMachineConfig {
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
	
}
