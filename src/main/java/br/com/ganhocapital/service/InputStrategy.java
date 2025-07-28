/**
 * 
 */
package br.com.ganhocapital.service;

import java.util.List;


/**
 * Define a estratégia de leitura dos dados
 *
 */
public interface InputStrategy {

	/**
	 * Processa os dados no format JSON criando uma lista como resultado
	 * @param line Dado a ser transformando
	 * @return Lista de JSON em string
	 * Ex:
	 * [{"operation":"buy", "unit-cost":10.00, "quantity": 100},{"operation":"sell", "unit-cost":15.00, "quantity": 50},{"operation":"sell", "unit-cost":15.00, "quantity": 50}]
	 * [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":20.00, "quantity": 5000},{"operation":"sell", "unit-cost":5.00, "quantity": 5000}]
	 */
	List<String> read(String line);
}
