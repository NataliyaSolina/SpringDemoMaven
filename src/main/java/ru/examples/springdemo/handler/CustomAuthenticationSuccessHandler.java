package ru.examples.springdemo.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.examples.springdemo.model.User;
import ru.examples.springdemo.repository.UserRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Getter
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private User user;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        response.setStatus(HttpStatus.OK.value());
        response.getWriter().flush();
        response.getWriter().close();
        user = userRepository.findUserByLogin(authentication.getName()).get();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}