FortuneCookie API

Versão 1.0.0

Atualizada na Branch Main com o Spring Boot, versão 3.0.2, e atualizações das bibliotecas do JUnit e PiTest.

A partir do Spring Boot 3, é pré-requisito para essa aplicação a JDK 17 ou superior.

______________

Release Notes

Versão 1.2.1
Implementação de controle de Exceptions Usando Problem Details, conforme:
https://deviniciative.wordpress.com/2023/02/17/spring-boot-3-melhorando-suas-exceptions-com-problem-details/

_______________
Versão 1.3.0
Implementado a recuperação do Frase do Biscoito via IA com o ChatGPT, veja:
https://deviniciative.wordpress.com/2023/07/12/hands-on-conectando-uma-aplicacao-java-ao-chatgpt/

________________
Versão 1.4.0
- Atualizado para o Java 21
- Atualizado para o Spring 3.1.4
- Implementando Feature Toogle via FF4J
- Troca da Porta Padrao do servidor para ":9090"
- Detalhes: https://deviniciative.wordpress.com/2023/10/23/lidando-com-feature-flag-usando-o-ff4j/

________________
Versao 1.5.0

    - Incluindo o Spring Actuator

    - Criado mecanismo de Sanitização de dados.

    - Retorno da porta Padrão para ":8080"

    - Alterando o DockerFile para rodar o Temurin JDK 21 e rodar a partir do Jar

    - Atualização do Spring Boot para versão 3.2.1

    - Atualização das dependencias para suporte ao Java 21

    
    Detalhes: https://deviniciative.wordpress.com/2024/01/05/colocando-sua-api-spring-boot-no-ar-com-o-aws-fargate/

    Detalhes: https://deviniciative.wordpress.com/2024/01/12/spring-boot-3-protegendo-dados-sensiveis-no-actuator/

________________
Versao 1.5.1

    - Atualização do Spring Boot para versão 3.3.1 

    - Atualização do docker-compose.yml para configurar o Health Check no Containers.

________________
Versao 1.5.2

    - Inclusão de integração com o Prometheus e Actuator

________________
Versao 1.5.3

    - Inclusão da biblioteca Spring Boot Startup Report

________________
Versao 1.5.4

    - Ajustes para atualização para habilitação dos testes unitários
    
    - Atualização para o Spring 3.4.2
    - junit-jupiter-api de 5.10.1 para 5.11.4
    - junit-jupiter-engine de 5.10.1 para 5.11.4
    - maven-surefire-plugin de 3.2.3 para 3.5.2
    - httpclient5 de 5.2.1 para 5.4.2

________________
Versão 2.0.0 - (Versão Major)

    - Conexão com Banco de Dados PostGres para recuperação da lista de Frase
    - Refactor do pacote data para DTO
    - Criação do pacote Data com a classe Frases para conexão com o DataBase
    - Remoção do Antigo método de IA sem Langchain4j
    - Refatoração dos Testes Unitários
    - Criação dos Testes de Integração com TesteContainers
    - Migração para o Java 24
    - Refatorações dos Codigos para melhorias
_________________
Versão 2.1.0

    - Plugin Jacoco de 0.8.11 para 0.8.13 (suporte ao Java 24)
    - Spring Boot da 3.4.2 para 3.5.3

_________________
Versão 2.1.1

    - Atualização para o Java 25
    - Atualização do Spring Boot para 3.5.8