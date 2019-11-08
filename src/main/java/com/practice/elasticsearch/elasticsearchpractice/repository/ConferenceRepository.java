package com.practice.elasticsearch.elasticsearchpractice.repository;

import com.practice.elasticsearch.elasticsearchpractice.model.Conference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Christoph Strobl
 */
public interface ConferenceRepository extends ElasticsearchRepository<Conference, String> {
    List<Conference> findByName(String name);


}
