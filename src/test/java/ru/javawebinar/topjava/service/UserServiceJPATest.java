package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.JPA, Profiles.POSTGRES_DB})
public class UserServiceJPATest extends UserServiceAbstrTest {
}