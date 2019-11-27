package com.practice.elasticsearch.elasticsearchpractice.repository;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import com.practice.elasticsearch.elasticsearchpractice.repository.fragment.MediaFilterSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MediaRepository extends ElasticsearchRepository<Media, Long>, MediaFilterSearch {

    Media findByResourceId(long resourceId);
}
