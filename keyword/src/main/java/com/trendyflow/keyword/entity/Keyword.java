package com.trendyflow.keyword.entity;

import com.trendyflow.keyword.model.ElasticsearchSearchResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@RedisHash("keyword")
public class Keyword implements Serializable {
    @Id
    private String keyword;
    private LocalDate lastDate;
    private ElasticsearchSearchResponse analysisData;

    @Builder
    public Keyword(String keyword, LocalDate lastDate) {
        this.keyword = keyword;
        this.lastDate = lastDate;
    }

    public void setAnalysisData(ElasticsearchSearchResponse analysisData) {
        this.analysisData = analysisData;
    }
}