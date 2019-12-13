package study.java.util;

import study.java.model.UserMeal;
import study.java.model.UserMealWithExceed;

import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(12, 0), LocalTime.of(23, 0), 2000)
                .forEach(m -> System.out.println(m.isExceed()));
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate))
                .values()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        l -> l.stream().mapToInt(UserMeal::getCalories).sum()
                ))
                .entrySet()
                .stream()
                .flatMap(e -> e.getKey().stream()
                        .filter(m -> TimeUtil.isBetween(m.getTime(), startTime, endTime))
                        .map(m -> new UserMealWithExceed(m, e.getValue() > caloriesPerDay))
                )
                .sorted(Comparator.comparing(UserMealWithExceed::getDateTime))
                .collect(Collectors.toList());

    }
}
