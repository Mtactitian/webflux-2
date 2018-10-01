package com.alexb.webflux;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Flux.just("one","two","three")
                .subscribeOn(Schedulers.single())
                .subscribe(s -> System.out.println(Thread.currentThread() + "// " + s));
        Thread.sleep(1000);
    }
}
