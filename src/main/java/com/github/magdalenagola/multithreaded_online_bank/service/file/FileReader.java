package com.github.magdalenagola.multithreaded_online_bank.service.file;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
class FileReader {

    private final static String FILE_NAME = "src/main/resources/transactions.csv";
    private final LineParser lineParser;

    public FileReader(LineParser lineParser) {
        this.lineParser = lineParser;
    }

    public void read(){
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            scanner.nextLine(); // skip the titles
            while (scanner.hasNext()) {
               lineParser.parse(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
