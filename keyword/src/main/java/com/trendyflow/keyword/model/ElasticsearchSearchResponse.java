package com.trendyflow.keyword.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ElasticsearchSearchResponse {
    private List<HitResponse> hitList;
    private List<BucketResponse> bucketList;
}
