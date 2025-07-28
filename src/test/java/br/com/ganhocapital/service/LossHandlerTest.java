/**
 * 
 */
package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Testa o prejuizo causado pela venda da ação.
 * Quando há prejuizo, nenhum imposto é gerado
 * Realizado dois testes
 * Test 1) Gera prejuizo e imposto não é gerado
 * Test 2) Gera lucro e imposto é gerado
 *
 */
class LossHandlerTest {

	@DisplayName("Simula uma perda onde o imposto não será gerado.")
	@Test
	void testLossCalculate() {
		//Inicializa uma nova instância de LossHandler
		LossHandler lossHandler = new LossHandler();
		
		//Cria mocks para Transaction e StockTaxCalculator
	    Transaction mockTransaction = mock(Transaction.class);
	    StockTaxCalculator mockContext = mock(StockTaxCalculator.class);
	    Tax tax = new Tax(new BigDecimal("0.0"));
	    
	    when(mockTransaction.getOperation()).thenReturn("sell");
        when(mockTransaction.getQuantity()).thenReturn(5);
        when(mockTransaction.getUnitCost()).thenReturn(new BigDecimal("10.00"));
        when(mockContext.getWeightedAverageCost()).thenReturn(new BigDecimal("10.50"));
        when(mockContext.getAccumulatedLoss()).thenReturn(new BigDecimal("5.00"));
        

	    
        // Chamada do método calculate
        Tax resultTax = lossHandler.calculate(mockTransaction, mockContext);
        assertNotNull(resultTax);
        assertEquals(tax.getTax(), resultTax.getTax(), "O imposto retornado para perda deve ser zero.");

        verify(mockTransaction, atLeastOnce()).getUnitCost();
        verify(mockTransaction, atLeastOnce()).getQuantity();
        verify(mockContext, atLeastOnce()).getWeightedAverageCost();
        verify(mockContext, atLeastOnce()).getAccumulatedLoss();
        verify(mockContext, times(1)).setAccumulatedLoss(any(BigDecimal.class));

	}
	
	@DisplayName("Simula um ganho e com isso o imposto será gerado.")
	@Test
	void testNoLossCalculate() {
		//Inicializa uma nova instância de LossHandler
		LossHandler lossHandler = new LossHandler();
		
		//Cria mocks para Transaction e StockTaxCalculator
	    Transaction mockTransaction = mock(Transaction.class);
	    StockTaxCalculator mockContext = mock(StockTaxCalculator.class);
	    
	    //Define os comportamento dos mocks
	    when(mockTransaction.getOperation()).thenReturn("sell");
        when(mockTransaction.getQuantity()).thenReturn(5);
        when(mockTransaction.getUnitCost()).thenReturn(new BigDecimal("10.00"));
        when(mockContext.getWeightedAverageCost()).thenReturn(new BigDecimal("8.00"));
        when(mockContext.getAccumulatedLoss()).thenReturn(new BigDecimal("5.00"));

	    
        // Chamada do método calculate
        Tax resultTax = lossHandler.calculate(mockTransaction, mockContext);
        assertNull(resultTax, "Quando null significa que houve ganho e o imposto será cobrado.");
        
        verify(mockTransaction, atLeastOnce()).getUnitCost();
        verify(mockContext, atLeastOnce()).getWeightedAverageCost();
	}
}
