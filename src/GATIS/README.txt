O projeto foi desenvolvido no Eclipse.

Para correto funcionamento do algoritmo, deve-se adionar a biblioteca uacari.jar ao build path do projeto.
No eclipse :
1-botão direito na pasta do projeto
2-properties
3-Java Build Path
4- em Library, add JARS... (o uacari.jar se encontra em src\GATIS)
5- Apply and close

Os parametros do GA estão todos como atributos do arquivo GA.java, coloquei 5 imagens para testes.
As imagens 1, 2 e 3 não possuem uma plaquinha de marcação, e o código funciona perfeitamente.
As imagens 4 e 5 possuem a marcação e estão ai pra demonstrar um ponto falho do GA.

para alternar as imagens, basta alterar o atributo static String imagem.
por exemplo, para rodar a img1 que está na pasta, basta alterar para:

static String imagem = "img1";

Para melhor análise já deixei alguns calculos de médias que são impressas no console.

Todos os parametros estão comentados para destacar qual é qual.

Para rodar abra a pasata GATIS como projeto e executar o arquivo GA como aplicação java.