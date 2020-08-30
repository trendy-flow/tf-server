package com.trendyflow.keyword.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.python.util.PythonInterpreter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KeywordController {
    private static PythonInterpreter interpreter;
    @RequestMapping("/")
    public String getKeyword() {
        return "Keyword Information";
    }
    // TODO: 키워드 파이썬 api 연결
    @ApiOperation(value = "키워드 검색 ", notes = " 연관 키워드 보여주기 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "키워드 이름", paramType = "path", required = true)
    })
    @GetMapping("/{query}")
    public ResponseEntity<Void> getkeyword(HttpServletRequest request,
                                                                   @PathVariable String query, @RequestParam String last_date) {
        /*
         interpreter = new PythonInterpreter();
         interpreter.execfile("/bigdata/datacollector/naver_api.py");
         PyFunction pyFunction = (PyFunction) interpreter.get("search_blog",PyFunction.class);
         PyObject  pyObject = pyFunction.__call__(new PyString(query) , new PyString(last_date));
         System.out.println(pyObject.toString());
         */
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
