package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Testa o lucro gerado pela venda da ação.
 * 
 * 
 * Test 1) Gera lucro onde o acumulado de perda é igual a zero
 * Test 2) Gera lucro e imposto é gerado apos lucro - perda( > 0(zero)
 * Test 3) Imposto não é gerado porque lucro - perda = 0(zero)
 */
class ProfitHandlerTest {

    @DisplayName("Simula um ganho e o imposto será gerado apos o prejuizo ser deduzido do lucro.")
    @Test
    void testProfitWithLoss() {
        // Inicializa uma nova instância
        ProfitHandler profitHandler = new ProfitHandler();

        // Cria mocks para Transaction e StockTaxCalculator
        Transaction mockTransaction = new Transaction("sell", new BigDecimal("20.00"), 3000);
        StockTaxCalculator mockContext = new StockTaxCalculator();
        mockContext.setAccumulatedLoss(new BigDecimal("25000.00"));
        mockContext.setWeightedAverageCost(new BigDecimal("10.00"));

        // Valor esperado após o calculo do lucro
        Tax tax = new Tax(new BigDecimal("1000.0"));

        // Chamada do método calculate
        Tax resultTax = profitHandler.calculate(mockTransaction, mockContext);
        assertNotNull(resultTax);
        assertEquals(tax.getTax(), resultTax.getTax(), "Deve retornar o valor do imposto a ser pago.");
    }

    @DisplayName("Simula um ganho porém o custo total é menor que 20K e não gera imposto.")
    @Test
    void testProfitWithNoTax() {
        // Inicializa uma nova instância
        ProfitHandler profitHandler = new ProfitHandler();

        // Cria mocks para Transaction e StockTaxCalculator
        Transaction mockTransaction = new Transaction("sell", new BigDecimal("30.00"), 650);
        StockTaxCalculator mockContext = new StockTaxCalculator();
        mockContext.setAccumulatedLoss(new BigDecimal("0.00"));
        mockContext.setWeightedAverageCost(new BigDecimal("20.00"));

        // Valor esperado após o calculo do lucro
        Tax tax = new Tax(new BigDecimal("0.0"));

        // Chamada do método calculate
        Tax resultTax = profitHandler.calculate(mockTransaction, mockContext);
        assertNotNull(resultTax);
        assertEquals(tax.getTax(), resultTax.getTax(), "Deve retornar o valor do imposto a ser pago.");
    }

    @DisplayName("Simula um ganho porem existe uma perda para ser deduzida e gerando lucro e imposto 0.")
    @Test
    void testNoProfitNoLoss() {
        // Inicializa uma nova instância
        ProfitHandler profitHandler = new ProfitHandler();

        // Cria mocks para Transaction e StockTaxCalculator
        Transaction mockTransaction = new Transaction("sell", new BigDecimal("20.00"), 2000);
        StockTaxCalculator mockContext = new StockTaxCalculator();
        mockContext.setAccumulatedLoss(new BigDecimal("20000.00"));
        mockContext.setWeightedAverageCost(new BigDecimal("10.00"));

        // Valor esperado após o calculo do lucro
        Tax tax = new Tax(new BigDecimal("0.0"));

        // Chamada do método calculate
        Tax resultTax = profitHandler.calculate(mockTransaction, mockContext);
        assertNotNull(resultTax);
        assertEquals(tax.getTax(), resultTax.getTax(), "Deve retornar o valor do imposto a ser pago.");
        assertEquals(new BigDecimal("0.0"), mockContext.getAccumulatedLoss().setScale(1),
                "Não deve haver prejuizo.");
    }

    @DisplayName("Simula um ganho porem existe uma perda para ser deduzida não gerando gerando debito de 20K.")
    @Test
    void testProfitWithLossNoTax() {
        // Inicializa uma nova instância
        ProfitHandler profitHandler = new ProfitHandler();

        // Cria mocks para Transaction e StockTaxCalculator
        Transaction mockTransaction = new Transaction("sell", new BigDecimal("20.00"), 2000);
        StockTaxCalculator mockContext = new StockTaxCalculator();
        mockContext.setAccumulatedLoss(new BigDecimal("40000.00"));
        mockContext.setWeightedAverageCost(new BigDecimal("10.00"));

        // Valor esperado após o calculo do lucro
        Tax tax = new Tax(new BigDecimal("0.0"));

        // Chamada do método calculate
        Tax resultTax = profitHandler.calculate(mockTransaction, mockContext);
        assertNotNull(resultTax);
        assertEquals(tax.getTax(), resultTax.getTax(), "Deve retornar o valor do imposto a ser pago.");
        assertEquals(new BigDecimal("20000.0"), mockContext.getAccumulatedLoss().setScale(1),
                "Deve haver prejuizo.");
    }
}
