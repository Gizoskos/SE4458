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
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
    @ManyToOne
    private Subscriber subscriber;

    private String month;
    private int year;

    @Enumerated(EnumType.STRING)
    private UsageType type;

    private int amount;

    public Usage() {}

    public Usage(Long id, AppUser appUser, Subscriber subscriber, String month, int year, UsageType type, int amount) {
        this.id = id;
        this.subscriber = subscriber;
        this.appUser = appUser;
        this.month = month;
        this.year = year;
        this.type = type;
        this.amount = amount;
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

    public AppUser getAppUser() { return appUser; }
    public void setAppUser(AppUser appUser) { this.appUser = appUser; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
