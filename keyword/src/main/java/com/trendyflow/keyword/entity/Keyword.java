package com.trendyflow.keyword.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@RedisHash("keyword")
public class Keyword implements Serializable {
    @Id
    private String id;
    private String keyword;
    private LocalDateTime refreshTime;

    @Builder
    public Keyword(String id, String keyword, LocalDateTime refreshTime) {
        this.id = id;
        this.keyword = keyword;
        this.refreshTime = refreshTime;
    }

}
