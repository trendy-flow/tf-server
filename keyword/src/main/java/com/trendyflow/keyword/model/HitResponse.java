package com.trendyflow.keyword.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class HitResponse {
    Map<String, Object> hit;
}
