package study.java.model;

import java.time.LocalDateTime;

public class MealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealWithExcess(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = exceed;
    }

    public MealWithExcess(Meal Meal, boolean exceed) {
        this.dateTime = Meal.getDateTime();
        this.description = Meal.getDescription();
        this.calories = Meal.getCalories();
        this.excess = exceed;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceeded() {
        return excess;
    }

}
