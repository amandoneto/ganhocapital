/**
 * 
 */
package br.com.ganhocapital.service;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * A implementação dessa interface vai confirmar o resultado da venda de ações.
 * Se houve perda ou lucro.
 * Quando houver lucro o imposto deverá ser pago
 *
 */
public interface TransactionStrategy {

	Tax calculate(Transaction transaction, StockTaxCalculator context);
}
