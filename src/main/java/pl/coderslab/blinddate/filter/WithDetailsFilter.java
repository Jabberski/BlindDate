package pl.coderslab.blinddate.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.repository.UserRepository;
import pl.coderslab.blinddate.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class WithDetailsFilter implements Filter {
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User logged = userRepository.getByEmail(authentication.getName());
        if(logged.isWithDetails()){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect("/welcome");
        }


    }
}
