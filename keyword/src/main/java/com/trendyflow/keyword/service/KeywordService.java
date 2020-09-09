package com.trendyflow.keyword.service;

import com.trendyflow.keyword.entity.Keyword;
import com.trendyflow.keyword.model.BucketResponse;
import com.trendyflow.keyword.model.ElasticsearchSearchResponse;
import com.trendyflow.keyword.model.HitResponse;
import com.trendyflow.keyword.repository.KeywordRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class KeywordService {

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Qualifier("getRestClient")
    @Autowired
    RestHighLevelClient client;

    /**
     * 키워드 분석 요청 처리
     * @param keywordReq
     * @return
     */
    public Keyword getKeyword(String keywordReq) {
        Keyword keyword = keywordRepository.findById(keywordReq).orElse(null);

        // 1-1. 캐시 생성
        if (Objects.isNull(keyword)) {
            Keyword newKeyword = Keyword.builder()
                    .keyword(keywordReq)
                    .lastDate(LocalDate.now().minusDays(1))
                    .build();

            requestDataCollectorServer(newKeyword.getKeyword(), "19960101");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SearchResponse searchResponse = requestElasticsearchGetKeyword(keywordReq);
            ElasticsearchSearchResponse elasticsearchSearchResponse = new ElasticsearchSearchResponse();

            elasticsearchSearchResponse.setHitList(getHitResponse(searchResponse));
            elasticsearchSearchResponse.setBucketList(getBucketResponse(searchResponse));

            newKeyword.setAnalysisData(elasticsearchSearchResponse);
            keywordRepository.save(newKeyword);
            return keywordRepository.findById(newKeyword.getKeyword()).get();
        } else {
            // 1-2. 캐시 업데이트
            if (keyword.getLastDate().isBefore(LocalDate.now().minusDays(1))) {
                requestDataCollectorServer(keyword.getKeyword(), keyword.getLastDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

                SearchResponse searchResponse = requestElasticsearchGetKeyword(keywordReq);
                ElasticsearchSearchResponse elasticsearchSearchResponse = new ElasticsearchSearchResponse();
                List<HitResponse> hitResponseList = new ArrayList<>();
                for (SearchHit searchHit  : searchResponse.getHits()) {
                    hitResponseList.add(new HitResponse(searchHit.getSourceAsMap()));
                }
                elasticsearchSearchResponse.setHitList(hitResponseList);
                elasticsearchSearchResponse.setBucketList(getBucketResponse(searchResponse));

                keyword.setAnalysisData(elasticsearchSearchResponse);
                return keyword;
            // 1-3. 캐시 조회
            } else {
                return keywordRepository.findById(keyword.getKeyword()).orElse(null);
            }
        }
    }

    /**
     * 데이터 수집 서버 요청
     * @param keyword
     * @param lastDate
     * @return
     */
    public ResponseEntity<String> requestDataCollectorServer(String keyword, String lastDate) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("keyword", keyword);
        body.add("last_date", lastDate);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity("http://ec2-3-19-32-93.us-east-2.compute.amazonaws.com/", requestEntity, String.class);
    }

    /**
     * Elasticsearch 키워드 조회
     * @param keyword
     * @return
     */
    public SearchResponse requestElasticsearchGetKeyword(String keyword) {
        String index = "tf_blog_" + keyword;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("aggs").field("description").size(100);
        searchSourceBuilder.aggregation(aggregation);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    /**
     * Bucket Dto 변환
     * @param searchResponse
     * @return
     */
    public List<BucketResponse> getBucketResponse(SearchResponse searchResponse) {
        Terms terms = searchResponse.getAggregations().get("aggs");
        List<BucketResponse> bucketList = new ArrayList<>();

        for (Terms.Bucket bucket : terms.getBuckets()) {
            bucketList.add(new BucketResponse(bucket.getKey().toString(), bucket.getDocCount()));
        }
        return bucketList;
    }

    /**
     * Hit Dto 반환
     * @param searchResponse
     * @return
     */
    public List<HitResponse> getHitResponse(SearchResponse searchResponse) {
        List<HitResponse> hitResponseList = new ArrayList<>();
        for (SearchHit searchHit  : searchResponse.getHits()) {
            hitResponseList.add(new HitResponse(searchHit.getSourceAsMap()));
        }
        return hitResponseList;
    }
}
