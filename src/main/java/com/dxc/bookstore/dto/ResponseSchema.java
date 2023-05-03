package com.dxc.bookstore.dto;

import java.util.HashMap;
import java.util.Map;

public class ResponseSchema {
  private Integer status;
  private Object data;
  private String error;

  public ResponseSchema(Integer status) {
    this.status = status;
    this.data = null;
  }

  public ResponseSchema(Integer status, Object data) {
    this.status = status;
    this.data = data;
  }

  public ResponseSchema(Integer status, String error) {
    this.status = status;
    this.error = error;
  }

  // Helper function to format successful response
  public Map<String, Object> getSuccessResponse() {
    Map<String, Object> response = new HashMap<>();
    response.put("data", this.data);
    response.put("status", this.status);
    return response;
  }

  // Helper function to format error response
  public Map<String, Object> getErrorResponse() {
    Map<String, Object> response = new HashMap<>();
    response.put("error", this.error);
    response.put("status", this.status);
    return response;
  }
}