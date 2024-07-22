package org.luiz;

import org.apache.poi.ss.usermodel.*;
import org.luiz.conf.SheetFactory;
import org.luiz.services.SheetService;

import java.io.FileOutputStream;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String caminho = "C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES fe.xlsx";
        SheetFactory sheetFactory = new SheetFactory(caminho);
        Sheet sheet = sheetFactory.criarSheet();
        SheetService service = new SheetService(sheet);
        int penultimaLinhaIndex = service.getUltimaLinhaIndex() - 1;

        sheet.shiftRows(penultimaLinhaIndex, penultimaLinhaIndex, 1);
        Row newRow = sheet.createRow(penultimaLinhaIndex);


        sheetFactory.closeFileInput();

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES mo.xlsx");
        sheetFactory.getWb().write(fileOutputStream);
        fileOutputStream.close();

    }
}