# desafio-dbc-votacao
## Sistema de votação baseado em Pauta e Sessão.


<h3>RabbitMQ</h3>
Windows
Download and install ERlang http://erlang.org/download/otp_win64_22.3.exe
Downlaod and install RabbitMQ https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.8/rabbitmq-server-3.8.8.exe

Docker 
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management


##Execução da Aplicação
java -jar target/desafio-dbc-votacao-0.0.1-SNAPSHOT.jar


##Swagger 
http://localhost:8080/dbc-votacao/api/swagger-ui/#

##H2
<p>
Para fácil configuração e teste foi utilizado o banco em memória H2</p>
http://localhost:8080/h2-console


