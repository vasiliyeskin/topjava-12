package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private List<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));

    private static String forward = "/meals.jsp";

    private static List<MealWithExceed> mealsWithExceeded;

    private Meal nullMeal = new Meal(0, null, "", 0);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        Meal editMeal = nullMeal;

        if (action != null) {
            if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                meals = MealsUtil.deleteMeal(id, meals);
            } else if (action.equalsIgnoreCase("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                for (Meal m : meals) {
                    if (m.getId() == id) {
                        editMeal = m;
                        break;
                    }
                }
            }
        }

        request.setAttribute("mealEdit", editMeal);
        mealsWithExceeded = MealsUtil.getWithExceeded(meals);
        request.setAttribute("mealsWithExceeded", mealsWithExceeded);
        getServletContext().getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String s = request.getParameter("id_edit");

        int id = 0;
        if(s != "") id = Integer.parseInt(request.getParameter("id_edit"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dlt = LocalDateTime.parse(request.getParameter("datetime"), formatter);

        if (id == 0)
            meals.add(new Meal(dlt, description, calories));
        else
            for (Meal m : meals) {
                if (m.getId() == id) {
                    meals.remove(m);
                    meals.add(new Meal(id, dlt, description, calories));
                    break;
                }
            }

        request.setAttribute("mealEdit", nullMeal);
        mealsWithExceeded = MealsUtil.getWithExceeded(meals);
        request.setAttribute("mealsWithExceeded", mealsWithExceeded);
        getServletContext().getRequestDispatcher(forward).forward(request, response);
    }
}
