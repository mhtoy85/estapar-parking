package com.estapar.parking.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ParkingEvent {
    private Long id;
    private String licensePlate;
    private OffsetDateTime entryTime;
    private OffsetDateTime exitTime;
    private BigDecimal price;
    private Sector sector;
    private Spot spot;

    public ParkingEvent() {}

    public ParkingEvent(String licensePlate, OffsetDateTime entryTime, Sector sector) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.sector = sector;
    }

    public ParkingEvent(Long id, OffsetDateTime exitTime, BigDecimal price) {
        this.id = id;
        this.exitTime = exitTime;
        this.price = price;
    }

    public ParkingEvent(Long id, Spot spot) {
        this.id = id;
        this.spot = spot;
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public OffsetDateTime getEntryTime() {
        return entryTime;
    }

    public OffsetDateTime getExitTime() {
        return exitTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Sector getSector() {
        return sector;
    }

    public Spot getSpot() {
        return spot;
    }
}