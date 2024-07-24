package org.luiz.view;

import org.luiz.model.Registro;
import org.luiz.services.SheetService;

import java.text.ParseException;
import java.util.Scanner;

public class Tela {
    private static Scanner ler = new Scanner(System.in);


    public static void mostrar() throws ParseException {
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

    private static void escreverRegistroTela() throws ParseException {
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

    private static void telaDeConfirmacao(Registro registro) throws ParseException {
        System.out.println("Deseja confirmar o salvamento da linha?");
        System.out.println(registro.toString());
        System.out.println("1 - SIM");
        System.out.println("2 - EDITAR REGISTRO");
        System.out.println("3 - CANCELAR REGISTRO");
        int escolha = ler.nextInt();
        switch (escolha){
            case 1:
                SheetService.criarEPreencherRegistro(registro);
                break;
            case 2:
                escreverRegistroTela();
                break;
            case 3:
                mostrar();
        }
    }

    private static void trocarArquivoTela() {
        System.out.println("Escreva o caminho do arquivo seguindo essa estrutura: C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES fe.xlsx");
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
