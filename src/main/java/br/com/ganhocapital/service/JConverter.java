/**
 * 
 */
package br.com.ganhocapital.service;

import java.util.List;

import br.com.ganhocapital.dto.Tax;
import br.com.ganhocapital.dto.Transaction;

/**
 * Contrato para implementar como a string em formato de JSON será consumida e
 * como o resultado será gerado usando o formato JSON
 *
 */
public interface JConverter {

	List<List<Transaction>> convertToListOfTransactions(List<String> jsonArr);
    String writeTaxesToJson(List<Tax> taxResults);
    List<String> convertToJsonList(String input);
}
