package com.practice.elasticsearch.elasticsearchpractice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "media", type = "_doc", refreshInterval = "-1")
public class Media {

    @Id
    private Long resourceId;
    private String filename;
    private List<String> keywords;

    private List<String> customLabels;
    private List<String> detectLabels;
    private List<String> faceLabels;
    @Field(type = FieldType.Date)
    private String startDate;
    @Field(type = FieldType.Date)
    private String endDate;


}
