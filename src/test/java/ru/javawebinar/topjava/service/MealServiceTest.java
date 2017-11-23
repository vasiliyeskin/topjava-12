package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by Vaisiliy Es'kin on 08/24/17.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertThat(meal).isEqualTo(ma1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        Meal meal = service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertThat(service.getAll(USER_ID)).isEqualTo(Arrays.asList(m6, m5, m4, m3, m2));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(m2, ADMIN_ID);
    }
}