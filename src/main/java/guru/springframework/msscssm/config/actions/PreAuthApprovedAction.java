package guru.springframework.msscssm.config.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;

@Component
public class PreAuthApprovedAction implements Action<PaymentState, PaymentEvent> {

	@Override
	public void execute(StateContext<PaymentState, PaymentEvent> context) {
		// TODO Auto-generated method stub
		System.out.println("Sending Notification of PreAuth Approved");
	}
}
