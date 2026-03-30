package com.app.quantitymeasurement.model.enums;

public enum Unit {
    // Length (Base: Meter)
    KM("LENGTH", 1000.0), M("LENGTH", 1.0), MILE("LENGTH", 1609.34),
    // Weight (Base: Kilogram)
    TON("WEIGHT", 1000.0), KG("WEIGHT", 1.0), G("WEIGHT", 0.001),
    // Volume (Base: Liter)
    GALLON("VOLUME", 3.78541), L("VOLUME", 1.0), ML("VOLUME", 0.001),
    // Temperature
    C("TEMPERATURE", 0.0), F("TEMPERATURE", 0.0), K("TEMPERATURE", 0.0);

    private final String type;
    private final double factor;

    Unit(String type, double factor) {
        this.type = type;
        this.factor = factor;
    }

    public String getType() { 
    	return type; 
    }

    public double toBase(double val) {
        if (this.type.equals("TEMPERATURE")) {
            if (this == F) return (val - 32) * 5 / 9;
            if (this == K) return val - 273.15;
            return val; // C is base
        }
        return val * factor;
    }

    public double fromBase(double baseVal) {
        if (this.type.equals("TEMPERATURE")) {
            if (this == F) return (baseVal * 9 / 5) + 32;
            if (this == K) return baseVal + 273.15;
            return baseVal; // C is base
        }
        return baseVal / factor;
    }
}