package com.gizemyazilim.mobileprovider.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;


    public Subscriber() {}

    public Subscriber(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}
