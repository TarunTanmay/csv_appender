package com.test_csv.service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    @Value("${excel.file.path}")
    private String EXCEL_FILE_PATH;

    // Method to append new data to the Excel file
    public void appendToExcel(List<String> newData) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(EXCEL_FILE_PATH);

        // Create workbook from the existing file
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

        // Create a new row at the end of the sheet
        int lastRowNum = sheet.getLastRowNum();
        Row newRow = sheet.createRow(lastRowNum + 1); // Create a row after the last row

        // Append new data to the row
        for (int i = 0; i < newData.size(); i++) {
            Cell cell = newRow.createCell(i);
            cell.setCellValue(newData.get(i));
        }

        // Write the updated workbook to the file
        fileInputStream.close(); // Close the input stream before writing
        FileOutputStream fileOutputStream = new FileOutputStream(EXCEL_FILE_PATH);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        workbook.close();
    }

    public List<List<String>> readFromExcel() throws IOException {
        List<List<String>> data = new ArrayList<>();

        // Open the Excel file
        FileInputStream fileInputStream = new FileInputStream(EXCEL_FILE_PATH);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

        // Iterate through each row
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();

            // Iterate through each cell in the row
            for (Cell cell : row) {
                rowData.add(cell.toString()); // Add cell value to the row data
            }
            data.add(rowData); // Add row data to the list
        }

        workbook.close();
        fileInputStream.close();

        return data;
    }
}
