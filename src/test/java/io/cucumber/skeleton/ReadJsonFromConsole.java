package io.cucumber.skeleton;

import br.com.ganhocapital.dto.Transaction;
import br.com.ganhocapital.service.InputStrategy;
import br.com.ganhocapital.service.JConverter;
import br.com.ganhocapital.service.JsonConverter;
import br.com.ganhocapital.service.ReadFromConsole;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadJsonFromConsole {
    List<List<Transaction>> transactions = null;
    private List<String> list = null;

    @Given("Read list from console:")
    public void read_list_from_console(String docString) {
        InputStrategy consoleInputStrategy = new ReadFromConsole();
        this.list = consoleInputStrategy.read(docString);
    }
    @When("Parsing the transaction")
    public void parsing_the_transaction() {
        JConverter jsonConverter = new JsonConverter();
        transactions = jsonConverter.convertToListOfTransactions(list);
    }
    @Then("we should have a single transaction list")
    public void we_should_have_a_single_transaction_list() {
        List<Transaction> trans = Arrays.asList(
                new Transaction("buy", new BigDecimal("10.0"), 10000),
                new Transaction("sell", new BigDecimal("20.0"), 5000));
        assertEquals(1, transactions.size());
        assertEquals(trans, transactions.get(0));
    }

    @Then("we should have a several transaction list")
    public void we_should_have_a_several_transaction_list() {
        List<List<Transaction>> trans = Arrays.asList(
                Arrays.asList(
                    new Transaction("buy", new BigDecimal("10.0"), 10000),
                    new Transaction("sell", new BigDecimal("20.0"), 5000)),
                Arrays.asList(
                        new Transaction("buy", new BigDecimal("20.0"), 10000),
                        new Transaction("sell", new BigDecimal("10.0"), 5000))
        );
        assertEquals(2, transactions.size());
        assertEquals(trans.get(0), transactions.get(0));
        assertEquals(trans.get(1), transactions.get(1));
    }
}
