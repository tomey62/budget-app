package pl.zukowski.jwtauth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/login"))
            filterChain.doFilter(request,response);
         else{
             String authorizationHeader = request.getHeader(AUTHORIZATION);
             if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                 try {
                     String token = authorizationHeader.substring("Bearer ".length());
                     Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                     JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                     DecodedJWT decodedJWT = jwtVerifier.verify(token);
                     String username = decodedJWT.getSubject();
                     String [] roles = decodedJWT.getClaim("roles").asArray(String.class);
                     Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                     stream(roles).forEach(role ->{
                         authorities.add(new SimpleGrantedAuthority(role));
                     });
                     UsernamePasswordAuthenticationToken authenticationToken =
                             new UsernamePasswordAuthenticationToken(username, null, authorities);
                     SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                     filterChain.doFilter(request,response);
                 } catch (Exception exception) {
                     log.error("Error logging in: {}", exception.getMessage());
                    response.sendError(CONFLICT.value());
                    response.setHeader("error", exception.getMessage());
                 }
                else {
                 filterChain.doFilter(request,response);
                }

        }
    }
}
