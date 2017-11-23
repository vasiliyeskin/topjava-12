package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
  		  
 import java.time.LocalDateTime;
 import java.time.format.DateTimeFormatter;
 import java.util.Arrays;
 import java.util.List;
 import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
 
public class MealTestData {
     public static final int MEAL1_ID = START_SEQ + 2;
     public static final int ADMIN_MEAL_ID = START_SEQ + 8;
 
      static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     public static final Meal m1 = new Meal(MEAL1_ID, LocalDateTime.parse("2015-05-30 10:00:00", formatter), "Завтрак Леопольда", 500);
     public static final Meal m2 = new Meal(MEAL1_ID+1, LocalDateTime.parse("2015-05-30 13:00:00", formatter), "Обед Людоеда", 1000);
     public static final Meal m3 = new Meal(MEAL1_ID+2, LocalDateTime.parse("2015-05-30 20:00:00", formatter), "Ужин у Водянова", 500);
     public static final Meal m4 = new Meal(MEAL1_ID+3, LocalDateTime.parse("2015-05-31 10:00:00", formatter), "Завтрак", 500);
     public static final Meal m5 = new Meal(MEAL1_ID+4, LocalDateTime.parse("2015-05-31 13:00:00", formatter), "Обед", 1000);
     public static final Meal m6 = new Meal(MEAL1_ID+5, LocalDateTime.parse("2015-05-31 20:00:00", formatter), "Ужин", 510);
     public static final Meal ma1 = new Meal(ADMIN_MEAL_ID, LocalDateTime.parse("2015-06-01 14:00:00", formatter), "Админ ланч", 510);
     public static final Meal ma2 = new Meal(ADMIN_MEAL_ID+1, LocalDateTime.parse("2015-06-01 21:00:00", formatter), "Админ ужин", 1500);
 

    public static final List<Meal> MEALS = Arrays.asList(m1, m2, m3, m4, m5, m6);

}