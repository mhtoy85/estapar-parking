package com.estapar.parking.domain.model;

public class Spot {
    private Long id;
    private double lat;
    private double lng;
    private Sector sector;

    public Spot() {}

    public Spot(double lat, double lng, Sector sector) {
        this.lat = lat;
        this.lng = lng;
        this.sector = sector;
    }

    public Long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public Sector getSector() {
        return sector;
    }
}