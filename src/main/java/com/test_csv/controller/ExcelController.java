package com.test_csv.controller;

import com.test_csv.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    // Endpoint to append new data to the Excel file
    @PostMapping("/append")
    public String appendData(@RequestBody List<String> newData) {
        try {
            excelService.appendToExcel(newData);
            return "Data appended successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error appending data to Excel file.";
        }
    }

    @GetMapping("/read")
    public List<List<String>> readCsv() {
        try{
            return excelService.readFromExcel();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
