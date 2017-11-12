package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getUserID() == AuthorizedUser.id()) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
            }
            repository.put(meal.getId(), meal);
            return meal;
        } else return meal;
    }


    @Override
    public void delete(int id) {
    Meal m = repository.get(id);
    if(m.getUserID() == AuthorizedUser.id())
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        Meal m = repository.get(id);
        if (m.getUserID() == AuthorizedUser.id()) return m;
        return null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream().
                filter(x -> x.getUserID() == AuthorizedUser.id()).
                sorted(((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))).
                collect(Collectors.toList());
    }
}

