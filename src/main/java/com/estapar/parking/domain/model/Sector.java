package com.estapar.parking.domain.model;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Sector {

    private Long id;
    private String name;
    private BigDecimal basePrice;
    private int maxCapacity;
    private LocalTime openHour;
    private LocalTime closeHour;
    private int durationLimitMinutes;

    public Sector(){}

    public Sector(String name, BigDecimal basePrice, int maxCapacity, LocalTime openHour, LocalTime closeHour, int durationLimitMinutes) {
        this.name = name;
        this.basePrice = basePrice;
        this.maxCapacity = maxCapacity;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.durationLimitMinutes = durationLimitMinutes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public LocalTime getOpenHour() {
        return openHour;
    }

    public LocalTime getCloseHour() {
        return closeHour;
    }

    public int getDurationLimitMinutes() {
        return durationLimitMinutes;
    }
}