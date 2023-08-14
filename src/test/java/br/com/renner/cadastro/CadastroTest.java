package br.com.renner.cadastro;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;

public class CadastroTest {

    private WebDriver navegador;
    private double valorConta;

    private char getLastDigit(String accountNumber) {

        return accountNumber.charAt(accountNumber.length() - 1);
    }

    @BeforeEach
    public void setUp() {
      WebDriverManager.chromedriver().setup();
      navegador = new ChromeDriver();
      navegador.manage().window().maximize();
      //Acessar o site
      navegador.get("https://bugbank.netlify.app/");
    }
    @AfterEach
    public void tearDown() {
        if (navegador != null) {
          navegador.quit();
        }
    }
    @Test
    @DisplayName("Cadastro de conta")
    public void testCadastroDeConta () {


        //Fazer 1º cadastro
        WebElement btnRegistrar = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[1]/form/div[3]/button[2]"));
        btnRegistrar.click();
        WebElement email = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/form/div[2]/input"));
        email.sendKeys("tomas.azevedo@geradornv.com.br");
        WebElement nome = navegador.findElement(By.name("name"));
        nome.sendKeys("Tomás Sá Azevedo");
        WebElement senha = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/form/div[4]/div/input"));
        senha.sendKeys("123456");
        WebElement confirmaSenha = navegador.findElement(By.name("passwordConfirmation"));
        confirmaSenha.sendKeys("123456");
        //criar conta com saldo
        WebElement geraSaldo = navegador.findElement(By.cssSelector("#toggleAddBalance"));
        geraSaldo.click();
        //Botão Cadastrar
        WebElement btnCadastrar = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/form/button"));
        btnCadastrar.click();
        //Clicar no botão fechar do pop up
        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement btnFechar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btnCloseModal\"]")));
        btnFechar.click();

        //Fazer login na conta
        WebElement loginEmail = navegador.findElement(By.name("email"));
        loginEmail.sendKeys("tomas.azevedo@geradornv.com.br");
        WebElement loginSenha = navegador.findElement(By.name("password"));
        loginSenha.sendKeys("123456");
        WebElement btnAcessar = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[1]/form/div[3]/button[1]"));
        btnAcessar.click();

        // Salvar o número da conta
        By accountNumberLocator = By.xpath("//*[@id=\"textAccountNumber\"]/span");
        WebElement accountNumberElement = wait.until(ExpectedConditions.presenceOfElementLocated(accountNumberLocator));
        String accountNumber = accountNumberElement.getText();
        String firstThreeDigits = accountNumber.substring(0, 3);
        char lastDigit = getLastDigit(accountNumber);
       // Clicar no botão Sair
        WebElement btnSair = navegador.findElement(By.id("btnExit"));
        btnSair.click();

        //Fazer 2º cadastro
        btnRegistrar = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[1]/form/div[3]/button[2]"));
        btnRegistrar.click();
        email = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/form/div[2]/input"));
        email.sendKeys("rosani.laporte@geradornv.com.br");
        nome = navegador.findElement(By.name("name"));
        nome.sendKeys("Rosani Robadey Laporte");
        senha = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/form/div[4]/div/input"));
        senha.sendKeys("123456");
        confirmaSenha = navegador.findElement(By.name("passwordConfirmation"));
        confirmaSenha.sendKeys("123456");
        //criar conta com saldo
        geraSaldo = navegador.findElement(By.cssSelector("#toggleAddBalance"));
        geraSaldo.click();
        //Botão Cadastrar
        btnCadastrar = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[2]/form/button"));
        btnCadastrar.click();
        //Clicar no botão fechar do pop up
        wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        btnFechar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btnCloseModal\"]")));
        btnFechar.click();

        //Fazer login na conta
        loginEmail = navegador.findElement(By.name("email"));
        loginEmail.sendKeys("rosani.laporte@geradornv.com.br");
        loginSenha = navegador.findElement(By.name("password"));
        loginSenha.sendKeys("123456");
        btnAcessar = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[1]/form/div[3]/button[1]"));
        btnAcessar.click();

        // Gravar valor inicial da conta
        By valorInicialLocator = By.xpath("//*[@id=\"textBalance\"]/span");
        WebElement valorInicialElemento = wait.until(ExpectedConditions.presenceOfElementLocated(valorInicialLocator));
        String valorConta = valorInicialElemento.getText();
        // Remover caracteres não numéricos da string, mantendo apenas números, vírgulas e pontos
        String valorNumerico = valorConta.replaceAll("[^\\d,.]", "");
        // Substituir vírgulas por pontos (para garantir formatação correta) e remover múltiplos pontos
        valorNumerico = valorNumerico.replace(",", ".").replaceAll("(?<=\\d)\\.(?=\\d(.*\\d)?)", "");
        valorNumerico = valorNumerico.substring(0, 4);
        // Converter a string em um número double
        double saldoInicial = Double.parseDouble(valorNumerico);


        //Transferencia
        WebElement btnTransferencia = navegador.findElement(By.id("btn-TRANSFERÊNCIA"));
        btnTransferencia.click();
        wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement inputElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"__next\"]/div/div[3]/form/div[1]/div[1]/input")));
        inputElement.sendKeys(firstThreeDigits);
        WebElement inputElementDigito = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"__next\"]/div/div[3]/form/div[1]/div[2]/input")));
        inputElementDigito.sendKeys(String.valueOf(lastDigit));
        WebElement valorTransferencia = navegador.findElement(By.name("transferValue"));
        valorTransferencia.sendKeys("500");
        String valorTransferenciaText = valorTransferencia.getAttribute("value"); // Obter o texto do elemento
        double valorTransferido = Double.parseDouble(valorTransferenciaText);
        WebElement descricaoTransferencia = navegador.findElement(By.name("description"));
        descricaoTransferencia.sendKeys("Pix");
        WebElement btnTransferirAgora = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/form/button"));
        btnTransferirAgora.click();

        //Validação da transferencia
        WebElement successMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"modalText\"]")));
        String successMessage = successMessageElement.getText();
        Assertions.assertEquals("Transferencia realizada com sucesso", successMessage);
        WebElement closeButon = navegador.findElement(By.xpath("//*[@id=\"btnCloseModal\"]"));
        closeButon.click();
        WebElement backButon = navegador.findElement(By.xpath("//*[@id=\"btnBack\"]"));
        backButon.click();


        //Gravar o saldo da conta que realizou a transferencia
        WebElement saldoAtual = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"textBalance\"]/span")));
        String saldoAtualText = saldoAtual.getText();
        // Remover caracteres não numéricos da string, mantendo apenas números, vírgulas e pontos
        String saldoNumerico = saldoAtualText.replaceAll("[^\\d,.]", "");
        // Substituir vírgulas por pontos (para garantir formatação correta) e remover múltiplos pontos
        saldoNumerico = saldoNumerico.replace(",", ".").replaceAll("\\.+", ".");
        // Converter a string em um número double
        double saldoRecebido = Double.parseDouble(saldoNumerico);


        // Valide o valor atual da conta que realizou a transferencia
        // Remover caracteres não numéricos da string, mantendo apenas números, vírgulas e pontos
        saldoNumerico = saldoAtualText.replaceAll("[^\\d.,]", "");
        // Substituir vírgulas por pontos (para garantir formatação correta) e remover múltiplos pontos
        saldoNumerico = saldoNumerico.replace(",", ".").replaceAll("\\.+", ".");
        // Converter a string em um número double
        double saldoAposTransferencia = Double.parseDouble(saldoNumerico);
        // Calcular o saldo esperado após a transferência
        double saldoEsperado = saldoInicial - valorTransferido;
        // Realizar a validação com uma margem de erro de 0.001
        double margemDeErro = 0.001;
        Assertions.assertEquals(saldoEsperado, saldoAposTransferencia, margemDeErro);

        //Sair da conta
        btnSair = navegador.findElement(By.id("btnExit"));
        btnSair.click();

        //Fazer login na conta que recebeu a transferencia
        loginEmail = navegador.findElement(By.name("email"));
        loginEmail.sendKeys("tomas.azevedo@geradornv.com.br");
        loginSenha = navegador.findElement(By.name("password"));
        loginSenha.sendKeys("123456");
        btnAcessar = navegador.findElement(By.xpath("//*[@id=\"__next\"]/div/div[2]/div/div[1]/form/div[3]/button[1]"));
        btnAcessar.click();

        //Consultar o extrato
        WebElement btnExtrato = navegador.findElement(By.id("btn-EXTRATO"));
        btnExtrato.click();

        //Guardar o valor de abertura da conta
        WebElement valorInicialConta = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"textTransferValue\"]")));
        String valorInicialContaText = valorInicialConta.getText();
        // Remover caracteres não numéricos da string, mantendo apenas números, vírgulas e pontos
        String valorInicialNumerico = valorInicialContaText.replaceAll("[^\\d,.]", "");
        // Substituir vírgulas por pontos (para garantir formatação correta) e remover múltiplos pontos
        valorInicialNumerico = valorInicialNumerico.replace(",", ".").replaceAll("(?<=\\d)\\.(?=\\d(.*\\d)?)", "");
        valorInicialNumerico = valorInicialNumerico.substring(0, 4);
        // Converter a string em um número double
        double saldoInicialConta = Double.parseDouble(valorInicialNumerico);

        //Guardar o saldo atual da conta
        WebElement saldoDisponivelConta = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"textBalanceAvailable\"]")));
        String saldoDisponivelContaText = saldoDisponivelConta.getText();
        // Remover caracteres não numéricos da string, mantendo apenas números, vírgulas e pontos
        String saldoDisponivelContaNumerico = saldoDisponivelContaText.replaceAll("[^\\d,.]", "");
        // Substituir vírgulas por pontos (para garantir formatação correta) e remover múltiplos pontos
        saldoDisponivelContaNumerico = saldoDisponivelContaNumerico.replace(",", ".").replaceAll("(?<=\\d)\\.(?=\\d(.*\\d)?)", "");
        saldoDisponivelContaNumerico = saldoDisponivelContaNumerico.substring(0, 4);
        // Converter a string em um número double
        double saldoAtualConta = Double.parseDouble(saldoDisponivelContaNumerico);

        //Validar se o valor inicial da conta + valor de tranferencia confere com o saldo disponivel na conta
        // Calcular o saldo esperado após a transferência
        double valorAtual  = saldoInicialConta + valorTransferido;
        // Realizar a validação com uma margem de erro de 0.001
        margemDeErro = 0.001;
        Assertions.assertEquals(saldoAtualConta, valorAtual, margemDeErro);



    }





}
