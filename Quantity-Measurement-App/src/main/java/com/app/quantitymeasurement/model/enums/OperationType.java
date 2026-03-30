package com.app.quantitymeasurement.model.enums;

public enum OperationType {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    COMPARE,
    CONVERT;

    public String getDisplayName() {
        return this.name().toLowerCase();
    }

    // Safely parse from string, throws IllegalArgumentException with a clear message
    public static OperationType fromString(String value) {
        try {
            return OperationType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Invalid operation: '" + value + "'. Allowed: add, subtract, multiply, divide, compare, convert"
            );
        }
    }
}