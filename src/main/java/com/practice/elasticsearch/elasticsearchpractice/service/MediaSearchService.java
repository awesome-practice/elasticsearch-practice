package com.practice.elasticsearch.elasticsearchpractice.service;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;

import java.util.List;

/**
 * @author Luo Bao Ding
 * @since 2019/11/27
 */
public interface MediaSearchService {

    Iterable<Media> filterSearch();

    void deleteByIds(List<Long> ids);

    void deleteByIdsByBulkByNative(List<Long> ids);

    void updateFilenameByDoc(Long resourceId, String filename);

    void updateFilenameByScript(Long resourceId, String filename);


    void updateFilenameByBulk(List<Long> resourceIds, String filename);
}
