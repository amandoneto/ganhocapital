package br.com.ganhocapital.service;

import java.util.List;

public class ReadFromConsole implements InputStrategy {

	private JsonConverter jsonConverter = null;

	public ReadFromConsole() {
		jsonConverter = new JsonConverter();
	}
	
	@Override
	public List<String> read(String line) {
		
		return jsonConverter.convertToJsonList(line);
	}
}
