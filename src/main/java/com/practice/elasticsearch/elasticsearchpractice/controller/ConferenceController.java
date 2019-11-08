package com.practice.elasticsearch.elasticsearchpractice.controller;

import com.practice.elasticsearch.elasticsearchpractice.model.Conference;
import com.practice.elasticsearch.elasticsearchpractice.repository.ConferenceRepository;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Luo Bao Ding
 * @since 2019/11/8
 */
@RestController
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

    @GetMapping("/searchName")
    public Iterable<Conference> search(@RequestParam("name") String name) {
        QueryBuilder query=new FuzzyQueryBuilder("name",name);
       return conferenceRepository.search(query);
    }
}
