package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public void saveOrUpdate(Integer userId, String password, String firstName,
                             String lastName, String email, String role,
                             String phone) throws UniversityServiceException {
        AppUser user;
        if(userId != null){
            user = findUserById(userId);
        } else {
            user = new AppUser();
            if(userRepository.findUserByEmail(email).isPresent()){
                throw new UniversityServiceException("User with such an email is already registered");
            }
            if (password.equals("")){
                throw new UniversityServiceException("Password required");
            }

            user.setPassword(passwordEncoder.encode(password));
        }
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPhone(phone);
        user.setUserRole(AppUser.Role.valueOf(role));
        userRepository.save(user);
    }
}
