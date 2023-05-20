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
    public void saveOrUpdate(AppUser appUser) {
        userRepository.save(appUser);
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
}
