package com.example.demo.infrastructure.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ShowCurrentTimeSchedule {

  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Scheduled(initialDelay = 1000, fixedDelay = 200000000)
  public void showCurrentTime() {
    Date d = new Date();
    System.out.println(df.format(d));
  }

  @Scheduled(cron = "0 0 0 14 2 ?", zone = "Asia/Saigon")
  public void doSomethingInValentineDay() {
    System.out.println("go for a walk");
  }
}
