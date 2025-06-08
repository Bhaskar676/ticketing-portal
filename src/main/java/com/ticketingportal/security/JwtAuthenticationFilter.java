package com.ticketingportal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ticketingportal.repository.UserRepository;
import com.ticketingportal.model.User;

import java.util.Optional;
import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,UserRepository userRepository){
        this.jwtUtil=jwtUtil;
        this.userRepository=userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        if (authHeader!= null && authHeader.startsWith("Bearer ")){
            jwtToken=authHeader.substring((7));

            if (jwtUtil.validateToken(jwtToken)){
                username = jwtUtil.extractUsername(jwtToken);

                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isPresent()){
                    User user = userOptional.get();
                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
