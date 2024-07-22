package org.luiz.services;

import org.apache.poi.ss.usermodel.Sheet;

public class SheetService {
    private Sheet sheet;

    public SheetService(Sheet sheet){
        this.sheet = sheet;
    }

    public int getUltimaLinhaIndex(){
        return sheet.getLastRowNum();
    }
}

