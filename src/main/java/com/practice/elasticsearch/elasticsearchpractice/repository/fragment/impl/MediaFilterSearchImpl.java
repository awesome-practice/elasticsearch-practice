package com.practice.elasticsearch.elasticsearchpractice.repository.fragment.impl;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import com.practice.elasticsearch.elasticsearchpractice.repository.fragment.MediaFilterSearch;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author Luo Bao Ding
 * @since 2019/11/27
 */
@Component
public class MediaFilterSearchImpl implements MediaFilterSearch {


    private final ElasticsearchRestTemplate template;

    public MediaFilterSearchImpl(ElasticsearchRestTemplate template) {
        this.template = template;
    }

    /**
     * filter: libraryTypeMarker, isOnShelf，isCopiedToTenant， rank，startDate
     * query： keywords,customLabels， ai labels (detectLabels，faceLabels)
     */
    @Override
    public Iterable<Media> filterSearch() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, 2019);
        calendar2.set(Calendar.MONTH, 11);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(boolQuery()
                        .must(termQuery("libraryTypeMarker", (byte) 1))
                        .must(termQuery("isOnShelf", true))
                        .must(termQuery("isCopiedToTenant2", false))
                        .must(termQuery("isCopiedToTenant", false))
                        .must(termQuery("rank", 5))
                        .must(rangeQuery("testDate").gte(calendar.toInstant().toEpochMilli()).lte(calendar2.toInstant().toEpochMilli()))
                        .must(rangeQuery("testWithAnnotationDate").gte(calendar.getTime()).lte(calendar2.getTime()))
                )
                .withQuery(boolQuery()
                        .should(matchQuery("keywords", "good"))
                        .should(matchQuery("customLabels", "activity"))
                        .should(matchQuery("detectLabels", "porn"))
                        .should(matchQuery("faceLabels", "Jam"))
                )
                .build();
        AggregatedPage<Media> medias = template.queryForPage(searchQuery, Media.class);
        return medias;

    }

    @Override
    public void deleteByIdBatch(List<Long> ids) {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(QueryBuilders.boolQuery().filter(
                QueryBuilders.termsQuery("resourceId", ids)
        ));

        template.delete(deleteQuery, Media.class);
    }

    @Override
    public void updateFilenameByDoc(Long resourceId, String filename) {

        try {
            template.update(new UpdateQueryBuilder().withId(resourceId + "")
                    .withClass(Media.class)
                    .withUpdateRequest(
                            new UpdateRequest()
                                    .doc(
                                            XContentFactory.jsonBuilder().startObject()
                                                    .field("filename", filename)
                                                    .endObject())
                    )
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateFilenameByScript(Long resourceId, String filename) {
        template.update(new UpdateQueryBuilder()
                .withId(resourceId + "")
                .withClass(Media.class)
                .withUpdateRequest(
                        new UpdateRequest()
                                .script(new Script("ctx._source.filename=\""+ filename + "\"")))
                .build());
    }
}
