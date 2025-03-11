package ru.examples.springdemo.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.examples.springdemo.handlers.CustomAuthenticationFailureHandler;
import ru.examples.springdemo.handlers.CustomAuthenticationSuccessHandler;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users**", "/users/**", "/tasks**", "/tasks/**").authenticated()
                        .requestMatchers("/ui/users**", "/ui/users/**", "/ui/tasks**", "/ui/tasks/**").authenticated()
                        .requestMatchers("/admin/**").authenticated()
                        .requestMatchers("/ui/admin/**").authenticated()
                        .requestMatchers("/login*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll() // Регистрация
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/login*", "/main", "/").permitAll()
//                        .anyRequest().authenticated())
//                .cors(AbstractHttpConfigurer::disable)
//                .formLogin(form -> form
//                        .loginProcessingUrl("/login")
//                        .usernameParameter("login")
//                        .passwordParameter("password")
//                        .successHandler(customAuthenticationSuccessHandler)
//                        .failureHandler(customAuthenticationFailureHandler)
//                        .permitAll()
//                        .failureUrl("/login?error=true"))
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login"))
//                .httpBasic(Customizer.withDefaults())
//                       ;
//
//        return http.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(
//                "/jakarta.faces.resource/**",
//                "/",
//                "/index.html",
//                "/login",
//                "/main",
//                "/main.xhtml",
//                "/img/**",
//                "/error/**",
//                "/RES_NOT_FOUND");
//    }
}