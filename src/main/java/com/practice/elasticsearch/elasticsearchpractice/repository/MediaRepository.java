package com.practice.elasticsearch.elasticsearchpractice.repository;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MediaRepository extends ElasticsearchRepository<Media, Long> {

    Media findByResourceId(long resourceId);
}
