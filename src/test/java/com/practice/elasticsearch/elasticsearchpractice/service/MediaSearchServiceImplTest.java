package com.practice.elasticsearch.elasticsearchpractice.service;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import com.practice.elasticsearch.elasticsearchpractice.repository.MediaRepository;
import com.practice.elasticsearch.elasticsearchpractice.service.MediaSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

/**
 * @author Luo Bao Ding
 * @since 2019/11/27
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
class MediaSearchServiceImplTest {
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaSearchService mediaSearchService;

    @Test
    void filterSearch() {
        Iterable<Media> medias = mediaSearchService.filterSearch();

        for (Media media : medias) {
            System.out.println(media);
        }


    }

    @Test
    void deleteByIdBatch() {
        count();
        mediaSearchService.deleteByIds(Arrays.asList(1L, 2L));
        count();

    }

    private void count() {
        Iterable<Media> all = mediaRepository.findAll();
        int count=0;
        for (Media media : all) {
            count++;
        }
        System.out.println("count = " + count);
    }

    @Test
    void updateFilenameByDoc() {
        mediaSearchService.updateFilenameByDoc(1L, "updateFilenameByDoc");

    }

    @Test
    void updateFilenameByScript() {
        mediaSearchService.updateFilenameByScript(1L,"updateFilenameByScript2");
        mediaRepository.refresh();

    }
}
