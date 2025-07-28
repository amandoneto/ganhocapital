/**
 * 
 */
package br.com.ganhocapital.dto;

import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que encapsula os dados da transação
 *
 */
public class Transaction {

	private String operation;
    @JsonProperty("unit-cost") // Map "unit-cost" from JSON to unitCost field
    private BigDecimal unitCost;
    private int quantity;

    // Default constructor is required by Jackson
    public Transaction() {
    }

    
	public Transaction(String operation, BigDecimal unitCost, int quantity) {
		super();
		this.operation = operation;
		this.unitCost = unitCost;
		this.quantity = quantity;
	}


	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(operation, quantity, unitCost);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(operation, other.operation) && quantity == other.quantity
				&& Objects.equals(unitCost, other.unitCost);
	}


	@Override
	public String toString() {
		return "Transaction [operation=" + operation + ", unitCost=" + unitCost + ", quantity=" + quantity + "]";
	}
	
}
