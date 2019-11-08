package com.practice.elasticsearch.elasticsearchpractice.datainitialization;

import com.practice.elasticsearch.elasticsearchpractice.model.Conference;
import com.practice.elasticsearch.elasticsearchpractice.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

/**
 * @author Luo Bao Ding
 * @since 2019/11/8
 */
@Component
public class ConferenceEsDataInitialization {
    private final ElasticsearchRestTemplate elasticsearchOperations;

    private final ConferenceRepository repository;

    public ConferenceEsDataInitialization(ElasticsearchRestTemplate elasticsearchOperations, ConferenceRepository repository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.repository = repository;
    }

    @PreDestroy
    public void deleteIndex() {
        elasticsearchOperations.deleteIndex(Conference.class);
    }

    @PostConstruct
    public void insertDataSample() {

        repository.deleteAll();
        elasticsearchOperations.refresh(Conference.class);

        // Save data sample
        repository.save(Conference.builder().date("2014-11-06").name("Spring eXchange 2014 - London")
                .keywords(Arrays.asList("java", "spring")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        repository.save(Conference.builder().date("2014-12-07").name("Scala eXchange 2014 - London")
                .keywords(Arrays.asList("scala", "play", "java")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        repository.save(Conference.builder().date("2014-11-20").name("Elasticsearch 2014 - Berlin")
                .keywords(Arrays.asList("java", "elasticsearch", "kibana")).location(new GeoPoint(52.5234051D, 13.4113999))
                .build());
        repository.save(Conference.builder().date("2014-11-12").name("AWS London 2014")
                .keywords(Arrays.asList("cloud", "aws")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        repository.save(Conference.builder().date("2014-10-04").name("JDD14 - Cracow")
                .keywords(Arrays.asList("java", "spring")).location(new GeoPoint(50.0646501D, 19.9449799)).build());
    }
}
