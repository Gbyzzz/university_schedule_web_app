package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import ua.foxminded.pinchuk.javaspring.universityschedulewebapp.bean.AppUser;

public class CustomWithMockUserSecurityContextFactory implements WithSecurityContextFactory<CustomWithMockUser> {

    @Override
    public SecurityContext createSecurityContext(CustomWithMockUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AppUser userDetails = new AppUser(1, customUser.username(), null, customUser.firstName(),
                customUser.lastName(), AppUser.Role.valueOf(customUser.roles()[0]), null);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}