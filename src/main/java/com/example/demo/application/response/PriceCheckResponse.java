package com.example.demo.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceCheckResponse extends BaseResponse {
  List<PriceCheckResult> results;
}
