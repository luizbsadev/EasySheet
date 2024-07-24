package org.luiz;

import org.apache.poi.ss.usermodel.*;
import org.luiz.conf.SheetFactory;
import org.luiz.model.Registro;
import org.luiz.services.SheetService;
import org.luiz.view.Tela;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;



public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String caminho = "C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES fe.xlsx";
        SheetFactory sheetFactory = new SheetFactory(caminho);
        Sheet sheet = sheetFactory.criarSheet();
        SheetService.configurar(sheet);


        Tela.mostrar();


        sheetFactory.closeFileInput();

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES mo.xlsx");
        sheetFactory.getWb().write(fileOutputStream);
        fileOutputStream.close();

    }

}