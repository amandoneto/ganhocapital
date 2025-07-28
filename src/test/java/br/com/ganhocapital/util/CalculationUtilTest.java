package br.com.ganhocapital.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CalculationUtilTest {

	/**
	 * Testa o valor weightedAverageCost > 0
	 */
	@Test
    void testCalculateWeightedAverageCost_weightedAverageCost_noZero() {
        // Initial state: 100 shares at an average cost of 10.00
        int totalQuantity = 100;
        // New transaction: Buy 50 shares at 12.00
        int quantity = 50;
        BigDecimal weightedAverageCost = new BigDecimal("10.00");
        BigDecimal unitCost = new BigDecimal("12.00");

        // Expected calculation:
        // ((100 * 10.00) + (50 * 12.00)) / (100 + 50)
        // (1000.00 + 600.00) / 150
        // 1600.00 / 150 = 10.666666666666666...
        // Rounded to 10 decimal places with HALF_EVEN: 10.6666666667
        BigDecimal expected = new BigDecimal("10.67");

        BigDecimal actual = CalculationUtil.calculateWeightedAverageCost(totalQuantity, quantity, weightedAverageCost, unitCost);
        
        // Use compareTo for BigDecimal equality, as equals() also checks scale.
        assertEquals(0, expected.compareTo(actual), "The weighted average cost should be correctly calculated.");
    }
	/**
	 * Testa o valor weightedAverageCost = 0
	 */
	@Test
    void testCalculateWeightedAverageCost_weightedAverageCost_zero() {
        // Initial state: 100 shares at an average cost of 10.00
        int totalQuantity = 100;
        // New transaction: Buy 50 shares at 12.00
        int quantity = 50;
        BigDecimal weightedAverageCost = new BigDecimal("0.00");
        BigDecimal unitCost = new BigDecimal("12.00");

        // Expected calculation:
        // ((100 * 0.00) + (50 * 12.00)) / (100 + 50)
        // (00.00 + 600.00) / 150
        // 1600.00 / 150 = 4.00
        // Rounded to 10 decimal places with HALF_EVEN: 4.00
        BigDecimal expected = new BigDecimal("4.00");

        BigDecimal actual = CalculationUtil.calculateWeightedAverageCost(totalQuantity, quantity, weightedAverageCost, unitCost);
        
        // Use compareTo for BigDecimal equality, as equals() also checks scale.
        assertEquals(0, expected.compareTo(actual), "The weighted average cost should be correctly calculated.");
    }
	
	/**
	 * Testa o calculo do custo da compra da ação.
	 * Quantidade X valor unitário
	 */
	@Test
	void testCalculateCost_() {
		BigDecimal expected = new BigDecimal("5000.00");
		BigDecimal actual = CalculationUtil.calculateCost(5, new BigDecimal("1000"));
		
		// Use compareTo for BigDecimal equality, as equals() also checks scale.
        assertEquals(0, expected.compareTo(actual), "The cost should be correctly calculated.");
	}
}
