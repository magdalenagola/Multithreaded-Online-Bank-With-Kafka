package com.github.magdalenagola.multithreaded_online_bank.service.file;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class FileUploadRunner implements CommandLineRunner {

    private final FileReader fileReader;

    public FileUploadRunner(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(3000);
        fileReader.read();
    }
}

