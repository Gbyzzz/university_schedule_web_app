package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findUserByEmail(String email);
    List<AppUser> findAppUsersByUserRole(AppUser.Role role);
    List<AppUser> findAllByOrderByUserIdAsc();
}
