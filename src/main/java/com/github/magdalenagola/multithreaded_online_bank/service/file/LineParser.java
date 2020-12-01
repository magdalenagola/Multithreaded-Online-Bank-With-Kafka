package com.github.magdalenagola.multithreaded_online_bank.service.file;

import com.github.magdalenagola.multithreaded_online_bank.service.ProducerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
class LineParser {
    private final ExecutorService executorService = Executors.newFixedThreadPool(6);
    private final ProducerService producerService;

    public LineParser(ProducerService producerService) {
        this.producerService = producerService;
    }

    public void parse(String line){
        Runnable converter = new Converter(producerService,line);
        executorService.submit(converter);
    }
}
