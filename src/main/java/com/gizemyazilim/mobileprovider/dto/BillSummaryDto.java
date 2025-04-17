package com.gizemyazilim.mobileprovider.dto;
import lombok.Data;

@Data
public class BillSummaryDto {
    private double totalAmount;
    private boolean isPaid;

    public BillSummaryDto() {}

    public BillSummaryDto(double totalAmount, boolean isPaid) {
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
    }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
}
