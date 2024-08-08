package org.luiz;


import org.luiz.conf.SheetFactory;
import org.luiz.services.SheetService;
import org.luiz.view.Tela;


import java.io.IOException;
import java.text.ParseException;



public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String caminho = "C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES fe.xlsx";
        SheetService.setArquivo(caminho);

        Tela.mostrar();

        SheetFactory.close();



    }

}