/**
 * 
 */
package br.com.ganhocapital.service;

import java.math.BigDecimal;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;
import br.com.ganhocapital.util.CalculationUtil;

/**
 * Trata o cenario de não pagar imposto.
 * Você não paga nenhum imposto e não deve deduzir o lucro obtido dos prejuízos
 * acumulados se o valor
 * total da operação (custo unitário da ação x quantidade) for menor ou igual a
 * R$ 20000,00.
 */
public class ExemptionHandler implements TransactionStrategy {

	@Override
	public Tax calculate(Transaction transaction, StockTaxCalculator context) {

		BigDecimal totalOperationValue = CalculationUtil.calculateCost(transaction.getQuantity(),
				transaction.getUnitCost());
		if (totalOperationValue.compareTo(SellOperationState.TAX_THRESHOLD) <= 0) {
			return new Tax(new BigDecimal("0.0"));

		}
		return null;
	}
}
