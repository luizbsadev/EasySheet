package org.luiz.conf;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SheetFactory {
    private String endereco;
    private Workbook wb;
    private FileInputStream file;

    public SheetFactory(String endereco){
        this.endereco = endereco;
    }

    public Sheet criarSheet() throws IOException {
        this.file = new FileInputStream(endereco);
        this.wb = WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(0);

        return sheet;
    }

    public Workbook getWb(){
        return wb;
    }

    public void closeFileInput() throws IOException {
        file.close();
    }
}
