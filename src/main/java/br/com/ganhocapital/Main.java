package br.com.ganhocapital;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import br.com.ganhocapital.dto.Transaction;
import br.com.ganhocapital.service.InputStrategy;
import br.com.ganhocapital.service.JConverter;
import br.com.ganhocapital.service.JsonConverter;
import br.com.ganhocapital.service.ReadFromConsole;
import br.com.ganhocapital.service.ReadFromFile;
import br.com.ganhocapital.service.StockTaxCalculator;
import br.com.ganhocapital.service.TaxCalculator;

/**
 * 
 * Classe onde inicia a execução do programa. O programa permite duas
 * entradas de dados:
 * 1) Via Console: Permite o cliente digitar uma ou mais lista no formato JSON
 * 2) Via arquivo: O arquivo aceita uma ou mais lista de JSON no formato string
 * Em ambos os casos, o programa para de ler os dados após identifica uma linha
 * em branco.
 * 
 * Depois de coletar os dados, é feito o parse do dado e transformando em uma ou
 * mais
 * lista de Transactions.
 * 
 * Em seguida o
 */
public class Main {

    private JConverter jsonConverter;
    private InputStream inputStream;
    private PrintStream outputStream;
    private InputStrategy consoleInputStrategy;
    private InputStrategy fileInputStrategy;

    public Main(JConverter jsonConverter, InputStream inputStream, PrintStream outputStream,
            InputStrategy consoleInputStrategy, InputStrategy fileInputStrategy) {

        this.jsonConverter = jsonConverter;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.consoleInputStrategy = consoleInputStrategy;
        this.fileInputStrategy = fileInputStrategy;
    }

    /**
     * Ler a informação do prompt ou do arquivo.
     * Para ser lido do arquivo a informação deve finalizar com .txt
     * 
     * @return Lista de string no formato JSON
     */
    public List<String> readInput() {
        try (Scanner scanner = new Scanner(inputStream)) {
            outputStream.println(
                    "Enter JSON objects (one per line) or provide the full filename that ends with .txt. Enter an empty line to finish.");

            InputStrategy input = null;
            String line;
            StringBuilder append = new StringBuilder();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line.trim().isEmpty() || line.endsWith(".txt")) {
                    if (line.endsWith(".txt")) {
                        input = fileInputStrategy; // Use injected file strategy
                        append.append(line);
                    }
                    break;
                }

                append.append(line);
            }

            if (input == null) {
                input = consoleInputStrategy; // Use injected console strategy
            }

            return input.read(append.toString());
        }
    }

    /**
     * Inicia as operações para gerar o imposto devido.
     * Uma vez gerado, imprimi o resultado no console.
     * 
     * @param transactions Lista de transações compra/venda de ações
     */
    private void process(List<Transaction> transactions) {
        TaxCalculator taxCalculator = new StockTaxCalculator();
        String json = jsonConverter.writeTaxesToJson(taxCalculator.calculateTaxes(transactions));
        outputStream.println(json);
    }

    /**
     * Inicia a leitura dos dados via console or arquivo
     */
    public void run() {
        List<String> jsonArr = readInput();
        List<List<Transaction>> collectionTransaction = jsonConverter.convertToListOfTransactions(jsonArr);

        collectionTransaction.forEach(this::process);
    }

    public static void main(String[] args) {
        JConverter jsonConverter = new JsonConverter();
        InputStrategy consoleInputStrategy = new ReadFromConsole();
        InputStrategy fileInputStrategy = new ReadFromFile();

        Main main = new Main(jsonConverter, System.in, System.out, consoleInputStrategy, fileInputStrategy);
        main.run();

    }

}