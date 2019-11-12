package com.softteco.text.repository;

import com.softteco.text.model.Sentence;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SentenceRepository extends ElasticsearchRepository<Sentence, String> {

}