/**
 * 
 */
package br.com.ganhocapital.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Representa a transação de venda de ação
 * Aqui é calculado o Imposto da operação, quando necessário
 *
 */
public class SellOperationState implements StockOperationState {

	public static final BigDecimal TAX_THRESHOLD = new BigDecimal("20000.00");

	@Override
	public Tax process(Transaction transaction, StockTaxCalculator context) {
		Tax tax = null;

		for (TransactionStrategy chain : getListTransactions()) {
			tax = chain.calculate(transaction, context);
			if (tax != null) {
				break;
			}
		}
		context.setTotalQuantity(context.getTotalQuantity() - transaction.getQuantity());
		return tax;
	}

	private List<TransactionStrategy> getListTransactions() {
		return Arrays.asList(
				new LossHandler(),
				new ProfitHandler(),
				new ExemptionHandler());
	}
}
