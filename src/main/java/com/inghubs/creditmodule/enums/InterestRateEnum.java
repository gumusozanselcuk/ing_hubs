package com.inghubs.creditmodule.enums;

public enum InterestRateEnum {

    ZERO_POINT_ONE(0.1),
    ZERO_POINT_TWO(0.2),
    ZERO_POINT_THREE(0.3),
    ZERO_POINT_FOUR(0.4),
    ZERO_POINT_FIVE(0.5);


    private final double value;

    InterestRateEnum(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
