package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,11,0), "Завтрак", 2000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<MealWithExceed> l = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(23,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
        /*for (MealWithExceed umwe: l
             ) {
            System.out.println(umwe.getDateTime() + "   " + umwe.getCalories() + "   " + umwe.isExceed());
        }*/

        l.forEach(x->System.out.println(x.getDateTime() + "   " +
        x.getCalories() + "   " + x.isExceed()));

        System.out.println("----------------------------------------");
        List<MealWithExceed> l2 = getFilteredWithExceededWithStream(mealList, LocalTime.of(7, 0), LocalTime.of(23,0), 2000);
        l.forEach(x->System.out.println(x.getDateTime() + "   " +
                x.getCalories() + "   " + x.isExceed()));
    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map<LocalDate, Integer> mapDateCalories = new HashMap<>();
        Map<LocalDate, AtomicBoolean> mapDateBoolean = new HashMap<>();


        List<MealWithExceed> listUM = new ArrayList<>();

        for (Meal um: mealList)
        {
            LocalDate ld = um.getDateTime().toLocalDate();
            if(mapDateCalories.containsKey(ld))
            {
                mapDateCalories.put(ld, mapDateCalories.get(ld) + um.getCalories());
                mapDateBoolean.get(ld).set(mapDateCalories.get(ld)>caloriesPerDay);
            }
            else
            {
                mapDateCalories.put(ld, um.getCalories());
                mapDateBoolean.put(ld, new AtomicBoolean((mapDateCalories.get(ld)>caloriesPerDay)));
            }

            if(TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
            {
                listUM.add(new MealWithExceed(um, mapDateBoolean.get(ld)));
            }
        }

        return listUM;
    }

    public static List<MealWithExceed>  getFilteredWithExceededWithStream(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().
                collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

                        return mealList.stream()
                                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime)).
                                map(x->new MealWithExceed(x,new AtomicBoolean(x.getCalories()>caloriesPerDay)))
                               .collect(Collectors.toList());
    }
}
