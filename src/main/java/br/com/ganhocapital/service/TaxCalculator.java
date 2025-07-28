/**
 * 
 */
package br.com.ganhocapital.service;

import java.util.List;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Calcula o imposto, quando necessário, a ser pago.
 * Gera uma lista com todos os valores do imposto 
 * 
 */
public interface TaxCalculator {
	/**
	 * Calcula o valor do imposto
	 * @param transactions Lista de transações para calculo do imposto
	 * @return	Lista de valores do imposto
	 */
	List<Tax> calculateTaxes(List<Transaction> transactions);
}
