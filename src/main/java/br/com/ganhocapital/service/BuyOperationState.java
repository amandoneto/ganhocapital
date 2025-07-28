/**
 * 
 */
package br.com.ganhocapital.service;

import java.math.BigDecimal;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;
import br.com.ganhocapital.util.CalculationUtil;

/**
 * 
 *
 */
public class BuyOperationState implements StockOperationState {

	@Override
	public Tax process(Transaction transaction, StockTaxCalculator context) {
		BigDecimal currentWeightedAverageCost = context.getWeightedAverageCost();
        int currentTotalQuantity = context.getTotalQuantity();

        BigDecimal newWeightedAverageCost = CalculationUtil.calculateWeightedAverageCost(
            currentTotalQuantity,
            transaction.getQuantity(),
            currentWeightedAverageCost,
            transaction.getUnitCost()
        );
        context.setWeightedAverageCost(newWeightedAverageCost);
        context.setTotalQuantity(currentTotalQuantity + transaction.getQuantity());
        return new Tax(new BigDecimal("0.0")); // No tax on buy operations
	}

}
