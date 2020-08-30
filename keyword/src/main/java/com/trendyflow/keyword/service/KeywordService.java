package com.trendyflow.keyword.service;

import com.trendyflow.keyword.entity.Keyword;
import com.trendyflow.keyword.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KeywordService {

    @Autowired
    KeywordRepository keywordRepository;

    public void saveKeyword(String query , LocalDateTime refreshTime){
        Keyword keyword = Keyword.builder()
                        .keyword(query)
                        .refreshTime(refreshTime)
                        .build();

        keywordRepository.save(keyword);
    }

}
