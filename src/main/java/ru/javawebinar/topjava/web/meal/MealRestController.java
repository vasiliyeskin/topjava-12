package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";


     @Override
     @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public Meal get(@PathVariable("id") int id) {
                return super.get(id);
            }

     @Override
     @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
     public List<MealWithExceed> getAll() {
                return super.getAll();
            }

     @Override
     @DeleteMapping(value = "/{id}")
     public void delete(@PathVariable("id") int id) {
                super.delete(id);
            }

         /*    @PostMapping(value = "/create/{datetime}/{description}/{calories}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
       public Meal create(@PathVariable("datetime") LocalDateTime dateTime, @PathVariable("description") String description, @PathVariable("calories") Integer calories) {
         Meal meal = new Meal(dateTime, description, calories);
         return super.create(meal);
     }*/

     @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<Meal> createH(@RequestBody Meal meal) {
                Meal created = super.create(meal);

                        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path(REST_URL + "/{id}")
                                .buildAndExpand(created.getId()).toUri();

                        return ResponseEntity.created(uriOfNewResource).body(created);
            }

     @Override
     @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
     public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
                super.update(meal, id);
            }

     @PostMapping(value = "/update/{id}/{datetime}/{description}/{calories}", produces = MediaType.APPLICATION_JSON_VALUE)
     public void update(@PathVariable("id") int id, @PathVariable("datetime") LocalDateTime dateTime, @PathVariable("description") String description, @PathVariable("calories") Integer calories) {
                Meal meal = new Meal(dateTime, description, calories);
                super.update(meal, id);
            }


         /*    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
         int userId = AuthorizedUser.id();
         log.info("getBetween dates({} - {}) time({} - {}) for User {}", startDate, endDate, startTime, endTime, userId);

         return MealsUtil.getFilteredWithExceeded(
                 service.getBetweenDates(
                         startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                         endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                 startTime != null ? startTime : LocalTime.MIN,
                 endTime != null ? endTime : LocalTime.MAX,
                 AuthorizedUser.getCaloriesPerDay()
         );
     }*/
}