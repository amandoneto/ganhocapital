package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * 
 * Testa a classe StockTaxCalculator para calcular o valor do imposto.
 * 
 *
 */
class TaxCalculatorTest {

	private StockTaxCalculator taxCalculator;

    @BeforeEach
    void setUp() {
        taxCalculator = new StockTaxCalculator(); // Initialize the concrete implementation for each test
    }

    @Test
    @DisplayName("Testa o lucro menor ou igual 20K e não gerando imposto")
    void testCalculateTaxProfitLessEqualThan20k() {
        // Arrange
        List<Transaction> transactions = Arrays.asList(
            new Transaction("buy", new BigDecimal("10.00"), 1000),   
            new Transaction("sell", new BigDecimal("30.00"), 500),  
            new Transaction("sell", new BigDecimal("30.00"), 300)
        );
        
        // Expected tax result
        List<Tax> expectedTaxes = Arrays.asList(
        		new Tax(new BigDecimal("0.0")),
        		new Tax(new BigDecimal("0.0")),
        		new Tax(new BigDecimal("0.0"))
        );

        // Act
        List<Tax> actualTaxes = taxCalculator.calculateTaxes(transactions);

        // Assert
        assertEquals(expectedTaxes.size(), actualTaxes.size(), "Deve conter uma lista com 3 items");
        assertEquals(expectedTaxes, actualTaxes, "As duas listas devem ser iguals");
        
    }
    
    @Test
    @DisplayName("Testa o lucro maior que 20K e gerando imposto")
    void testCalculateTaxProfitGreaterThan20k() {
        // Arrange
        List<Transaction> transactions = Arrays.asList(
            new Transaction("buy", new BigDecimal("10.00"), 10000),   
            new Transaction("sell", new BigDecimal("30.00"), 5000),  
            new Transaction("sell", new BigDecimal("31.20"), 3000)
        );
        
        // Expected tax result
        List<Tax> expectedTaxes = Arrays.asList(
        		new Tax(new BigDecimal("0.0")),
        		new Tax(new BigDecimal("20000.0")),
        		new Tax(new BigDecimal("12720.0"))
        );

        // Act
        List<Tax> actualTaxes = taxCalculator.calculateTaxes(transactions);

        // Assert
        assertEquals(expectedTaxes.size(), actualTaxes.size(), "Deve conter uma lista com 3 items");
        assertEquals(expectedTaxes, actualTaxes, "As duas listas devem ser iguals");
        
    }
}
