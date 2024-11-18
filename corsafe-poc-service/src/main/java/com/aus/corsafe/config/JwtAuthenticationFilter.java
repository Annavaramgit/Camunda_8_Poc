package com.aus.corsafe.config;

import com.aus.corsafe.exceptions.BadCrediantialsCls;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Configuration
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailasService myUserDetailasService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("jwtauthentication filter entered");

        try {
            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {

                log.info("doFilter method if block entered...");
                filterChain.doFilter(request, response);

                return;

            }

            String token = header.substring(7);
            String userName = jwtService.getSubjectFromToken(token);

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userInfo = myUserDetailasService.loadUserByUsername(userName);

                if (userInfo != null && jwtService.isTokenValid(token)) {

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userInfo,
                                    null,
                                    userInfo.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                }


            }
            filterChain.doFilter(request, response);
        }
       catch (JwtException e) {
            log.error("JWT token is invalid: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
        } catch (Exception e) {
            log.info("Error in JwtAuthenticationFilter: " + e.toString());

            throw new BadCrediantialsCls("BadRequest !!");
        }

    }
}
