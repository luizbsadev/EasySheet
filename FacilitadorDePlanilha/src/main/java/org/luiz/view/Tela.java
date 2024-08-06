package org.luiz.view;

import org.luiz.model.Registro;
import org.luiz.services.SheetService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Tela {
    private static Scanner ler = new Scanner(System.in);


    public static void mostrar() throws ParseException, IOException {
        int escolha = menu();
        switch (escolha){
            case 1:
                trocarArquivoTela();
                break;
            case 2:
                escreverRegistroTela();
                break;
        }



    }

    private static void escreverRegistroTela() throws ParseException, IOException {
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
        registro.setAprazo(ler.nextFloat());

        telaDeConfirmacao(registro);

    }

    private static void telaDeConfirmacao(Registro registro) throws ParseException, IOException {
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

    private static void editarRegistroTela(Registro registro) throws ParseException, IOException {
        System.out.println("Qual coluna vc gostaria de alterar?");
        System.out.println("1 - Data");
        System.out.println("2 - Cartão");
        System.out.println("3 - Dinheiro");
        System.out.println("4 - Pix");
        System.out.println("5 - A vista");
        System.out.println("6 - A prazo");
        System.out.println("7 - Cancelar");
        int resposta = ler.nextInt();
        System.out.println("Digite o novo valor:");
        String novoValor = ler.next();

        switch (resposta){
            case 1:
                registro.setData(novoValor);
                break;
            case 2:
                registro.setCartao(Float.parseFloat(novoValor));
                break;
            case 3:
                registro.setDinheiro(Float.parseFloat(novoValor));
                break;
            case 4:
                registro.setPix(Float.parseFloat(novoValor));
                break;
            case 5:
                registro.setAvista(Float.parseFloat(novoValor));
                break;
            case 6:
                registro.setAprazo(Float.parseFloat(novoValor));
                break;
            default:
                break;
        }
        System.out.println(registro.toString());
        System.out.println("Deseja editar mais alguma valor?");
        System.out.println("1 - Sim");
        System.out.println("2 - Salvar");
        System.out.println("3 - Cancelar");
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

    private static void telaDeContinuarAdicionando() throws ParseException, IOException {
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

    private static void trocarArquivoTela() {
        System.out.println("Escreva o caminho do arquivo seguindo essa estrutura: C:\\Users\\...\\NOMEDOARQUIVO.xlsx");
        String arquivo = ler.next();
    }

    private static int menu() {
        System.out.println("Arquivo selecionado: $$$$");
        System.out.println("");
        System.out.println("Escolha uma das opções:");
        System.out.println("1 - Escolher novo arquivo");
        System.out.println("2 - Adicionar registro");
        return ler.nextInt();
    }

}
