package org.luiz.conf;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SheetFactory {
    private String endereco;

    public SheetFactory(String endereco){
        this.endereco = endereco;
    }

    public Sheet criarSheet() throws IOException {
        FileInputStream arquivo = new FileInputStream(endereco);

        Workbook workbook = WorkbookFactory.create(arquivo);
        Sheet sheet = workbook.getSheetAt(0);

        return sheet;
    }
}
