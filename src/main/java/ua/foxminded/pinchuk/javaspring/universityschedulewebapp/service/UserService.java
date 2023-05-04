package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service;

import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.User;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserById(int id) throws UniversityServiceException;

    void saveOrUpdate(User user);

    List<User> findAll();
}
