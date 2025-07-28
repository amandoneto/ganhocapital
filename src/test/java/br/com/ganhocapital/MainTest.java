/**
 * 
 */
package br.com.ganhocapital;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;
import br.com.ganhocapital.service.InputStrategy;
import br.com.ganhocapital.service.JConverter;

/**
 * Criar os test cases para a classe principal.
 * Essa classe que inicia a execução da aplicação.
 *
 */

@ExtendWith(MockitoExtension.class) //Inicia os mocks
class MainTest {

	@Mock
    private JConverter mockJConverter;
    @Mock
    private InputStrategy mockConsoleInputStrategy;
    @Mock
    private InputStrategy mockFileInputStrategy;

    //Simula o output do System.out
    private ByteArrayOutputStream outputStreamCaptor;
    
    //Simula a entrada de dados via System.in
    private InputStream testInputStream;
    private Main main;
    
    final String expectedPrompt = "Enter JSON objects (one per line) or provide the full filename that ends with .txt. Enter an empty line to finish.\n";
    
    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStreamCaptor);

        // Reset para stream vazia
        testInputStream = new ByteArrayInputStream("".getBytes());

        main = new Main(mockJConverter, testInputStream, printStream,
                        mockConsoleInputStrategy, mockFileInputStrategy);
    }
    
    @Test
    @DisplayName("Simula a leitura via console e processa as transações com sucesso.")
    void testReadInputFromConsoleAndProcess() {
        // Arrange
        String consoleInput = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}\n" +
                              "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]\n" +
                              "\n"; // Empty line to stop input

        testInputStream = new ByteArrayInputStream(consoleInput.getBytes());
        main = new Main(mockJConverter, testInputStream, new PrintStream(outputStreamCaptor),
                        mockConsoleInputStrategy, mockFileInputStrategy);


        List<String> rawJsonInputs = Arrays.asList(
                "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}",
                "{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}"
        );

        List<List<Transaction>> mockTransactions = Collections.singletonList(
                Arrays.asList(
                		new Transaction("buy", new BigDecimal("10.00"), 100), 
                		new Transaction("sell", new BigDecimal("15.00"), 50))
        		);

        //Tax taxResult = new Tax(new BigDecimal("0.0")); // Assuming a simple TaxResult
        List<Tax> mockTaxResults = Arrays.asList(new Tax(new BigDecimal("0.0")), new Tax(new BigDecimal("0.0")));
        String expectedJsonOutput = "[{\"tax\":\"0.00\"},{\"tax\":\"0.00\"}]";

        // Stubbing
        when(mockConsoleInputStrategy.read(anyString())).thenReturn(rawJsonInputs); // For console input
        when(mockJConverter.convertToListOfTransactions(rawJsonInputs)).thenReturn(mockTransactions);
        when(mockJConverter.writeTaxesToJson(mockTaxResults)).thenReturn(expectedJsonOutput);

        // Act
        main.run();

        // Assert
        verify(mockConsoleInputStrategy).read(anyString());
        verify(mockJConverter).convertToListOfTransactions(rawJsonInputs);
        verify(mockJConverter).writeTaxesToJson(mockTaxResults);

        // Além de conter o resultado também possui o prompt
        assertEquals(expectedPrompt + expectedJsonOutput + "\n", outputStreamCaptor.toString());

    }
    
    @Test
    @DisplayName("Simula a leitura via arquivo e processa as transações com sucesso.")
    void testReadInputFromFileAndProcess() {
        // Arrange
        String fileInputLine = "input.txt\n"; // Simulating user typing a filename
        testInputStream = new ByteArrayInputStream(fileInputLine.getBytes());
        main = new Main(mockJConverter, testInputStream, new PrintStream(outputStreamCaptor),
                        mockConsoleInputStrategy, mockFileInputStrategy);

        List<String> rawJsonInputsFromFile = Arrays.asList(
                "{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 2000}",
                "{\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\": 1800}"
        );

        List<List<Transaction>> mockTransactionsFromFile = Collections.singletonList(
        		Arrays.asList(
        				new Transaction("buy", new BigDecimal("20.00"), 2000), 
        				new Transaction("sell", new BigDecimal("30.00"), 1800))
        );

        List<Tax> mockFileTaxResults = Arrays.asList(new Tax(new BigDecimal("0.0")), new Tax(new BigDecimal("3600.0")));
        String expectedFileJsonOutput = "[{\"tax\":0.0},{\"tax\":3600.0}]";

        // Stubbing
        when(mockFileInputStrategy.read("input.txt")).thenReturn(rawJsonInputsFromFile); // For file input
        when(mockJConverter.convertToListOfTransactions(rawJsonInputsFromFile)).thenReturn(mockTransactionsFromFile);
        when(mockJConverter.writeTaxesToJson(mockFileTaxResults)).thenReturn(expectedFileJsonOutput);

        // Act
        main.run();

        // Assert
        verify(mockFileInputStrategy).read("input.txt"); // Verify file strategy was used
        verify(mockJConverter).convertToListOfTransactions(rawJsonInputsFromFile);
        verify(mockJConverter).writeTaxesToJson(mockFileTaxResults);

        //Além de conter o resultado também possui o prompt
        assertEquals(expectedPrompt + expectedFileJsonOutput + "\n", outputStreamCaptor.toString());
        
    }
    
    @Test
    @DisplayName("Should handle multiple sets of transactions")
    void testHandleMultipleTransactionSets() {
    	
        // Arrange
        String consoleInput = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100}\n," +
        		"{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}\n," +
        		"{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]\n" +
        		"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000}\n," +
        		"{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}\n," +
        		"{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]\n" +
        		"\n";

        testInputStream = new ByteArrayInputStream(consoleInput.getBytes());
        main = new Main(mockJConverter, testInputStream, new PrintStream(outputStreamCaptor),
                        mockConsoleInputStrategy, mockFileInputStrategy);

        List<String> rawJsonInputs = Arrays.asList(
            		"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 100},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50},{\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\": 50}]",
            		"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000},{\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\": 5000}]");

        
        List<List<Transaction>> mockTransactions = Arrays.asList(

    		Arrays.asList(
    				new Transaction("buy", new BigDecimal("10.00"), 100), 
    				new Transaction("sell", new BigDecimal("15.00"), 50),
    				new Transaction("sell", new BigDecimal("15.00"), 50)),
		
    		Arrays.asList(
    				new Transaction("buy", new BigDecimal("10.00"), 10000), 
    				new Transaction("sell", new BigDecimal("20.00"), 5000),
    				new Transaction("sell", new BigDecimal("5.00"), 5000))
        );

        List<Tax> mockTaxResults1 = Arrays.asList(new Tax(new BigDecimal("0.0")), new Tax(new BigDecimal("0.0")), new Tax(new BigDecimal("0.0")) );
        List<Tax> mockTaxResults2 = Arrays.asList(new Tax(new BigDecimal("0.0")), new Tax(new BigDecimal("10000.0")), new Tax(new BigDecimal("0.0")) );

        String expectedJsonOutput1 = "[{\"tax\":0.0},{\"tax\":0.0},{\"tax\":0.0}]";
        String expectedJsonOutput2 = "[{\"tax\":0.0},{\"tax\":10000.0},{\"tax\":0.0}]";


        // Stubbing
        when(mockConsoleInputStrategy.read(anyString())).thenReturn(rawJsonInputs); // Simulate concatenated input
        when(mockJConverter.convertToListOfTransactions(rawJsonInputs)).thenReturn(mockTransactions);
        when(mockJConverter.writeTaxesToJson(mockTaxResults1)).thenReturn(expectedJsonOutput1);
        when(mockJConverter.writeTaxesToJson(mockTaxResults2)).thenReturn(expectedJsonOutput2);

        // Act
        main.run();

        // Assert
        verify(mockJConverter).convertToListOfTransactions(rawJsonInputs);

        // Verify that writeTaxesToJson was called for each tax result
        verify(mockJConverter).writeTaxesToJson(mockTaxResults1);
        verify(mockJConverter).writeTaxesToJson(mockTaxResults2);

        //Além de conter o resultado também possui o prompt
        String expectedOutput = expectedPrompt + expectedJsonOutput1 + "\n" + expectedJsonOutput2 + "\n";
        assertEquals(expectedOutput, outputStreamCaptor.toString());
        System.out.println(outputStreamCaptor.toString());
    }
}
