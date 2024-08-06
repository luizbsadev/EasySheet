package org.luiz;

import org.apache.poi.ss.usermodel.*;
import org.luiz.conf.SheetFactory;
import org.luiz.services.SheetService;
import org.luiz.view.Tela;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;



public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String caminho = "C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES fe.xlsx";
        SheetFactory.configurar(caminho);
        Sheet sheet = SheetFactory.criarSheet();
        SheetService.configurar(sheet);


        Tela.mostrar();


        SheetFactory.closeFileInput();

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES mo.xlsx");
        SheetFactory.getWb().write(fileOutputStream);
        fileOutputStream.close();

    }

}