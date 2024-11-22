package com.test_csv.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class CsvController {

    @Value("${csv.file.path}")
    private String csvFilePath;  // The file path can be provided from application.properties

    // Endpoint to append new data to the CSV file
    @PostMapping("/append")
    public String appendToCsv(@RequestBody List<String> newData) {
        try {
            FileWriter writer = new FileWriter(csvFilePath, true); // Open in append mode (true)
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            // Write the new data to the CSV file
            csvPrinter.printRecord(newData);

            // Close the printer and writer
            csvPrinter.flush();
            csvPrinter.close();
            writer.close();

            return "Data appended successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error appending data to CSV";
        }
    }

    // Endpoint to read CSV content (for debugging purposes)
    @GetMapping("/read")
    public List<List<String>> readCsv() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        Iterable<org.apache.commons.csv.CSVRecord> records = format.parse(reader);

        List<List<String>> data = new ArrayList<>();
        for (org.apache.commons.csv.CSVRecord record : records) {
            data.add(Arrays.asList(record.get(0), record.get(1), record.get(2))); // Assuming 3 columns
        }

        reader.close();
        return data;
    }
}
