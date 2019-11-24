package com.practice.elasticsearch.elasticsearchpractice.repository;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith({SpringExtension.class})
class MediaRepositoryTest {

    @Autowired
    private MediaRepository mediaRepository;

    @Test
    void search() {
        Iterable<Media> result = mediaRepository.search(QueryBuilders.matchQuery("customLabels", "vocation"));
        for (Media media : result) {
            System.out.println(media);

        }


    }
}