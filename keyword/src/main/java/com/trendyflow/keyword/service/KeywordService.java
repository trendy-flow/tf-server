package com.trendyflow.keyword.service;

import com.trendyflow.keyword.entity.Keyword;
import com.trendyflow.keyword.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class KeywordService {

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void saveKeyword(String query, LocalDateTime refreshTime) {
        Keyword keyword = Keyword.builder()
                .keyword(query)
                .refreshTime(refreshTime)
                .build();

        keywordRepository.save(keyword);
    }

    public Keyword getKeyword(String keywordReq) {
        Keyword keyword = keywordRepository.findById(keywordReq).orElse(null);
        if (Objects.isNull(keyword)) {
            Keyword newKeyword = Keyword.builder()
                    .keyword(keywordReq)
                    .refreshTime(LocalDateTime.now())
                    .build();
            keywordRepository.save(newKeyword);
            return keywordRepository.findById(newKeyword.getKeyword()).get();
        } else {
            if (keyword.getRefreshTime().toLocalDate().isBefore(LocalDate.now())) {

                return keyword;
            } else {
                return keyword;
            }
        }
    }

}
