package com.tsv.implementation.Security;

import com.tsv.implementation.dto.UserLoginDTO;
import com.tsv.implementation.service.DefaultUserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private DefaultUserService userDetailsService;

    //1. getting the token from url
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestToken = request.getHeader("Authorization");
        String username = null;
        String token = null;
        System.out.println(requestToken);
        if(requestToken != null &&  requestToken.startsWith("Bearer"))
        {
            token = requestToken.substring(7);

            try
            {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("unabke to get jwt token");
            }
            catch(ExpiredJwtException e)
            {
                System.out.println("jwt token expired");
            }
            catch (MalformedJwtException e)
            {
                System.out.println("invalid jwt");
            }

        }
        else
        {
            System.out.println("Jwt  token does not start with Bearer");
        }

        //2. once the token is get , now we are walidating

        if(username != null  && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setEmail_id(userDetails.getUsername());
            userLoginDTO.setPassword(userDetails.getPassword());
            if(this.jwtTokenHelper.validateToken(token, userLoginDTO))
            {
                //sahi chal raha he
                //authincate karna he
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(token,null ,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource() );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else
            {
                System.out.println("invalid jwt token");
            }
        }
        else
        {
            System.out.println("username is null or context is not null");
        }
        filterChain.doFilter(request,response);
    }
}
