package com.softteco.text.repository;

import com.softteco.text.model.FullResponse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FullResponseRepository extends ElasticsearchRepository<FullResponse, String> {

}