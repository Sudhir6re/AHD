package com.mahait.gov.in.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PensionExcelGenerator {
	


    private List<Object[]> lstPensPaybillGenerationTrnDetails;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public PensionExcelGenerator(List<Object[]> pensionBillDetails) {
        this.lstPensPaybillGenerationTrnDetails = pensionBillDetails;
        workbook = new XSSFWorkbook();
    }
  
    
    private void writeHeader() {
        sheet = workbook.createSheet("MJP");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Sr No", style);
        createCell(row, 1, "Employee Name", style);
        createCell(row, 2, "PPO No.", style);
        createCell(row, 3, "Pension Basic.", style);
        createCell(row, 4, "DA", style);
        createCell(row, 5, "Arrears", style);
        createCell(row, 6, "Gross Total Amt.", style);
        createCell(row, 7, "Commutation", style);
        createCell(row, 8, "Recovery", style);
        createCell(row, 9, "PT", style);
        createCell(row, 10, "Income Tax", style);
        createCell(row, 11, "Total deduction", style);
        createCell(row, 12, "Net Amount", style);
        createCell(row, 13, "Bank Name", style);
        createCell(row, 14, "Ifsc Code", style);
        createCell(row, 15, "Account Number", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof Double) {
    	cell.setCellValue((Double) valueOfCell);
         }
        else {
        	if(valueOfCell==null) {
        		 cell.setCellValue("0");
        	}
        }
        cell.setCellStyle(style);
    }
    private void write() {
    	int i=1;
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (Object obj[]: lstPensPaybillGenerationTrnDetails) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, i, style);
            
            
            createCell(row, columnCount++, obj[0], style);
            createCell(row, columnCount++, obj[1], style);
            createCell(row, columnCount++, obj[2], style);
            createCell(row, columnCount++, obj[3], style);
            createCell(row, columnCount++, obj[4], style);
            createCell(row, columnCount++, obj[5], style);
            createCell(row, columnCount++, obj[6], style);
            createCell(row, columnCount++, obj[10], style);
            createCell(row, columnCount++, obj[11], style);
            createCell(row, columnCount++, obj[12], style);
            createCell(row, columnCount++, obj[13], style);
            createCell(row, columnCount++, obj[14], style);
            createCell(row, columnCount++, obj[15], style);
            createCell(row, columnCount++, obj[16], style);
            createCell(row, columnCount++, obj[17], style);
            createCell(row, columnCount++, obj[18], style);
            /*createCell(row, columnCount++, record.getPpoNo(), style);
            createCell(row, columnCount++, record.getPensionBasic(), style);
            createCell(row, columnCount++, record.getDa(), style);
            createCell(row, columnCount++, record.getArrears(), style);
            createCell(row, columnCount++, record.getGrossTotalAmt(), style);
            createCell(row, columnCount++, record.getComm(), style);
            createCell(row, columnCount++, record.getOtherRec(), style);
            createCell(row, columnCount++, record.getPt(), style);
            createCell(row, columnCount++, record.getIncomeTax(), style);
            createCell(row, columnCount++, record.getTotalDeduction(), style);
            createCell(row, columnCount++, record.getTotalNetAmt(), style);
            createCell(row, columnCount++, record.getBankName(), style);
            createCell(row, columnCount++, record.getIfscCode(), style);
            createCell(row, columnCount++ , record.getAccountNo(), style);*/
            i++;
        }
    }
    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
