package com.example.smartparkingsystemapi.JWTSecurity;

import com.example.smartparkingsystemapi.entity.User;
import com.example.smartparkingsystemapi.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
//@Componenet -> spring will detect it automatically and creates its object and pass it to whole project
public class JwtFilter extends OncePerRequestFilter {
    //OncePerRequestFilter runs once for each HTTP Request
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtFilter(JwtUtil jwtUtil,UserRepository userRepository){
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JWT FILTER RUNNING");
        //extracting header from request e.g (Authorization : Bearer (token))
        String authheader = request.getHeader("Authorization");

        //extracting token from authheader e.g(Bearer (token))
        String token = null;
        String email = null;
        if(authheader != null && authheader.startsWith("Bearer ")){
            token = authheader.substring(7);//this method removes "Bearer " from authheader to get token
            email = jwtUtil.extractEmail(token);
        }

        System.out.println("Auth Header: " + authheader);
        System.out.println("Token: " + token);
        System.out.println("Email: " + email);

        //Validate only if already not authenticated
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){//this avoids reauthentication if already authenticated i.e. logged in
            //load user
            User user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new RuntimeException("User not found"));
            //validate user
            System.out.println("VALID TOKEN? " + jwtUtil.validateToken(token, user.getEmail()));
            if(jwtUtil.validateToken(token,user.getEmail())){
                //create Authenticatio object
                UsernamePasswordAuthenticationToken authTToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
                System.out.println("After setting auth: " + SecurityContextHolder.getContext().getAuthentication());
                authTToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                System.out.println("Token valid: " + jwtUtil.validateToken(token, user.getEmail()));
                //set authentication
                SecurityContextHolder.getContext().setAuthentication(authTToken);
                System.out.println("AUTH SET: " + SecurityContextHolder.getContext().getAuthentication());
            }
        }
        //continue filter chain
        filterChain.doFilter(request,response);
    }
}
