package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service;

import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    AppUser findUserById(int id) throws UniversityServiceException;

    List<AppUser> findAll();

    Optional<AppUser> findUserByEmail(String email);

    List<AppUser> findAllByRole(AppUser.Role role);

    void saveOrUpdate(Integer userId, String password, String firstName, String lastName, String email, String role, String phone) throws UniversityServiceException;
}
