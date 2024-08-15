package org.luiz.view;

import org.luiz.conf.SheetFactory;
import org.luiz.model.Registro;
import org.luiz.services.BackupService;
import org.luiz.services.PathService;
import org.luiz.services.SheetService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Tela {
    private static Scanner ler = new Scanner(System.in);


    public static void mostrar() throws ParseException, IOException, InterruptedException {
        if (SheetService.getSheet() != null && PathService.getPath() != null) {
            int escolha = menu();
            switch (escolha) {
                case 1:
                    ler.nextLine(); //resolve o bug do nextLine() dps do nextInt()
                    trocarArquivoTela();
                    break;
                case 2:
                    escreverRegistroTela();
                    break;
            }
        }else {
            trocarArquivoTela("arquivo não encontrado");
        }


    }

    private static void escreverRegistroTela() throws ParseException, IOException, InterruptedException {
        limparTela();
        Registro registro = new Registro();
        System.out.println("Escreva os valores para cada celula:");
        System.out.print("Data:");
        registro.setData(ler.next());
        System.out.print("Cartão:");
        registro.setCartao(ler.nextFloat());
        System.out.print("Dinheiro:");
        registro.setDinheiro(ler.nextFloat());
        System.out.print("Pix:");
        registro.setPix(ler.nextFloat());
        System.out.print("A vista:");
        registro.setAvista(ler.nextFloat());
        System.out.print("A prazo:");
        ler.nextLine();
        String aprazo = ler.nextLine();

        if (aprazo != "")
            registro.setAprazo(Float.parseFloat(aprazo));

        telaDeConfirmacao(registro);

    }

    private static void telaDeConfirmacao(Registro registro) throws ParseException, IOException, InterruptedException {
        limparTela();
        System.out.println("Deseja confirmar o salvamento da linha?");
        System.out.println(registro.toString());
        System.out.println("1 - SIM");
        System.out.println("2 - EDITAR REGISTRO");
        System.out.println("3 - CANCELAR REGISTRO");
        int escolha = ler.nextInt();
        switch (escolha){
            case 1:
                SheetService.criarEPreencherRegistro(registro);
                telaDeContinuarAdicionando();
                break;
            case 2:
                editarRegistroTela(registro);
                break;
            case 3:
                mostrar();
        }
    }

    private static void editarRegistroTela(Registro registro) throws ParseException, IOException, InterruptedException {
        limparTela();
        System.out.println(registro.toString());
        System.out.println("Qual coluna vc gostaria de alterar?");
        System.out.println("1 - Data");
        System.out.println("2 - Cartão");
        System.out.println("3 - Dinheiro");
        System.out.println("4 - Pix");
        System.out.println("5 - A vista");
        System.out.println("6 - A prazo");
        System.out.println("0 - Cancelar");
        int resposta = ler.nextInt();
        System.out.println("Digite o novo valor:");
        Float novoValor = null;
        String novoValorData = null;
        if (resposta == 1)
             novoValorData = ler.next();
        else
             novoValor = ler.nextFloat();

        switch (resposta){
            case 1:
                registro.setData(novoValorData);
                break;
            case 2:
                registro.setCartao(novoValor);
                break;
            case 3:
                registro.setDinheiro(novoValor);
                break;
            case 4:
                registro.setPix(novoValor);
                break;
            case 5:
                registro.setAvista(novoValor);
                break;
            case 6:
                registro.setAprazo(novoValor);
                break;
            default:
                break;
        }
        limparTela();
        System.out.println(registro.toString());
        System.out.println("Deseja editar mais alguma valor?");
        System.out.println("1 - Sim");
        System.out.println("2 - Salvar");
        System.out.println("0 - Cancelar");
        int reposta2 = ler.nextInt();

        switch (reposta2){
            case 1:
                editarRegistroTela(registro);
                break;
            case 2:
                SheetService.criarEPreencherRegistro(registro);
                telaDeContinuarAdicionando();
                break;
            default:
                mostrar();
                break;

        }
    }

    private static void telaDeContinuarAdicionando() throws ParseException, IOException, InterruptedException {
        limparTela();
        System.out.println("Deseja adicionar outro registro?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        int resposta = ler.nextInt();


        switch (resposta){
            case 1:
                escreverRegistroTela();
            default:
                break;
        }

    }

    private static void trocarArquivoTela() throws IOException, ParseException, InterruptedException {
        limparTela();
        System.out.println("Escreva o caminho do arquivo seguindo essa estrutura: C:\\Users\\...\\NOMEDOARQUIVO.xlsx");
        String caminho = ler.nextLine();
        PathService.setPath(caminho);

        SheetService.setArquivo(PathService.getPath());
        mostrar();
    }

    private static void trocarArquivoTela(String mensagem) throws IOException, ParseException, InterruptedException {
        System.out.println(mensagem);
        trocarArquivoTela();
        BackupService.fazerBackup();
    }

    private static int menu() throws IOException, InterruptedException {
        limparTela();
        System.out.println("Arquivo selecionado: "+ SheetFactory.getEndereco());
        System.out.println("");
        System.out.println("Escolha uma das opções:");
        System.out.println("1 - Escolher novo arquivo");
        System.out.println("2 - Adicionar registro");
        return ler.nextInt();
    }

    private static void limparTela() throws IOException, InterruptedException {
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    }

}
