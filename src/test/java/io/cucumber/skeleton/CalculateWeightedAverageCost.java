package io.cucumber.skeleton;

import br.com.ganhocapital.util.CalculationUtil;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateWeightedAverageCost {
    private List<AverageCostTestData> data = null;
    private List<BigDecimal> actualResults = new ArrayList<>();

    public static class AverageCostTestData{
        public Integer totalQuantity = 0;
        public Integer quantity = 0;
        public BigDecimal unitCost = null;
        public BigDecimal weightedAverageCost = null;
        public BigDecimal expectedResult = null;

        public AverageCostTestData(Integer totalQuantity, Integer quantity, BigDecimal unitCost, BigDecimal weightedAverageCost, BigDecimal expectedResult) {
            this.totalQuantity = totalQuantity;
            this.quantity = quantity;
            this.unitCost = unitCost;
            this.weightedAverageCost = weightedAverageCost;
            this.expectedResult = expectedResult;
        }
    }

    @DataTableType
    public AverageCostTestData defineAverageCostTestData(Map<String,String> entry){
        return new AverageCostTestData(
                Integer.parseInt(entry.get("totalQuantity")),
                Integer.parseInt(entry.get("quantity")),
                new BigDecimal(entry.get("unitCost")),
                new BigDecimal(entry.get("weightedAverageCost")),
                new BigDecimal(entry.get("expectedResult"))
        );
    }

    @Given("the following data")
    public void the_following_data(List<AverageCostTestData> data) {
        //data.forEach( e -> System.out.println(e.unitCost));
        this.data = data;
    }

    @When("average cost is calculated")
    public void average_cost_is_calculated() {

        for( AverageCostTestData testData: data) {
            BigDecimal actual = CalculationUtil.calculateWeightedAverageCost(
                    testData.totalQuantity,
                    testData.quantity,
                    testData.weightedAverageCost,
                    testData.unitCost);

            actualResults.add(actual);
            System.out.println("weightedAverageCost: " + actual);
        }
    }

    @Then("Average cost should return a value")
    public void average_cost_should_return_a_value() {

        for(int i = 0; i<data.size(); i++){
            assertEquals(data.get(i).expectedResult, actualResults.get(i));
        }
    }
}
