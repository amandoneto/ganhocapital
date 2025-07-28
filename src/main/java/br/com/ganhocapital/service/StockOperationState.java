/**
 * 
 */
package br.com.ganhocapital.service;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Usar o State Pattern para lidar com as operações de compra e venda das ações
 *
 */
public interface StockOperationState {

	Tax process(Transaction transaction, StockTaxCalculator context);
}
