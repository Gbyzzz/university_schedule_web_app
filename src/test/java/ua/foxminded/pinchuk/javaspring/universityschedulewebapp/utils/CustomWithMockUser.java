package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomWithMockUserSecurityContextFactory.class)
public @interface CustomWithMockUser {
    String username() default "user";
    String[] roles() default {};
    String firstName() default "";
    String lastName() default "";
}
