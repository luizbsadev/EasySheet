package org.luiz.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.luiz.conf.SheetFactory;
import org.luiz.model.Registro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SheetService {
    private static Sheet sheet;
    private static Workbook wb = SheetFactory.getWb();

    public static void configurar(Sheet sheet){
        SheetService.sheet = sheet;

    }

    private static Row criarLinha(int indexLinhaParaDescer) {
        int startRowIndex = indexLinhaParaDescer;
        int numRows = 1;
        int[] columnsToMove = {0, 1, 2, 3, 4, 5, 6};

        for (int rowIndex = sheet.getLastRowNum(); rowIndex >= startRowIndex; rowIndex--) {
            Row oldRow = sheet.getRow(rowIndex);
            Row newRow = sheet.getRow(rowIndex + numRows);
            if (newRow == null) {
                newRow = sheet.createRow(rowIndex + numRows);
            }

            for (int colIndex : columnsToMove) {
                Cell oldCell = oldRow.getCell(colIndex);
                Cell newCell = newRow.createCell(colIndex);

                if (oldCell != null) {
                    // Copiar o estilo da célula
                    newCell.setCellStyle(oldCell.getCellStyle());

                    // Copiar o valor da célula
                    switch (oldCell.getCellType()) {
                        case STRING:
                            newCell.setCellValue(oldCell.getStringCellValue());
                            break;
                        case NUMERIC:
                            newCell.setCellValue(oldCell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            newCell.setCellValue(oldCell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            newCell.setCellFormula(oldCell.getCellFormula());
                            break;
                        case BLANK:
                            newCell.setBlank();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected cell type: " + oldCell.getCellType());
                    }
                }
            }
        }

        // Limpar o conteúdo das colunas especificadas na linha de início
        Row startRow = sheet.getRow(startRowIndex);
        if (startRow == null) {
            startRow = sheet.createRow(startRowIndex);
        }
        for (int colIndex : columnsToMove) {
            Cell cell = startRow.getCell(colIndex);
            if (cell == null) {
                cell = startRow.createCell(colIndex);
            }
            cell.setBlank();
        }
        return startRow;
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
        Cell celulaDeCima = sheet.getRow(celula.getRowIndex() - 1).getCell(celula.getColumnIndex());

        XSSFCellStyle padrão = (XSSFCellStyle) wb.createCellStyle();
        padrão.setDataFormat(wb.createDataFormat().getFormat("R$ #,##0.00"));


        try {
            celula.setCellStyle(celulaDeCima.getCellStyle());
        }catch (NullPointerException e){
            celula.setCellStyle(padrão);
        }
    }

    public static void criarEPreencherRegistro(Registro registro) throws IOException {
        int penultimaLinhaIndex = getUltimaLinhaIndex() - 1;
        Row newRow = criarLinha(penultimaLinhaIndex);
        preencherRegistro(registro, newRow);
        SheetFactory.salvar();
    }

}

