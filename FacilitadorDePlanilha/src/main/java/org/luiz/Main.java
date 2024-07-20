package org.luiz;

import org.apache.poi.ss.usermodel.*;
import org.luiz.conf.SheetFactory;
import org.luiz.services.PegarLinha;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String caminho = "C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES fe.xlsx";
        Sheet sheet = new SheetFactory(caminho).criarSheet();
        int ultimaLinhaIndex = new PegarLinha(sheet).ultima();
        System.out.println(ultimaLinhaIndex);

    }
}