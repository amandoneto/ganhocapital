# Descrição da Aplicação Ganho de capital.
- Implementa um programa de linha de comando (CLI) que calcula o imposto a
ser pago sobre lucros ou prejuízos de operações no mercado financeiro de ações.

# Versão Java e Dependências
1) Esse programa foi desenvolvido usando openjdk version "17.0.15" 2025-04-15
2) O programa usa a ferramenta de automação Maven  Apache Maven 3.8.7
   Para executar os testes e criar o jar, é necessário instalar o maven.
3) O programa usa as seguintes dependências:
	1) jackson-databind: Processamento do JSON
	2) junit-jupiter-api: Teste unitários


# Executando o programa
1) Ao descompactar o java_17.zip em qualquer diretório, será criado um diretório ganhocapital 
2) cd java_17
3) mvn clean package - esse comando criar o jar para ser executado.
   Esses comando já executa os testes, mas caso deseje executar apenas os testes, 
   o comando é: mvn test
   caso não tenha o maven, uma cópia do jar está incluida no arquivo zip.
4) java -jar target/ganhocapital-1.0.0.jar ( ajustes para o diretório caso necessário)
O diretório target é criado/populado após rodar o comando mvn clean package
O programa não faz nenhuma validação dos dados, pois os dados serão inputados conforme detalha na especificação

O programa aceita uma ou mais lista de JSON.
O programa encerra a leitura quando a linha não houver mais dados.
O programa aceita 2 tipos de inputs :
	1) via console 
	2) via arquivo: para o uso do arquivo, é necessário informar o caminho
	do arquivo e o mesmo deve finalizar com .txt
	ex: /tmp/test/case1.txt

# Dados de entrada	
Esses são os exemplos de JSON suportado pela aplicação
Modelo 1: Apenas uma lista
[{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
{"operation":"sell", "unit-cost":20.00, "quantity": 5000}]

Modelo 2: mais de uma lista
[{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
{"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
[{"operation":"buy", "unit-cost":20.00, "quantity": 10000},
{"operation":"sell", "unit-cost":10.00, "quantity": 5000}]

# Dados de saída
- O programa retornar uma lista contendo o imposto pago para cada operação recebida. 
A lista terá o formato JSON e a saída será no console do cliente.
Ex:
[{"tax": 0.0}, {"tax": 10000.0}]
[{"tax": 0.0}, {"tax": 0.0}]

#Design Pattern
- Para a solução dessa problema, foi usado os seguites padrões:
1) Strategy: Usado para definir os diferentes tipos de inputs e calculo para o imposto
2) State: Usado na operação de vendas para calculo de lucro ou prejuizo.