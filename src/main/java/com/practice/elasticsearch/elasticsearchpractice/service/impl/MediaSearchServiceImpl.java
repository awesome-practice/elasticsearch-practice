package com.practice.elasticsearch.elasticsearchpractice.service.impl;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import com.practice.elasticsearch.elasticsearchpractice.repository.MediaRepository;
import com.practice.elasticsearch.elasticsearchpractice.service.MediaSearchService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author Luo Bao Ding
 * @since 2019/11/27
 */
@Component
public class MediaSearchServiceImpl implements MediaSearchService {

    private final MediaRepository mediaRepository;
    private final RestHighLevelClient client;
    private final ElasticsearchRestTemplate template;


    public MediaSearchServiceImpl(MediaRepository mediaRepository, RestHighLevelClient client, ElasticsearchRestTemplate template) {
        this.mediaRepository = mediaRepository;
        this.client = client;
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
                .withPageable(PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "testDate")))
                .build();
        return template.queryForPage(searchQuery, Media.class);

    }

    @Override
    public void deleteByIds(List<Long> ids) {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(QueryBuilders.boolQuery().filter(
                QueryBuilders.termsQuery("resourceId", ids)
        ));

        template.delete(deleteQuery, Media.class);
    }

    @Override
    public void deleteByIdsByBulkByNative(List<Long> ids) {
        BulkRequest request = new BulkRequest(Media.class.getSimpleName().toLowerCase(), "_doc");
        for (Long id : ids) {
            request.add(new DeleteRequest().id(id + ""));
        }
        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("deleteByIdsByBulkByNative '" + ids + "' exception:", e);
        }

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
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateFilenameByScript(Long resourceId, String filename) {
        template.update(new UpdateQueryBuilder()
                .withId(resourceId + "")
                .withClass(Media.class)
                .withUpdateRequest(
                        new UpdateRequest()
                                .script(new Script("ctx._source.filename=\"" + filename + "\"")))
                .build());
    }

    @Override
    public void updateFilenameByBulk(List<Long> resourceIds, String filename) {

        List<UpdateQuery> updates = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            updates.add(new UpdateQueryBuilder()
                    .withId(resourceId + "")
                    .withClass(Media.class)
                    .withUpdateRequest(
                            new UpdateRequest()
                                    .script(new Script("ctx._source.filename=\"" + filename + "\"")))
                    .build());
        }

        template.bulkUpdate(updates);

    }
}
