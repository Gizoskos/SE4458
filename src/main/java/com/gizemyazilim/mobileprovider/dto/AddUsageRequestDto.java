package com.gizemyazilim.mobileprovider.dto;

import lombok.Data;

@Data
public class AddUsageRequestDto {
    private Long subscriberNo;
    private String month;
    private int year;
    private String usageType;
    private int amount;

    public AddUsageRequestDto() {}
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public Long getSubscriberNo() { return subscriberNo; }
    public void setSubscriberNo(Long subscriberNo) { this.subscriberNo = subscriberNo; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getUsageType() { return usageType; }
    public void setUsageType(String usageType) { this.usageType = usageType; }
}
