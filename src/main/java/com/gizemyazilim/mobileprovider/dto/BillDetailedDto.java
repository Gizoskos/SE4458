package com.gizemyazilim.mobileprovider.dto;

import lombok.Data;

@Data
public class BillDetailedDto {
    private String usageType;
    private int amount;
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getBillTotalAmount() {
        return billTotalAmount;
    }

    public void setBillTotalAmount(double billTotalAmount) {
        this.billTotalAmount = billTotalAmount;
    }

    private double billTotalAmount;


    public BillDetailedDto() {}

    public BillDetailedDto(String usageType, int amount, String unit, double billTotalAmount) {
        this.usageType = usageType;
        this.amount = amount;
        this.unit = unit;
        this.billTotalAmount = billTotalAmount;
    }

    public String getUsageType() { return usageType; }
    public void setUsageType(String usageType) { this.usageType = usageType; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
