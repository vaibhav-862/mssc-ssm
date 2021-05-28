package guru.springframework.msscssm.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING) //to tell hibernate to persist this field as an enumeration
	private PaymentState state;
	
	private BigDecimal amount;
}
