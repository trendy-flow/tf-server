package com.trendyflow.keyword.repository;

import com.trendyflow.keyword.entity.Keyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword,String> {
}
