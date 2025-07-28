/**
 * 
 */
package br.com.ganhocapital.util;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Classe calcula o preço médio ponderado de compra e o custo total da compra
 *
 */
public class CalculationUtil {
	private CalculationUtil() {}
	
	/**
	 * Calcula o custo total
	 * @param quantity	Quantidade
	 * @param unitCost	Valor Unitário
	 * @return	Valor Total
	 */
	public static final BigDecimal calculateCost(int quantity, BigDecimal unitCost) {
    	BigDecimal qty  = new BigDecimal(quantity);
    	return unitCost.multiply(qty);
    }
	
	/**
	 * Calcula o preço médio ponderado de compra
	 * @param totalQuantity Total quantidade comprada
	 * @param quantity	quantidade de compra ataual
	 * @param weightedAverageCost	preço médio ponderado
	 * @param unitCost	Custo unitário
	 * @return preço médio ponderado
	 */
	public static final BigDecimal calculateWeightedAverageCost(int totalQuantity, int quantity, BigDecimal weightedAverageCost, BigDecimal unitCost) {
    	
    	//quantidade-de-acoes-atual * media-ponderada-atual
    	BigDecimal cal1 = calculateCost(totalQuantity,weightedAverageCost);
    	
    	//quantidade-de-acoes-compradas * valor-de-compra
    	BigDecimal cal2 = calculateCost(quantity, unitCost);
    	
    	//quantidade-de-acoes-atual +	quantidade-de-acoes-compradas
    	BigDecimal sumQuantity = new BigDecimal(totalQuantity + quantity);
    	BigDecimal cal4 = cal1.add(cal2);
    	
    	// It's crucial to specify a RoundingMode and scale for BigDecimal division
        // to avoid ArithmeticException for non-terminating decimal expansions.
        // Using RoundingMode.HALF_EVEN and a scale of 10 for financial precision.
    	cal4 = cal4.divide(sumQuantity,2, RoundingMode.HALF_EVEN);
    	return cal4;
    	
    }
}
