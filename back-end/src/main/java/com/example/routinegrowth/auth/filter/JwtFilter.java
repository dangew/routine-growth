package com.example.routinegrowth.auth.filter;

import com.example.routinegrowth.auth.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain) throws ServletException, IOException {

    String token = tokenFromCookie(request);

    if (token != null && jwtUtil.validate(token)) {
     UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
       jwtUtil.getUserEmail(token), null, null
     );
     auth.setDetails(new WebAuthenticationDetails(request));
     SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

  public String tokenFromCookie(HttpServletRequest request) {
    String token = null;
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals("token")) {
          token = cookie.getValue();
          break;
        }
      }
    }
    return token;
  }
}
