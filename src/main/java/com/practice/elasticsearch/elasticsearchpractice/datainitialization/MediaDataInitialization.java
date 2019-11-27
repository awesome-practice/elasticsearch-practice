package com.practice.elasticsearch.elasticsearchpractice.datainitialization;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import com.practice.elasticsearch.elasticsearchpractice.repository.MediaRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.Date;

@Component
public class MediaDataInitialization {

    private final ElasticsearchRestTemplate template;

    private final MediaRepository repository;

    public MediaDataInitialization(ElasticsearchRestTemplate template, MediaRepository repository) {
        this.template = template;
        this.repository = repository;
    }

    @PreDestroy
    public void deleteIndex() {
        template.deleteIndex(Media.class);
    }

    @PostConstruct
    public void init() {
        repository.deleteAll();
        template.refresh(Media.class);
        repository.index(Media.builder().resourceId(1L)
                .memberId(1L)
                .filename("meeting_photo.png")
                .keywords(Arrays.asList("good", "red", "big"))
                .customLabels(Arrays.asList("building", "activity"))
                .detectLabels(Arrays.asList("porn", "politic"))
                .faceLabels(Arrays.asList("Jam", "Tom", "Marry"))
                .startDate("2019-07-10")
                .endDate("2019-07-11")
                .testDate(new Date())
                .testWithAnnotationDate(new Date())
                .rank((byte) 5)
                .libraryTypeMarker((byte) 1)
                .isOnShelf(true)
                .isCopiedToTenant(false)
                .isCopiedToTenant2(false)
                .build());
        repository.index(Media.builder().resourceId(2L)
                .filename("family_photo.png")
                .keywords(Arrays.asList("nice", "family", "father"))
                .customLabels(Arrays.asList("home", "party"))
                .faceLabels(Arrays.asList("John", "Henry", "Roger"))
                .startDate("2019-11-10")
                .endDate("2019-11-11")
                .rank((byte)5)
                .testDate(new Date())
                .build());
        repository.index(Media.builder().resourceId(3L)
                .filename("Mid-Autumn Festival.png")
                .keywords(Arrays.asList("festival", "rice dumpling", "Dragon Boat"))
                .customLabels(Arrays.asList("vocation", "salary"))
                .faceLabels(Arrays.asList("Family", "QuYuan"))
                .startDate("2019-08-15")
                .endDate("2019-08-16")
                .rank((byte)5)
                .testDate(new Date())
                .build());
        repository.index(Media.builder().resourceId(4L)
                .filename("national.png")
                .keywords(Arrays.asList("smile", "festival", "national day"))
                .customLabels(Arrays.asList("vocation", "fat"))
                .faceLabels(Arrays.asList("xjp", "wjb", "lg"))
                .startDate("2019-10-01")
                .endDate("2019-10-07")
                .rank((byte)5)
                .testDate(new Date())
                .build());

        template.refresh(Media.class);

    }

}
