package guru.springframework.msscssm.services;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.repository.PaymentRepository;

@SpringBootTest
public class PaymentServiceImplTest {

	@Autowired
	PaymentService paymentService;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	Payment payment;
	
	@BeforeEach
	void setUp() {
		payment = Payment.builder().amount(new BigDecimal(12.95)).build();
	}
	
	@Transactional
	@Test
	void preAuth() {
		Payment savedPayment = paymentService.newPayment(payment);
		
		System.out.println(savedPayment.getState());
		
		StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());
		
		Payment preAuthedPayment = paymentRepository.getOne(savedPayment.getId());
		
		System.out.println(sm.getState().getId());
		
		System.out.println(preAuthedPayment);
	}
}
