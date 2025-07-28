package br.com.ganhocapital.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReadFromFileTest {

	private InputStrategy inputStrategy;

	// JUnit 5 will create a temporary directory for each test method
    // and clean it up afterwards.
    @TempDir
    Path tempDir; // Injected by JUnit 5
    
    @BeforeEach // This method runs before each test method
    void setUp() {
        // Initialize the concrete implementation you want to test
        inputStrategy = new ReadFromFile();
    }
    
    
    @Test
    @DisplayName("Deve criar com sucesso duas listas distintas de texto lidos de um arquivo no formato JSON")
    void shouldExtractMultipleJsonArrayBlocksFromFile() {
        
    	Path tempFile = tempDir.resolve("test_input.txt");
    	List<String> fileData = getDataToSaveInFile();
    	
    	// Salva o dado no arquivo temporario
        try {
			Files.write(tempFile, fileData);
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Temporary test file created at: " + tempFile.toAbsolutePath().toString());
        
        List<String> expected = getList();
        List<String> actual = inputStrategy.read(tempFile.toAbsolutePath().toString());
        assertEquals(expected.size(), actual.size(), "Deve conter 2 listas");
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual), "Listas devem ser igual a informada no arquivo");
    }
    
    @Test
    @DisplayName("Deve criar com sucesso duas listas distintas de texto lidos de um arquivo no formato JSON")
    void shouldExtractSingleJsonArrayBlocksFromFile() {
        
    	Path tempFile = tempDir.resolve("test_input.txt");
    	List<String> fileData = getSingeListToSaveInFile();
    	
    	// Salva o dado no arquivo temporario
        try {
			Files.write(tempFile, fileData);
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Temporary test file created at: " + tempFile.toAbsolutePath().toString());
        
        List<String> expected = getSingeList();
        List<String> actual = inputStrategy.read(tempFile.toAbsolutePath().toString());
        assertEquals(expected.size(), actual.size(), "Deve conter 1 lista");
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual), "Lista deve ser igual a informada no arquivo");
    }

    private List<String> getDataToSaveInFile(){
    	return Arrays.asList(
                "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},",
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]",
                "[{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000},",
                "{\"operation\":\"sell\", \"unit-cost\":10.00, \"quantity\": 5000}]"
            );
    }
    
    private List<String> getSingeListToSaveInFile(){
    	return Arrays.asList(
                "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},",
                "{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]"
            );
    }
    
    private List<String> getList(){
    	return Arrays.asList(
			"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]",
            "[{\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":10.00, \"quantity\": 5000}]"
           );
    }
    
    private List<String> getSingeList(){
    	return Arrays.asList(
			"[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\": 10000},{\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\": 5000}]"
           );
    }
}
