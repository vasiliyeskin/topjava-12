package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.JDBC, Profiles.POSTGRES_DB})
public class UserServiceJDBCTest extends UserServiceAbstrTest {
}