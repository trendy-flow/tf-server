package com.trendyflow.keyword.controller;

import com.trendyflow.keyword.entity.Keyword;
import com.trendyflow.keyword.service.KeywordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    @RequestMapping("/")
    public String getKeyword() {
        return "Keyword Information";
    }

    @ApiOperation(value = "키워드 검색", notes = "연관 키워드 보여주기")
    @ApiImplicitParam(name = "query", value = "키워드 이름", paramType = "path", required = true)
    @GetMapping("/{keywordReq}")
    public ResponseEntity<Keyword> getKeyword(@PathVariable String keywordReq) {
        return new ResponseEntity<>(keywordService.getKeyword(keywordReq), HttpStatus.OK);
    }

}
