package com.fptacademy.training.domain;

public enum UserStatus {
    ON_BOARDING ("On boarding"),
    IN_CLASS ("In class"),
    OFF_CLASS ("Off class"),
    ACTIVE ("Active"),
    IN_ACTIVE ("In active"),
    ;

    private final String text;

    UserStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
