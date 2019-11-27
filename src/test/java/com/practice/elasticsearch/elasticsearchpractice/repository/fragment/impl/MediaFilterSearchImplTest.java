package com.practice.elasticsearch.elasticsearchpractice.repository.fragment.impl;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import com.practice.elasticsearch.elasticsearchpractice.repository.MediaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Luo Bao Ding
 * @since 2019/11/27
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
class MediaFilterSearchImplTest {
    @Autowired
    private MediaRepository mediaRepository;

    @Test
    void filterSearch() {
        Iterable<Media> medias = mediaRepository.filterSearch();

        for (Media media : medias) {
            System.out.println(media);
        }


    }
}
