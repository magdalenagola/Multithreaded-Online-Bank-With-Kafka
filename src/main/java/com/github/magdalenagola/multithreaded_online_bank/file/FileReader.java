package com.github.magdalenagola.multithreaded_online_bank.file;

import com.github.magdalenagola.multithreaded_online_bank.repository.AccountRepository;
import com.github.magdalenagola.multithreaded_online_bank.transaction.ProducerService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class FileReader {

    private final static String FILE_NAME = "src/main/resources/transactions.csv";
    private final ExecutorService executorService = Executors.newFixedThreadPool(50);
    private final ProducerService producerService;

    public FileReader(ProducerService producerService) {
        this.producerService = producerService;
    }

    public void read(){
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
                Runnable converter = new Converter(producerService, scanner.nextLine());
                Future<?> future = executorService.submit(converter);
                try {
                    future.get();
                    if(!future.isDone())
                        throw new IllegalArgumentException("Unable to read transaction");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
