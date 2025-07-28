/**
 * 
 */
package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Testa o lucro abaixo de 20000.00 onde não deve ser gerado nehum imposto
 * 
 * 
 * Test 1) Gera lucro onde o acumulado de perda é igual a zero
 * Test 2) Gera lucro e imposto é gerado apos lucro - perda( > 0(zero)
 * Test 3) Imposto não é gerado porque lucro - perda = 0(zero)
 */
class ExemptionHandlerTest {

	@DisplayName("Simula um ganho igual que 20000.00 onde não será cobrado imposto")
	@Test
	void testExemptionWhenGainEquals20K() {
		//Inicializa uma nova instância
		ExemptionHandler exemptionHandler = new ExemptionHandler();
		
		//Cria mocks para Transaction e StockTaxCalculator
	    Transaction mockTransaction = mock(Transaction.class);
	    StockTaxCalculator mockContext = mock(StockTaxCalculator.class);
	    
	    //Valor esperado após o calculo
	    Tax tax = new Tax(new BigDecimal("0.0"));
	    
        when(mockTransaction.getQuantity()).thenReturn(20);
        when(mockTransaction.getUnitCost()).thenReturn(new BigDecimal("1000.00"));
        when(mockContext.getWeightedAverageCost()).thenReturn(new BigDecimal("1000.00"));
	    
        // Chamada do método calculate
        Tax resultTax = exemptionHandler.calculate(mockTransaction, mockContext);
        assertNotNull(resultTax);
        assertEquals(tax.getTax(), resultTax.getTax(), "Não gera importo porque lucro = 20000.00.");

        verify(mockTransaction, atLeastOnce()).getUnitCost();
        verify(mockTransaction, atLeastOnce()).getQuantity();
	}
	
	@DisplayName("Simula um ganho menor que 20000.00 onde não será cobrado imposto")
	@Test
	void testExemptionWhenGainLessThan20K() {
		//Inicializa uma nova instância
		ExemptionHandler exemptionHandler = new ExemptionHandler();
		
		//Cria mocks para Transaction e StockTaxCalculator
	    Transaction mockTransaction = mock(Transaction.class);
	    StockTaxCalculator mockContext = mock(StockTaxCalculator.class);
	    
	    //Valor esperado após o calculo
	    Tax tax = new Tax(new BigDecimal("0.0"));
	    
        when(mockTransaction.getQuantity()).thenReturn(15);
        when(mockTransaction.getUnitCost()).thenReturn(new BigDecimal("1000.00"));
        when(mockContext.getWeightedAverageCost()).thenReturn(new BigDecimal("1000.00"));
	    
        // Chamada do método calculate
        Tax resultTax = exemptionHandler.calculate(mockTransaction, mockContext);
        assertNotNull(resultTax);
        assertEquals(tax.getTax(), resultTax.getTax(), "Não gera importo porque lucro < 20000.00.");

        verify(mockTransaction, atLeastOnce()).getUnitCost();
        verify(mockTransaction, atLeastOnce()).getQuantity();
	}
	
	@DisplayName("Simula um ganho maior que 20000.00 onde o imposto será gerado")
	@Test
	void testExemptionWhenGainGreaterThan20K() {
		//Inicializa uma nova instância
		ExemptionHandler exemptionHandler = new ExemptionHandler();
		
		//Cria mocks para Transaction e StockTaxCalculator
	    Transaction mockTransaction = mock(Transaction.class);
	    StockTaxCalculator mockContext = mock(StockTaxCalculator.class);
	    
        when(mockTransaction.getQuantity()).thenReturn(15);
        when(mockTransaction.getUnitCost()).thenReturn(new BigDecimal("10000.00"));
        when(mockContext.getWeightedAverageCost()).thenReturn(new BigDecimal("1000.00"));
	    
        // Chamada do método calculate
        Tax resultTax = exemptionHandler.calculate(mockTransaction, mockContext);
        assertNull(resultTax);

	}
}
