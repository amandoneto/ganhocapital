/**
 * 
 */
package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Realiza o test case do StockTaxCalculator
 * Será testado uma lista e 2 listas simulando a transformação do
 * JSON em Transaction
 * 
 */
class StockTaxCalculatorTest {

	@DisplayName("Testa o pagamento do imposto para uma operação de compra/venda")
	@Test
	void testTaxPayment() {
		StockTaxCalculator stock = new StockTaxCalculator();
		List<Tax> actual = stock.calculateTaxes(getTransactions1());
		
		List<Tax> expected = Arrays.asList(new Tax(new BigDecimal("0.0")),new Tax(new BigDecimal("10000.0")),new Tax(new BigDecimal("0.0")));
		boolean ok = expected.equals(actual);
		assertEquals(expected.size(), actual.size(), "Deve conter uma lista com 3 items");
		assertTrue(ok, "Deve conter o mesmo conteudo da lista expected");
	}
	
	@DisplayName("Testa o pagamento do imposto para mais de uma lista operação de compra/venda")
	@Test
	void testMoreThanOneList() {
		StockTaxCalculator stock = null;
		List<List<Tax>> result = new ArrayList<List<Tax>>();		
		
		List<List<Transaction>> all = Arrays.asList(getTransactions1(),getTransactions2());
		
		for(List<Transaction> list : all) {
			stock = new StockTaxCalculator();
			List<Tax> taxes = stock.calculateTaxes(list);
			result.add(taxes);
		}
		
		List<Tax> expected1 = Arrays.asList(new Tax(new BigDecimal("0.0")),new Tax(new BigDecimal("10000.0")),new Tax(new BigDecimal("0.0")));
		List<Tax> expected2 = Arrays.asList(new Tax(new BigDecimal("0.0")),new Tax(new BigDecimal("0.0")),new Tax(new BigDecimal("0.0")));
		
		assertEquals(expected1.size(), result.get(0).size(), "Deve conter uma lista com 3 items");
		assertEquals(expected2.size(), result.get(1).size(), "Deve conter uma lista com 3 items");
		
		assertTrue(expected1.equals(result.get(0)), "Deve conter o mesmo conteudo da lista expected1");
		assertTrue(expected2.equals(result.get(1)), "Deve conter o mesmo conteudo da lista expected2");
		
	}
	
	
	private List<Transaction> getTransactions1(){
		return Arrays.asList(
				new Transaction("buy",new BigDecimal("10.00"), 10000),
				new Transaction("sell",new BigDecimal("20.00"), 5000),
				new Transaction("sell",new BigDecimal("5.00"), 5000));
	}
	
	private List<Transaction> getTransactions2(){
		return Arrays.asList(
				new Transaction("buy",new BigDecimal("10.00"), 10),
				new Transaction("sell",new BigDecimal("15.00"), 50),
				new Transaction("sell",new BigDecimal("15.00"), 50));
	}
}
