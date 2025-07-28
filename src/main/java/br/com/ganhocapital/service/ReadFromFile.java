/**
 * 
 */
package br.com.ganhocapital.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Permite que os dados sejam lidos a partir de um arquivo
 *
 */
public class ReadFromFile implements InputStrategy {

	private JsonConverter jsonConverter = null;

	public ReadFromFile() {
		jsonConverter = new JsonConverter();
	}
	
	@Override
	public List<String> read(String input) {		

        String result = null;
		
        try (BufferedReader reader = readFile(input)) {
        	
        	result = reader.lines()
                                 .takeWhile(line -> !line.trim().isEmpty()) // Take lines as long as they are not empty
                                 .collect(Collectors.joining());
        	
        } catch (IOException e1) {
			e1.printStackTrace();
		}
		return convertResultToList(result);
	}

	private BufferedReader readFile(String input) throws IOException {
		Path filePath = Paths.get(input);
		
		if (!Files.exists(filePath)) {
        	
        	System.out.println("File not found: " + filePath);
        	System.exit(1);
        }
		return Files.newBufferedReader(filePath);
	}
	
	private List<String> convertResultToList(String result){
		return jsonConverter.convertToJsonList(result);
	}
}
