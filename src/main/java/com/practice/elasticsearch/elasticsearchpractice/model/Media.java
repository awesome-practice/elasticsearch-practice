package com.practice.elasticsearch.elasticsearchpractice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "media", type = "_doc", refreshInterval = "-1")
public class Media {

    @Id
    private Long resourceId;
    private Long memberId;
    private String filename;
    private List<String> keywords;

    private List<String> customLabels;
    private List<String> detectLabels;
    private List<String> faceLabels;
    @Field(type = FieldType.Date)
    private String startDate;
    @Field(type = FieldType.Date)
    private String endDate;
    private Date  testDate;
    @Field(type = FieldType.Date)
    private Date testWithAnnotationDate;


    private Byte rank;
    @Field(type = FieldType.Byte)
    private byte libraryTypeMarker;
    @Field(type = FieldType.Byte)
    private byte testNoUseByte;
    @Field(type = FieldType.Byte)
    private Byte testNoUseByte2;

    private Boolean isOnShelf;
    @Field(type = FieldType.Boolean)
    private boolean isCopiedToTenant;
    private Boolean isCopiedToTenant2;
    @Field(type = FieldType.Boolean)
    private Boolean isCopiedToPlatform;




}
