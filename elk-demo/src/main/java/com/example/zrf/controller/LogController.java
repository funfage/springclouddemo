package com.example.zrf.controller;

import com.example.zrf.entity.LogEntity;
import com.example.zrf.repository.LogRepository;
import com.example.zrf.service.NotifyService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("log")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private NotifyService notifyService;



    @PostMapping("/add")
    public String add() {
        LogEntity log = new LogEntity();
        log.setId("1");
        log.setApp("app");
        log.setHost("host");
        log.setLevel("info");
        log.setDateTime(new Date());
        log.setPort(13432);
        log.setProfile("pro");
        log.setMethod("get");
        log.setTraceId("123");
//        log.setDateTime(new Date());
        logRepository.save(log);
        return "success";
    }

    @GetMapping("/get/{id}")
    public String getById(@PathVariable String id) {
        MatchQueryBuilder query = QueryBuilders.matchQuery("level", "ERROR");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        SearchHits<LogEntity> search = elasticsearchRestTemplate.search(nativeSearchQuery, LogEntity.class);
        List<SearchHit<LogEntity>> searchHits = search.getSearchHits();
        System.out.println(searchHits);
        return "success";
    }

    @GetMapping("/get/error")
    public String getError(String beginTime, String endTime) {
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("level", "ERROR");
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("dateTime").gt(beginTime).lt(endTime);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().must(matchQuery).must(rangeQuery);
        FieldSortBuilder fieldSort = SortBuilders.fieldSort("dateTime").order(SortOrder.ASC);
        Query nativeSearchQuery = new NativeSearchQueryBuilder().withSort(fieldSort).withQuery(boolQuery).build();
        SearchHits<LogEntity> search = elasticsearchOperations.search(nativeSearchQuery, LogEntity.class, IndexCoordinates.of("springboot-logstash-logerror"));
        notifyService.notifyError(search);
        return "success";
    }

    @GetMapping("/get/info")
    public String getInfo(String beginTime, String endTime) {
        MatchQueryBuilder query = QueryBuilders.matchQuery("level", "INFO");
        Query nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        SearchHits<LogEntity> search = elasticsearchOperations.search(nativeSearchQuery, LogEntity.class, IndexCoordinates.of("springboot-logstash-loginfo"));
        notifyService.notifyError(search);
        return "success";
    }

    @GetMapping("/get/warn")
    public String getWarn(String beginTime, String endTime) {
        MatchQueryBuilder query = QueryBuilders.matchQuery("level", "WARN");
        Query nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        SearchHits<LogEntity> search = elasticsearchOperations.search(nativeSearchQuery, LogEntity.class, IndexCoordinates.of("springboot-logstash-logwarn"));
        notifyService.notifyError(search);
        return "success";
    }

}
