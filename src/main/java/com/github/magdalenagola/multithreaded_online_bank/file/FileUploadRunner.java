package com.github.magdalenagola.multithreaded_online_bank.file;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FileUploadRunner implements CommandLineRunner {

    private final FileReader fileReader;

    public FileUploadRunner(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public void run(String... args) throws Exception {
        fileReader.read();
    }
}

