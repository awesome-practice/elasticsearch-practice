package com.practice.elasticsearch.elasticsearchpractice.controller;

import com.practice.elasticsearch.elasticsearchpractice.model.Conference;
import com.practice.elasticsearch.elasticsearchpractice.repository.ConferenceRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * java client文档见： https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high.html
 *
 * @author Luo Bao Ding
 * @since 2019/11/8
 */
@RestController("/Conference")
public class ConferenceController {
    private final ConferenceRepository conferenceRepository;

    public ConferenceController(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @GetMapping("/list")
    public Iterable<Conference> list() {
        return conferenceRepository.findAll();
    }

    @PostMapping("/save")
    public void save(Conference conference) {
        conferenceRepository.save(conference);
    }

    @GetMapping("/fuzzy")
    public Iterable<Conference> fuzzy(@RequestParam("name") String name) {
       return conferenceRepository.search(QueryBuilders.fuzzyQuery("name", name));
    }

    @GetMapping("/search")
    public Iterable<Conference> search(@RequestParam(name="tag",required = false) String tag,
                                       @RequestParam(name="value",required = false) String value) {
        if ("prefix".equals(tag)) {
           return conferenceRepository.search(QueryBuilders.prefixQuery("name", value));
        }
        if("fuzzy".equals(tag)){
            return conferenceRepository.search(QueryBuilders.fuzzyQuery("name", value));
        }
        if ("match".equals(tag)) {
            return conferenceRepository.search(QueryBuilders.matchQuery("name", value));
        }

        /*
       聚合搜索见：  https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.7/java-rest-high-aggregation-builders.html
         */
        return null;
    }
}
