package com.example.zrf.service;

import com.example.zrf.entity.LogEntity;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {

    public void notifyError(SearchHits<LogEntity> searchHits) {
        for (SearchHit<LogEntity> searchHit : searchHits) {
            LogEntity logEntity = searchHit.getContent();
            System.out.println(logEntity);
            System.out.println(logEntity.getDateTime());
            System.out.println(logEntity.getLevel());
            System.out.println(logEntity.getMessage());
        }
    }

}
