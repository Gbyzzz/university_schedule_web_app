package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.repository.UserRepository;

import java.util.Collection;
import java.util.Map;

@Service
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder,
                               AuthenticationManager authenticationManager) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

//  public AuthenticationResponse register(RegisterRequest request) {
//    User user = User.builder()
//        .firstname(request.getFirstname())
//        .lastname(request.getLastname())
//        .email(request.getEmail())
//        .password(passwordEncoder.encode(request.getPassword()))
//        .role(request.getRole())
//        .build();
//    var savedUser = repository.save(user);
//    var jwtToken = jwtService.generateToken(user);
//    var refreshToken = jwtService.generateRefreshToken(user);
//    saveUserToken(savedUser, jwtToken);
//    return AuthenticationResponse.builder()
//        .accessToken(jwtToken)
//            .refreshToken(refreshToken)
//        .build();
//  }

  public void sign_in(Model model) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            model.getAttribute("email"),
            model.getAttribute("password")
        )
    );
    AppUser appUser = repository.findUserByEmail((String) model.getAttribute("email"))
        .orElseThrow();
    model.addAttribute("user", appUser);
  }

  public void sign_out(Model model) {
    model = new Model() {
      @Override
      public Model addAttribute(String attributeName, Object attributeValue) {
        return null;
      }

      @Override
      public Model addAttribute(Object attributeValue) {
        return null;
      }

      @Override
      public Model addAllAttributes(Collection<?> attributeValues) {
        return null;
      }

      @Override
      public Model addAllAttributes(Map<String, ?> attributes) {
        return null;
      }

      @Override
      public Model mergeAttributes(Map<String, ?> attributes) {
        return null;
      }

      @Override
      public boolean containsAttribute(String attributeName) {
        return false;
      }

      @Override
      public Object getAttribute(String attributeName) {
        return null;
      }

      @Override
      public Map<String, Object> asMap() {
        return null;
      }
    };
  }
}
