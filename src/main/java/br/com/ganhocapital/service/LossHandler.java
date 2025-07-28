/**
 * 
 */
package br.com.ganhocapital.service;

import java.math.BigDecimal;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Prejuízos acontecem quando você vende ações a um valor menor do que o preço
 * médio ponderado de compra.
 * Neste caso, nenhum imposto deve ser pago e você deve subtrair o prejuízo dos
 * lucros
 * seguintes, antes de calcular o imposto
 *
 */
public class LossHandler implements TransactionStrategy {

	@Override
	public Tax calculate(Transaction transaction, StockTaxCalculator context) {
		if (transaction.getUnitCost().compareTo(context.getWeightedAverageCost()) <= 0) {

			BigDecimal profit = context.getWeightedAverageCost()
					.subtract(transaction.getUnitCost())
					.multiply(new BigDecimal(transaction.getQuantity()));

			context.setAccumulatedLoss(context.getAccumulatedLoss().subtract(profit).abs());
			return new Tax(new BigDecimal("0.0"));
		}

		return null;
	}

}
