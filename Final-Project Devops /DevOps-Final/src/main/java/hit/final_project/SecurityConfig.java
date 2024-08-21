package hit.final_project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity


/*
  Added value
 // Note: This code was created with the help of AI assistance to ensure best practices and thorough testing.
מחלקת SecurityConfig מגדירה את האבטחה של היישום באמצעות Spring Security.
 היא מגדירה שרשרת פילטרים פשוטה שמאפשרת גישה לכל הבקשות ללא צורך באימות,
  וכן משתמש אחד שמאוחסן בזיכרון עם שם משתמש וסיסמה קבועים מראש.
   מחלקה זו מספקת דרך בסיסית מאוד להוסיף אבטחה ליישום,
    ומשתמשת ביכולות של Spring Security לניהול משתמשים והגדרת הרשאות.
 */
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Updated approach to disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("myuser")
                .password("mypassword")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
