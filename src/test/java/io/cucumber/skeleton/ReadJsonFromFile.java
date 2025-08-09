package io.cucumber.skeleton;

import br.com.ganhocapital.dto.Transaction;
import br.com.ganhocapital.service.InputStrategy;
import br.com.ganhocapital.service.JConverter;
import br.com.ganhocapital.service.JsonConverter;
import br.com.ganhocapital.service.ReadFromFile;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadJsonFromFile {
    private List<List<Transaction>> transactions = null;
    private List<String> list = null;

    @Given("Read list from {string}")
    public void read_list_from(String filename) {
        InputStrategy consoleInputStrategy = new ReadFromFile();
        this.list = consoleInputStrategy.read(filename);
        System.out.println(System.getProperty("user.dir"));
        System.out.println(list.size());
    }

    @When("Transform data into list of transaction")
    public void transformDataIntoListOfTransaction() {
        JConverter jsonConverter = new JsonConverter();
        transactions = jsonConverter.convertToListOfTransactions(list);
    }

    @Then("Should have a single transaction list")
    public void shouldHaveASingleTransactionList() {
        List<Transaction> trans = Arrays.asList(
                new Transaction("buy", new BigDecimal("10.0"), 10000),
                new Transaction("sell", new BigDecimal("20.0"), 5000));
        assertEquals(1, transactions.size());
        assertEquals(trans, transactions.get(0));
    }

    @Then("Should have multi transaction list")
    public void shouldHaveMultiTransactionList() {
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
