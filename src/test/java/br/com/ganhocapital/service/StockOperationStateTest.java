/**
 * 
 */
package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
 * Criar testes usando o Mockito para simular uma operação
 * de compra e venda de ações e pahamento de imposto.
 * 
 *
 */
class StockOperationStateTest {

	@DisplayName("Testando StockOperationState via mock para simular a cobrança de imposto")
	@Test
    void testProcessWithMockitoMock() {
        // Crie o mock da interface
        StockOperationState mockState = mock(StockOperationState.class);
        
        // Defina o comportamento do mock (stubbing)
        // Quando process for chamado com qualquer Transaction e StockTaxCalculator,
        // Simulando o pagamento de imposto.
        when(mockState.process(any(Transaction.class), any(StockTaxCalculator.class)))
            .thenReturn(new Tax( new BigDecimal("15.00")));

        // Objetos de suporte para o teste
        Transaction someTransaction = new Transaction();
        StockTaxCalculator someTaxCalculator = new StockTaxCalculator();

        // Chame o método process do seu mock
        Tax resultTax = mockState.process(someTransaction, someTaxCalculator);

        // Verifique o comportamento
        assertNotNull(resultTax);
        assertEquals(0, resultTax.getTax().compareTo(new BigDecimal("15.00")));
        
        // Verifique se o método process foi chamado pelo menos uma vez
        verify(mockState, atLeastOnce()).process(any(Transaction.class), any(StockTaxCalculator.class));
    }
	
	@DisplayName("Testando StockOperationState via mock para simular a não cobrança de imposto")
	@Test
    void testLoss() {
        // Crie o mock da interface
        StockOperationState mockState = mock(StockOperationState.class);
        
        // Defina o comportamento do mock (stubbing)
        // Quando process for chamado com qualquer Transaction e StockTaxCalculator,
        // Simulando o não pagamento de imposto.
        when(mockState.process(any(Transaction.class), any(StockTaxCalculator.class)))
            .thenReturn(new Tax( new BigDecimal("0.0")));

        // Objetos de suporte para o teste
        Transaction someTransaction = new Transaction();
        StockTaxCalculator someTaxCalculator = new StockTaxCalculator();

        // Chame o método process do seu mock
        Tax resultTax = mockState.process(someTransaction, someTaxCalculator);

        // Verifique o comportamento
        assertNotNull(resultTax);
        assertEquals(0, resultTax.getTax().compareTo(new BigDecimal("0.0")));
        
        // Verifique se o método process foi chamado pelo menos uma vez
        verify(mockState, atLeastOnce()).process(any(Transaction.class), any(StockTaxCalculator.class));
        
    }
}
