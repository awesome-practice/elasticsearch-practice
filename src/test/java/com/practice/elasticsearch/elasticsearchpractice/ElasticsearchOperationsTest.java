/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.practice.elasticsearch.elasticsearchpractice;

import com.practice.elasticsearch.elasticsearchpractice.model.Conference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test case to show Spring Data Elasticsearch functionality.
 *
 * @author Christoph Strobl
 */
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchPracticeApplication.class)
@ExtendWith(SpringExtension.class)
public class ElasticsearchOperationsTest {
//    @ClassRule
//    public static ElasticsearchAvailable elasticsearchAvailable = ElasticsearchAvailable.onLocalhost();

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ElasticsearchRestTemplate operations;

    @Test
    public void textSearch() throws ParseException {

        String expectedDate = "2014-10-29";
        String expectedWord = "java";
        CriteriaQuery query = new CriteriaQuery(
                new Criteria("keywords").contains(expectedWord).and(new Criteria("date").greaterThanEqual(expectedDate)));

        List<Conference> result = operations.queryForList(query, Conference.class);

        assertThat(result, hasSize(3));

        for (Conference conference : result) {
            assertThat(conference.getKeywords(), hasItem(expectedWord));
            assertThat(format.parse(conference.getDate()), greaterThan(format.parse(expectedDate)));
        }
    }
}
