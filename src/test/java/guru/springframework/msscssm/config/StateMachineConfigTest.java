package guru.springframework.msscssm.config;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;

@SpringBootTest
public class StateMachineConfigTest {

	@Autowired
	StateMachineFactory<PaymentState, PaymentEvent> factory;
	
	@Test
	void testNewStateMachine() {
		StateMachine<PaymentState, PaymentEvent> sm = factory.getStateMachine(UUID.randomUUID());
		
		sm.start();
		
		System.out.println(sm.getState());
		
		sm.sendEvent(PaymentEvent.PRE_AUTHORIZE);
		
		System.out.println(sm.getState());
		
		sm.sendEvent(PaymentEvent.PRE_AUTH_APPROVED);
		
		System.out.println(sm.getState());

		sm.sendEvent(PaymentEvent.PRE_AUTH_DECLINED);
		
		System.out.println(sm.getState());
	}
}
