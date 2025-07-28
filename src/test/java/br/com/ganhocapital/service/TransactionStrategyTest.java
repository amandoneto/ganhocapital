/**
 * 
 */
package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
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
 * Testa as diferentes estrategias de transações que podem gerar
 * lucro ou prejuizo, e consequentemente gerando imposto ou não.
 * 
 * O Rertorno é o valor do imposto >= 0.0
 *
 */
class TransactionStrategyTest {

	@DisplayName("Teste a simulação de uma venda com lucro e gerando imposto")
	@Test
    void shouldCalculateTaxForProfitableSellTransaction() {
		
		// Criar um mock para TransactionStrategy e StockTaxCalculator
        TransactionStrategy mockStrategy = mock(TransactionStrategy.class);
        StockTaxCalculator mockContext = mock(StockTaxCalculator.class);
        
        // Simula a venda da ação
        Transaction specificSellTransaction = new Transaction();
        
        when(mockStrategy.calculate(eq(specificSellTransaction), eq(mockContext)))
        .thenReturn(new Tax(new BigDecimal("300.00")));

        // Chamar o método a ser testado (neste caso, o método do nosso mock)
        Tax resultTax = mockStrategy.calculate(specificSellTransaction, mockContext);
        
        assertNotNull(resultTax);
        assertEquals(0, new BigDecimal("300.00").compareTo(resultTax.getTax()));
        
        // Verificar as interações (opcional, mas bom para garantir que o método foi chamado)
        verify(mockStrategy).calculate(eq(specificSellTransaction), eq(mockContext));
        
        verify(mockStrategy, atLeastOnce()).calculate(specificSellTransaction, mockContext);
	}
	
	@DisplayName("Testa a simulação de uma venda com prejuizo e sem pagamento de imposto")
	@Test
    void shouldCalculateTaxForLossSellTransaction() {
		
		// Criar um mock para TransactionStrategy e StockTaxCalculator
        TransactionStrategy mockStrategy = mock(TransactionStrategy.class);
        StockTaxCalculator mockContext = mock(StockTaxCalculator.class);
        
        // Simula a venda da ação
        Transaction specificSellTransaction = new Transaction();
        
        when(mockStrategy.calculate(eq(specificSellTransaction), eq(mockContext)))
        .thenReturn(new Tax(new BigDecimal("0.0")));

        // Chamar o método a ser testado (neste caso, o método do nosso mock)
        Tax resultTax = mockStrategy.calculate(specificSellTransaction, mockContext);
        
        assertNotNull(resultTax);
        assertEquals(0, new BigDecimal("0.0").compareTo(resultTax.getTax()));
        
        // Verificar as interações para saber se o metodo foi chamando
        verify(mockStrategy).calculate(eq(specificSellTransaction), eq(mockContext));        
        verify(mockStrategy, atLeastOnce()).calculate(specificSellTransaction, mockContext);
	}
}
