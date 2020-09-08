package com.trendyflow.keyword.controller;

import com.trendyflow.keyword.service.KeywordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    @RequestMapping("/")
    public String getKeyword() {
        return "Keyword Information";
    }
    @ApiOperation(value = "키워드 검색 ", notes = " 연관 키워드 보여주기 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "키워드 이름", paramType = "path", required = true)
    })
    //TODO: 캐싱 조건 추가(최근 검색일이 하루이내 일시 캐시서버에서 바로 데이터 반환)
    @Cacheable(value = "keywordCache" , key = "#query")
    @GetMapping("/{query}")
    public ResponseEntity<Void> getkeyword(HttpServletRequest request,

                                                                   @PathVariable String query, @RequestParam String last_date) {
        //캐싱 된 키워드 일 시에 이미 저장된 리턴값을 반환

        //캐싱 된게 아닐경우 검색어 와 검색일 저장
        keywordService.saveKeyword(query,LocalDateTime.now());

        //TODO: 플라스트 웹서버 api 호출

        //TODO: ES 데이터 반환
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
