/**
 * 
 */
package br.com.ganhocapital.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Classe usada para converter o objeto em JSON.
 * Na leitura, a classe converter o JSON para uma classe Transaction
 * Na escrita, converte a classe Tax para um JSON
 *
 */
public class JsonConverter implements JConverter{
	private ObjectMapper objectMapper = null;
	
	public JsonConverter() {
		objectMapper = new ObjectMapper();
	}
	
	/**
     * Converte uma lista de Tax para JSON.
     *
     * @param Convert Lista para JSON
     * @return Representação da lista em JSON.
     */
	@Override
    public String writeTaxesToJson(List<Tax> taxes) {
    	String json = null;
        try {
			json = objectMapper.writeValueAsString(taxes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        return json;
    }
    
    /**
	 * Transforma uma lista JSON em formato string para varias listas
	 * de objeto Transaction
	 * 
	 * @param list Lista JSON no formato string
	 * @return	Coleções de Transaction
	 */
	@Override
	public List<List<Transaction>> convertToListOfTransactions(List<String> list) {

		List<Transaction> transactions = null;
        List<List<Transaction>> collectionTransaction = new ArrayList<>();

   
        // Read the line as a JSON array
        JsonNode rootNode;
		try {
			
			for( String json : list) {
				rootNode = objectMapper.readTree(json);
				if (rootNode.isArray()) {
					transactions = new ArrayList<>();
					
		            for (JsonNode node : rootNode) {
		                if (node.isObject()) {
		                    // Convert the JSON node to a Map for easier access
		                	Transaction transaction = objectMapper.convertValue(node, Transaction.class);
		                	transactions.add(transaction);		                    
		                }
		            }
		            collectionTransaction.add(transactions);
		        }
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
         return collectionTransaction;

	}
    
    /**
     * Cria uma coleção de lista no formato json
     * @param input	Dado usado na convers
     * @return	Coleção de JSON
     */
	@Override
    public List<String> convertToJsonList(String input){
    	List<String> extractedArrays = new ArrayList<>();
    	Pattern pattern = Pattern.compile("\\[.*?\\]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
        	extractedArrays.add (matcher.group());
        }
        return extractedArrays;
    }
}
