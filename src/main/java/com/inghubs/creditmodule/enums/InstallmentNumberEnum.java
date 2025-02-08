package com.inghubs.creditmodule.enums;

public enum InstallmentNumberEnum {

    SIX(6),
    NINE(9),
    TWELVE(12),
    TWENTY_FOUR(24);

    private final int value;

    InstallmentNumberEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
