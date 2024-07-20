package org.luiz.services;

import org.apache.poi.ss.usermodel.Sheet;

public class PegarLinha {
    Sheet sheet;
    public PegarLinha(Sheet sheet){
        this.sheet = sheet;
    }

    public int ultima(){
        return sheet.getLastRowNum();
    }
}
