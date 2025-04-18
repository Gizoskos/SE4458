package com.gizemyazilim.mobileprovider.dto;

import lombok.Data;

@Data
public class BillDetailedDto {
    private String usageType;
    private int amount;

    public BillDetailedDto() {}

    public BillDetailedDto(String usageType, int amount) {
        this.usageType = usageType;
        this.amount = amount;
    }

    public String getUsageType() { return usageType; }
    public void setUsageType(String usageType) { this.usageType = usageType; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
