package com.practice.elasticsearch.elasticsearchpractice.repository.fragment.impl;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import com.practice.elasticsearch.elasticsearchpractice.repository.MediaRepository;
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

    @Test
    void deleteByIdBatch() {
        count();
        mediaRepository.deleteByIdBatch(Arrays.asList(1L, 2L));
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
        mediaRepository.updateFilenameByDoc(1L, "updateFilenameByDoc");

    }

    @Test
    void updateFilenameByScript() {
        mediaRepository.updateFilenameByScript(1L,"updateFilenameByScript2");
        mediaRepository.refresh();

    }
}
