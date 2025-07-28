/**
 * 
 */
package br.com.ganhocapital.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Trata o possível lucro.
 * Caso haja lucro, o imposto deve ser calculado.
 * Caso haja prejuizo, nenhum imposto deve ser pago
 *
 */
public class ProfitHandler implements TransactionStrategy {
    private static final BigDecimal TAX_PERCENTAGE = new BigDecimal("0.20");

    @Override
    public Tax calculate(Transaction transaction, StockTaxCalculator context) {

        Tax tax = new Tax(new BigDecimal("0.0"));

        BigDecimal profit = transaction.getUnitCost()
                .subtract(context.getWeightedAverageCost())
                .multiply(new BigDecimal(transaction.getQuantity()));

        // Lucro
        if (profit.compareTo(BigDecimal.ZERO) > 0) {

            // Houve Lucro, mas abaixo do limite do imposto
            if (isProfitTaxable(transaction)) {
                // Houve Lucro e custo total da operação é superior a 20K.
                BigDecimal taxableProfit = subtractLossFromProfit(profit, context);
                context.setAccumulatedLoss(context.getAccumulatedLoss().subtract(profit).max(BigDecimal.ZERO));

                if (!isLoss(context)) {
                    tax = new Tax(taxableProfit.multiply(TAX_PERCENTAGE).setScale(1, RoundingMode.HALF_UP));
                }
            }
        }
        // Prejuizo
        else if (profit.compareTo(BigDecimal.ZERO) < 0) {
            context.setAccumulatedLoss(context.getAccumulatedLoss().add(profit.abs()));
        }

        return tax;
    }

    /**
     * Deduz possivel prejuizo do lucro
     * 
     * @param profit Valor do lucro
     * @return retorna o valor do lucro
     */
    private BigDecimal subtractLossFromProfit(BigDecimal profit, StockTaxCalculator context) {
        if (context.getAccumulatedLoss().compareTo(BigDecimal.ZERO) != 0) {
            return context.getAccumulatedLoss().subtract(profit).abs();
        }
        return profit;
    }

    /**
     * Verifica se o valor total da transação excede o limite do imposto
     * 
     * @param transaction Informação da transação
     * @return Informa se havera taxação
     */
    private boolean isProfitTaxable(Transaction transaction) {
        BigDecimal totalCostTransaction = transaction.getUnitCost().multiply(new BigDecimal(transaction.getQuantity()));
        return (totalCostTransaction.compareTo(SellOperationState.TAX_THRESHOLD) > 0);
    }

    /**
     * Verifica se ainda existe perda a ser deduzida do lucro
     * 
     * @param context
     * @return retorna verdadeiro se quando houver perda a ser deduzida
     */
    private boolean isLoss(StockTaxCalculator context) {
        return context.getAccumulatedLoss().compareTo(BigDecimal.ZERO) > 0;
    }
}
