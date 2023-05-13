package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service;

import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    AppUser findUserById(int id) throws UniversityServiceException;

    void saveOrUpdate(AppUser appUser);

    List<AppUser> findAll();

    Optional<AppUser> findUserByEmail(String email);

    List<AppUser> findAllByRole(AppUser.Role role);
}
