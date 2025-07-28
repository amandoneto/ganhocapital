/**
 * 
 */
package br.com.ganhocapital.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Classe que contem o valor do imposto a ser pago
 *
 */
public class Tax {
	private BigDecimal tax = null;

	public Tax(BigDecimal tax) {
		this.tax = tax;
		
	}
	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	@Override
	public int hashCode() {
		return Objects.hash(tax);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tax other = (Tax) obj;
		return Objects.equals(tax, other.tax);
	}
	
	
}
