package com.trendyflow.keyword.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BucketResponse {
    private String key;
    private Long docCount;
}