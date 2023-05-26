package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.converter.TypeConverter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Table(name = "users")
@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role")
    @Type(TypeConverter.class)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Column(name = "phone")
    private String phone;

    public AppUser() {
    }

    public AppUser(Integer userId, String email, String password, String firstName,
                   String lastName, Role userRole, String phone) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return userId == appUser.userId && Objects.equals(email, appUser.email) &&
                Objects.equals(firstName, appUser.firstName) &&
                Objects.equals(lastName, appUser.lastName) &&
                userRole == appUser.userRole &&
                Objects.equals(phone, appUser.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password,
                firstName, lastName, userRole, phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", user_role=" + userRole +
                ", phone='" + phone + '\'' +
                '}';
    }

    public enum Role {
        ROLE_ADMIN, ROLE_TEACHER, ROLE_STUDENT
    }

}
