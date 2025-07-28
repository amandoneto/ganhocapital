package br.com.ganhocapital.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * 
 * Implementa como o imposto será calculado apos receber uma lista de
 * transações.
 * Compra de ação não gera imposto.
 *
 */
public class StockTaxCalculator implements TaxCalculator {

	private BigDecimal weightedAverageCost = new BigDecimal("0.0");
	private BigDecimal accumulatedLoss = new BigDecimal("0.0");
	private List<Tax> taxesPaid = new ArrayList<>();
	private int totalQuantity = 0;

	// Objeto de estado
	private final StockOperationState buyState = new BuyOperationState();
	private final StockOperationState sellState = new SellOperationState();

	@Override
	public List<Tax> calculateTaxes(List<Transaction> transactions) {
		for (Transaction transaction : transactions) {
			Tax currentTax = null;

			if (transaction.getOperation().equals("buy")) {
				currentTax = buyState.process(transaction, this);

			} else if (transaction.getOperation().equals("sell")) {
				currentTax = sellState.process(transaction, this);

			}
			taxesPaid.add(currentTax);
		}
		return taxesPaid;
	}

	/**
	 * @return the weightedAverageCost
	 */
	public BigDecimal getWeightedAverageCost() {
		return weightedAverageCost;
	}

	/**
	 * @param weightedAverageCost the weightedAverageCost to set
	 */
	public void setWeightedAverageCost(BigDecimal weightedAverageCost) {
		this.weightedAverageCost = weightedAverageCost;
	}

	/**
	 * @return the accumulatedLoss
	 */
	public BigDecimal getAccumulatedLoss() {
		return accumulatedLoss;
	}

	/**
	 * @param accumulatedLoss the accumulatedLoss to set
	 */
	public void setAccumulatedLoss(BigDecimal accumulatedLoss) {
		this.accumulatedLoss = accumulatedLoss;
	}

	/**
	 * @return the totalQuantity
	 */
	public int getTotalQuantity() {
		return totalQuantity;
	}

	/**
	 * @param totalQuantity the totalQuantity to set
	 */
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
}
