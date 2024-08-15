package org.luiz.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.luiz.conf.SheetFactory;
import org.luiz.model.Registro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SheetService {
    private static Sheet sheet;
    private static Workbook wb;

    public static void configurar(Sheet sheet){
        SheetService.sheet = sheet;
        SheetService.wb = SheetFactory.getWb();
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

    private static int getPenultimaLinhaIndex(){
        int lastRowWithContent = 0;
        for (int rowIndex = sheet.getLastRowNum(); rowIndex >= 0; rowIndex--) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    lastRowWithContent = rowIndex;
                    break;
                }
            }
        }
        return lastRowWithContent;
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
                    if (registro.getAprazo() != 0)
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
        int penultimaLinhaIndex = getPenultimaLinhaIndex();
        Row newRow = criarLinha(penultimaLinhaIndex);
        preencherRegistro(registro, newRow);
        atualizarFormula();
        SheetFactory.salvar();

    }

    public static Sheet getSheet() {
        return sheet;
    }

    public static void setArquivo(String arquivo) throws IOException {
        try {
            SheetFactory.setEndereco(arquivo);
            Sheet sheet = SheetFactory.criarSheet();
            SheetService.configurar(sheet);
            PathService.setPath(arquivo);
       }catch (FileNotFoundException e){
            SheetService.configurar(null);
            System.out.println("Arquivo não encontrado");
        }catch (NullPointerException e ){
            System.out.println("Arquivo path em branco");
        }
        
    }

    private static void atualizarFormula(){
        Row row = sheet.getRow(getPenultimaLinhaIndex());
        int[] entradaIndexs = procurarEntrada();
        Cell entradaValor = sheet.getRow(entradaIndexs[0]).getCell(entradaIndexs[1]);
        Cell saidaValor = sheet.getRow(entradaIndexs[0]+ 1).getCell(entradaIndexs[1]);
        Cell totalValor = sheet.getRow(entradaIndexs[0] + 2).getCell(entradaIndexs[1]);

        List<Cell> linha = new ArrayList();
        linha.add(row.getCell(1));
        linha.add(row.getCell(2));
        linha.add(row.getCell(3));
        linha.add(saidaValor);

        linha.forEach(celula -> {
             String formulaAntiga = celula.getCellFormula();
            String novaFormula = formulaAntiga.replace(String.valueOf(getPenultimaLinhaIndex() - 1), String.valueOf(getPenultimaLinhaIndex()));
            celula.setCellFormula(novaFormula);
        });

        String formulaAntigaEntrada = entradaValor.getCellFormula();
        String novaFormulaEntrada = formulaAntigaEntrada.replace(String.valueOf(getPenultimaLinhaIndex()), String.valueOf(getPenultimaLinhaIndex() + 1));
        entradaValor.setCellFormula(novaFormulaEntrada);

        FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

        linha.forEach(celula -> evaluator.evaluateFormulaCell(celula));
        evaluator.evaluateFormulaCell(entradaValor);
        evaluator.evaluateFormulaCell(totalValor);
    }

    private static int[] procurarEntrada(){
        Iterator<Row> iteratorRow = sheet.rowIterator();
        int indexRow = 0;
        int indexColumn = 0;


        while (iteratorRow.hasNext()){
            Row row = iteratorRow.next();
            Iterator<Cell> iteratorCell = row.iterator();
            while (iteratorCell.hasNext()){
                Cell cell = iteratorCell.next();
                try{
                    if (cell.getStringCellValue().equals("ENTRADA")){
                        indexRow = row.getRowNum();
                        indexColumn = cell.getColumnIndex();
                    }
                }catch(Exception e){

                }

            }
        }
         int[] indexs = {indexRow, indexColumn + 1};

        return indexs;

    }
}

