package study.java.util;

import study.java.model.Meal;
import study.java.model.MealWithExcess;

import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExcess(mealList, LocalTime.of(12, 0), LocalTime.of(23, 0), 2000)
                .forEach(m -> System.out.println(m.isExceeded()));
    }

    public static List<MealWithExcess> getFilteredWithExcess(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .collect(Collectors.groupingBy(Meal::getDate))
                .values()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        l -> l.stream().mapToInt(Meal::getCalories).sum()
                ))
                .entrySet()
                .stream()
                .flatMap(e -> e.getKey().stream()
                        .filter(m -> TimeUtil.isBetween(m.getTime(), startTime, endTime))
                        .map(m -> new MealWithExcess(m, e.getValue() > caloriesPerDay))
                )
                .sorted(Comparator.comparing(MealWithExcess::getDateTime))
                .collect(Collectors.toList());
    }
}
