/**
 * 
 */
package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.ganhocapital.dto.Transaction;

/**
 * Testa os metdos que transforma um JSON em Transation
 * e Tax em String.
 *
 */
class JConverterTest {

	private JConverter jsonConverter;

    @BeforeEach
    void setUp() {
        jsonConverter = new JsonConverter();
    }

    @Test
    @DisplayName("Should convert a list of JSON strings to a list of lists of Transactions")
    void testConvertSingleListIntoListOfList() {
        // Arrange
        String jsonInput1 = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}," +
                			"{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]";
        
        List<String> jsonArr = jsonConverter.convertToJsonList(jsonInput1);

        Transaction expectedTx1 = new Transaction("buy", new BigDecimal("10.0"), 100);
        Transaction expectedTx2 = new Transaction("sell", new BigDecimal("15.0"), 50);

        List<List<Transaction>> expectedResult = Arrays.asList(
                Arrays.asList(expectedTx1, expectedTx2)
            );

        // Act
        List<List<Transaction>> actualResult = jsonConverter.convertToListOfTransactions(jsonArr);

        // Assert
        assertNotNull(actualResult);
        assertEquals(expectedResult.size(), actualResult.size(), "Deve conter apenas 1 lista");
        assertEquals(expectedResult.get(0), actualResult.get(0), "Ambas as listas devem conter a mesma informação");        
        
    }
    
    @Test
    @DisplayName("Should convert a list of JSON strings to a list of lists of Transactions")
    void testConvertmultiListIntoListOfList() {
        // Arrange
        String jsonInput1 = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}," +
                			"{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]" +
                			"[{\"operation\":\"buy\", \"unit-cost\":100.00, \"quantity\": 10}," +
                			"{\"operation\":\"sell\", \"unit-cost\":45.00, \"quantity\": 8}]";
        
        List<String> jsonArr = jsonConverter.convertToJsonList(jsonInput1);

        Transaction expectedTx1 = new Transaction("buy", new BigDecimal("10.0"), 100);
        Transaction expectedTx2 = new Transaction("sell", new BigDecimal("15.0"), 50);
        Transaction expectedTx3 = new Transaction("buy", new BigDecimal("100.0"), 10);
        Transaction expectedTx4 = new Transaction("sell", new BigDecimal("45.0"), 8);

        List<List<Transaction>> expectedResult = Arrays.asList(
                Arrays.asList(expectedTx1, expectedTx2),
                Arrays.asList(expectedTx3, expectedTx4)
            );

        // Act
        List<List<Transaction>> actualResult = jsonConverter.convertToListOfTransactions(jsonArr);

        // Assert
        assertNotNull(actualResult);
        assertEquals(expectedResult.size(), actualResult.size(), "Deve conter apenas 1 lista");
        assertEquals(expectedResult.get(0), actualResult.get(0), "verifica lista 1 onde deve conter as mesmas informação");
        assertEquals(expectedResult.get(1), actualResult.get(1), "Verifica lista 2 onde deve conter as mesmas informação");      
        
    }
    
    @DisplayName("Testa a interface gerando mocks para retorna uma lista de String")
    @Test
    void testConvertToJsonListViaInterface() {
    	JConverter mockJsonConverter = mock(JsonConverter.class);
    	
    	String jsonInput1 = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}," +
    			"{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]";
    	
    	List<String> mockReturnData = Arrays.asList(
    			"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]"
        );
    	
    	when(mockJsonConverter.convertToJsonList(jsonInput1)).thenReturn(mockReturnData);    	
    	
    	List<String> actualResult = jsonConverter.convertToJsonList(jsonInput1);
    	
    	assertEquals(mockReturnData.size(), actualResult.size(), "Deve conter apenas 1 lista");
    	assertEquals(mockReturnData, actualResult);
    }
}
