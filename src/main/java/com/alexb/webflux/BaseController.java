package com.alexb.webflux;

import com.alexb.webflux.data.Chapter;
import com.alexb.webflux.repository.ChapterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@Slf4j
public class BaseController {

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/images")
    Flux<Image> images() {
        return Flux.just(
                new Image("1", "learning-spring-boot-cover.jpg"),
                new Image("2", "learning-spring-boot-2nd-edition-cover.jpg"),
                new Image("3", "bazinga.jpg")
        );
    }

    @GetMapping(value = "/images/{name}",produces = MediaType.IMAGE_JPEG_VALUE)
    Mono<ResponseEntity> image(@PathVariable String name) {
        return Mono.just(
                resourceLoader.getResource(String.format("%s.jpg", name)))
                .map(resource -> {
                    try {
                        return ResponseEntity.ok().contentLength(resource.contentLength())
                                .body(new InputStreamResource(resource.getInputStream()));
                    } catch (IOException e) {
                        return null;
                    }
                });
    }

    @GetMapping("/chapters")
    Flux<Chapter> getChapters() {
        log.info("request processed");
        return chapterRepository.findAll();
    }

    @PostMapping("/images")
    Mono<Void> saveImages(@RequestBody Flux<Image> images) throws InterruptedException {
        return images.map(image -> {
            log.info(Thread.currentThread() + "// " + "Will be saved soon " + images);
            return image;
        }).then();
    }
}
