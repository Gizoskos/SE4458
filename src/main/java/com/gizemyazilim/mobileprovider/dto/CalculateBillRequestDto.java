package com.gizemyazilim.mobileprovider.dto;

import lombok.Data;

@Data
public class CalculateBillRequestDto {
    private Long subscriberNo;
    private String month;
    private int year;

    public CalculateBillRequestDto() {}

    public Long getSubscriberNo() { return subscriberNo; }
    public void setSubscriberNo(Long subscriberNo) { this.subscriberNo = subscriberNo; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
