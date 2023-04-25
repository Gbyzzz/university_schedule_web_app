package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
