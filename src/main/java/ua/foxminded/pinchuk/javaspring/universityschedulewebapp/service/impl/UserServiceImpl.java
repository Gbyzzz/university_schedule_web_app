package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.impl;

import org.springframework.stereotype.Service;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.UserRepository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.UserService;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.exception.UniversityServiceException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AppUser findUserById(int id) throws UniversityServiceException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UniversityServiceException("User with id:" +
                        id + " haven't been found in the database"));
    }

    @Override
    public List<AppUser> findAll() {
        return userRepository.findAllByOrderByUserIdAsc();
    }

    @Override
    public Optional<AppUser> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<AppUser> findAllByRole(AppUser.Role role) {
        return userRepository.findAppUsersByUserRole(role);
    }

    @Override
    public void saveOrUpdate(int userId, String firstName, String lastName, String email, String role, String phone) throws UniversityServiceException {
        AppUser user = findUserById(userId);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPhone(phone);
        user.setUserRole(AppUser.Role.valueOf(role));
        userRepository.save(user);
    }
}
