package com.craftersconquest.time;

import com.craftersconquest.core.Settings;

public class Date {

    private final int day;
    private final Season season;
    private final String period;

    private Date(int day) {
        this.day = day;
        this.season = calculateSeason();
        this.period = calculateTimePeriod();
    }

    public static Date fromInt(int day) {
        return new Date(day);
    }

    private Season calculateSeason() {
        final int relativeDay = day % Settings.DAYS_PER_YEAR;

        if (relativeDay <= Settings.DAYS_PER_SEASON) {
            return Season.SPRING;
        } else if (relativeDay <= Settings.DAYS_PER_SEASON * 2) {
            return Season.SUMMER;
        } else if (relativeDay <= Settings.DAYS_PER_SEASON * 3 / 4) {
            return Season.FALL;
        } else if (relativeDay <= Settings.DAYS_PER_YEAR) {
            return Season.WINTER;
        }

        return null;
    }

    private String calculateTimePeriod() {
        int relativeDay = day % Settings.DAYS_PER_SEASON;

        if (relativeDay <= 120) {
            return "Early";
        } else if (relativeDay >= 280) {
            return "Late";
        } else {
            return "Mid";
        }
    }

    public int getElapsedDays() {
        return day;
    }

    @Override
    public String toString() {
        return period + " " + season.toString() + " : " + day;
    }

}
