package com.example.demo.infrastructure.jobs;

import com.example.demo.shared.exception.EmailException;
import com.example.demo.shared.utils.BaseEmailSender;
import com.example.demo.shared.utils.BaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScanFailureOrderJob {

  @Value("${demo.email.daily-failure-order-report.subject}")
  private String subject;

  @Value("${demo.email.daily-failure-order-report.template}")
  private String bodyTemplatePath;

  @Value("${demo.email.daily-failure-order-report.to-address}")
  private String toAddress;

  @Value("${demo.email.daily-failure-order-report.from-address}")
  private String fromAddress;

  @Value("${demo.email.daily-failure-order-report.cc-addresses}")
  private String[] ccAddresses;

  private final BaseEmailSender emailSender;

  /** scan daily failure orders, generate the report, and send email */
  @Scheduled(cron = "${demo.job.scan.failure.order}", zone = "Asia/Saigon")
  public void scan() throws EmailException {
    // demo sending email only
    String emailBody = BaseUtils.htmlToText(bodyTemplatePath);
    emailSender.sendEmail(fromAddress, toAddress, ccAddresses, subject, emailBody, null, null);
  }
}
