package org.luiz.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.luiz.model.Registro;

import java.util.ArrayList;
import java.util.List;

public class SheetService {
    private static Sheet sheet;

    public static void configurar(Sheet sheet){
        SheetService.sheet = sheet;
    }

    private static Row criarLinha(int index) {
        sheet.shiftRows(index, index, 1);
        return sheet.createRow(index);
    }

    private static int getUltimaLinhaIndex(){
        return sheet.getLastRowNum();
    }

    private static void preencherRegistro(Registro registro, Row newRow) {

        List<Cell> celulas = new ArrayList();


        for (int i = 0; i <= 6; i++){
            if(i != 4) {
                Cell cell = newRow.createCell(i);
                celulas.add(cell);
            }
        }

        celulas.forEach(celula -> {
            switch (celula.getColumnIndex()){
                case 0:
                    celula.setCellValue(registro.getData());
                    break;
                case 1:
                    celula.setCellValue(registro.getCartao());
                    break;
                case 2:
                    celula.setCellValue(registro.getDinheiro());
                    break;
                case 3:
                    celula.setCellValue(registro.getPix());
                    break;
                case 5:
                    celula.setCellValue(registro.getAvista());
                    break;
                case 6:
                    celula.setCellValue(registro.getAprazo());
                    break;
            };
            formatarCelula(celula);
        });


        }

    private static void formatarCelula(Cell celula) {
        //vai copiar a formatação da celula de cima
    }

    public static void criarEPreencherRegistro(Registro registro){
        int penultimaLinhaIndex = getUltimaLinhaIndex() - 1;
        Row newRow = criarLinha(penultimaLinhaIndex);
        preencherRegistro(registro, newRow);
    }
}

