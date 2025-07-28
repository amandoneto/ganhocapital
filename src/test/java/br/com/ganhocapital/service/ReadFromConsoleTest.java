package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReadFromConsoleTest {

	private InputStrategy inputStrategy;

    @BeforeEach // This method runs before each test method
    void setUp() {
        // Initialize the concrete implementation you want to test
        inputStrategy = new ReadFromConsole();
    }
    
    @Test
    @DisplayName("Deve criar com sucesso duas listas distinda de texto no formato JSON")
    void shouldExtractMultipleJsonArrayBlocks() {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}][{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 4000},{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]";
        List<String> expected = Arrays.asList(
        	"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]",
        	"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 4000},{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]"
        );
        List<String> actual = inputStrategy.read(input);
        assertEquals(expected.size(), actual.size(), "Deve conter 2 listas");
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual), "Listas devem ser igual a informada");
    }

    @Test
    @DisplayName("Deve criar com sucesso uma unica lista de texto no formato JSON")
    void shouldExtractSingleJsonArrayBlock() {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 4000},{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]";
        List<String> expected = Arrays.asList(
        		"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 4000},{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]"
        );
        List<String> actual = inputStrategy.read(input);
        assertEquals(expected.size(), actual.size(), "Deve conter apenas uma lista");
        assertEquals(expected, actual, "Lista de ser igual a informanda");
    }

}
