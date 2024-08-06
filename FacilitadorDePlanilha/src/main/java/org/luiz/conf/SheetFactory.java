package org.luiz.conf;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SheetFactory {
    private static String endereco;
    private static XSSFWorkbook wb;
    private static FileInputStream inputStream;
    private static FileOutputStream outputStream;


    public static void configurar(String endereco) throws FileNotFoundException {
        SheetFactory.endereco = endereco;
        outputStream = new FileOutputStream("C:\\Users\\Felipe\\Downloads\\DELICIAS LANCHES mo.xlsx");


    }

    public static Sheet criarSheet() throws IOException {
        SheetFactory.inputStream = new FileInputStream(endereco);
        SheetFactory.wb = (XSSFWorkbook) WorkbookFactory.create(inputStream);
        Sheet sheet = wb.getSheetAt(0);

        return sheet;
    }

    public static Workbook getWb(){
        return wb;
    }

    public static void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
    public static void salvar() throws IOException {
        SheetFactory.getWb().write(outputStream);
    }
}
