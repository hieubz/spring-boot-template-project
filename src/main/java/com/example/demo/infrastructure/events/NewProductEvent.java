package com.example.demo.infrastructure.events;

import com.example.demo.application.request.NewProductRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class NewProductEvent extends ApplicationEvent {

  private String requestId;
  private NewProductRequest request;

  public NewProductEvent(Object source, String requestId, NewProductRequest request) {
    super(source);
    this.requestId = requestId;
    this.request = request;
  }
}
