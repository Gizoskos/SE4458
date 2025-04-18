package com.gizemyazilim.mobileprovider.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Usage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subscriber subscriber;
    private boolean billed = false;
    private String month;
    private int year;

    @Enumerated(EnumType.STRING)
    private UsageType type;

    private int amount;

    public Usage() {}

    public Usage(Long id, Subscriber subscriber, String month, int year, UsageType type, int amount, boolean billed) {
        this.id = id;
        this.subscriber = subscriber;
        this.month = month;
        this.year = year;
        this.type = type;
        this.amount = amount;
        this.billed = billed;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Subscriber getSubscriber() { return subscriber; }
    public void setSubscriber(Subscriber subscriber) { this.subscriber = subscriber; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public UsageType getType() { return type; }
    public void setType(UsageType type) { this.type = type; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public boolean isBilled() {
        return billed;
    }

    public void setBilled(boolean billed) {
        this.billed = billed;
    }
}
