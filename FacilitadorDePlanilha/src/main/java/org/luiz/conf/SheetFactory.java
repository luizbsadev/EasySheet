package org.luiz.conf;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class SheetFactory {
    private static String endereco;
    private static XSSFWorkbook wb;
    private static FileInputStream file;


    public static void configurar(String endereco){
        SheetFactory.endereco = endereco;
    }

    public static Sheet criarSheet() throws IOException {
        SheetFactory.file = new FileInputStream(endereco);
        SheetFactory.wb = (XSSFWorkbook) WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(0);

        return sheet;
    }

    public static Workbook getWb(){
        return wb;
    }

    public static void closeFileInput() throws IOException {
        file.close();
    }
}
