package com.practice.elasticsearch.elasticsearchpractice.repository.fragment;

import com.practice.elasticsearch.elasticsearchpractice.model.Media;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.util.List;

/**
 * @author Luo Bao Ding
 * @since 2019/11/27
 */
public interface MediaFilterSearch {

    Iterable<Media> filterSearch();

}
