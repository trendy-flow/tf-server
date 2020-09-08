package com.trendyflow.keyword.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@RedisHash("keyword")
public class Keyword implements Serializable {
    @Id
    private String keyword;
    private LocalDateTime refreshTime;
    private String analysisData;

    @Builder
    public Keyword(String keyword, LocalDateTime refreshTime) {
        this.keyword = keyword;
        this.refreshTime = refreshTime;
    }

    public void setAnalysisData(String analysisData) {
        this.analysisData = analysisData;
    }
}