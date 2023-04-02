package com.tsv.implementation.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tsv.implementation.Security.JwtAuthResponse;
import com.tsv.implementation.controller.LoginController;
import com.tsv.implementation.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tsv.implementation.dao.UserRepository;
import com.tsv.implementation.Entity.User;
import com.tsv.implementation.service.DefaultUserService;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	UserRepository userRepo;

	@Autowired
	DefaultUserService userService;

	private UserLoginDTO userLogin;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {


		String redirectUrl = null;
		/*
		responsee.;*/
		/*Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for(GrantedAuthority grantedAuthority : authorities)
		{
			if(grantedAuthority.getAuthority().equals("USER"))
			{

			} else if (grantedAuthority.getAuthority().equals("HOST")) {

			}

		}*/

		 //ResponseEntity<JwtAuthResponse> tokenResponse= loginController.createToken(userLogin);

		 UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String username = userDetails.getUsername();
         User user = userRepo.findByEmail(username);
         String output = userService.generateOtp(user);
         if(output=="success")
        	 redirectUrl="/login/otpVerification" ;
         
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
		return;
	}



}
