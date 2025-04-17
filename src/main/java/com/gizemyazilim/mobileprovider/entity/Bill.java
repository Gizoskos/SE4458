package com.gizemyazilim.mobileprovider.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subscriber subscriber;

    private String month;
    private int year;

    private int phoneMinutes;
    private int internetMb;

    private double totalAmount;
    private boolean isPaid;

    public Bill() {}

    public Bill(Long id, Subscriber subscriber, String month, int year, int phoneMinutes, int internetMb, double totalAmount, boolean isPaid) {
        this.id = id;
        this.subscriber = subscriber;
        this.month = month;
        this.year = year;
        this.phoneMinutes = phoneMinutes;
        this.internetMb = internetMb;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Subscriber getSubscriber() { return subscriber; }
    public void setSubscriber(Subscriber subscriber) { this.subscriber = subscriber; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getPhoneMinutes() { return phoneMinutes; }
    public void setPhoneMinutes(int phoneMinutes) { this.phoneMinutes = phoneMinutes; }

    public int getInternetMb() { return internetMb; }
    public void setInternetMb(int internetMb) { this.internetMb = internetMb; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
}
