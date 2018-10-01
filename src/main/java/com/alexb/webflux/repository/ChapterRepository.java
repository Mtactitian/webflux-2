package com.alexb.webflux.repository;

import com.alexb.webflux.data.Chapter;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChapterRepository extends ReactiveMongoRepository<Chapter, String> {
}
