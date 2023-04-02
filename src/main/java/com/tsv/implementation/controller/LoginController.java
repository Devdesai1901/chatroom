package com.tsv.implementation.controller;

import com.tsv.implementation.Security.JwtAuthResponse;
import com.tsv.implementation.Security.JwtTokenHelper;
import com.tsv.implementation.config.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tsv.implementation.dao.UserRepository;
import com.tsv.implementation.dto.UserLoginDTO;
import com.tsv.implementation.Entity.User;
import com.tsv.implementation.service.DefaultUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


@CrossOrigin("http://localhost:3000")
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private DefaultUserService userService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	UserRepository userRepo;

	@Autowired
	AuthenticationManager authenticationManager;


    
    @ModelAttribute("user")
    public UserLoginDTO userLoginDTO() {
        return new UserLoginDTO();
    }
    
	@GetMapping
	public String login() {
		return "login";
	}
	
	@PostMapping
	public void loginUser(@ModelAttribute("user") 
	UserLoginDTO userLoginDTO) {
		System.out.println("UserDTO"+userLoginDTO);
		//customSuccessHandler.getLoginUsers(userLoginDTO);
		//userService.loadUserByUsername(userLoginDTO.getUsername());
	}
//	@GetMapping("/otpVerification")
//	public String otpSent(Model model,UserLoginDTO userLoginDTO , @RequestHeader("Authorization") String authorizationHeader) {
//      //* String token = authorizationHeader.replace("Bearer " , "");
//		model.addAttribute("otpValue", userLoginDTO);
//		return "otpScreen";
//
//	}
	@PostMapping("/otpVerification")
	public ResponseEntity<HttpStatus> otpVerification(@RequestBody UserLoginDTO userLoginDTO) {
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
		System.out.println("send");
		User users = userRepo.findByEmail(userLoginDTO.getEmail_id());
		System.out.println("Receive");
//        String redirectUrl = null;
		try{
			if(users.getOtp() == userLoginDTO.getOtp()) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else{
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch (Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	//@RequestBody
	@PostMapping("/authenticate")
	public  ResponseEntity<JwtAuthResponse> createToken(@RequestBody UserLoginDTO request)
	{
		System.out.println("hello0");
//        String redirectUrl = null;
		String token = null;
		System.out.println(request.getEmail_id());
		System.out.println(request.getPassword());
		this.authenticate(request.getEmail_id() , request.getPassword());
		System.out.println("hello1");
		/*UserDetails userDetails = this.userService.loadUserByUsername(request.getUsername());
		if(userDetails != null) {
			System.out.println("hi");
			String username = userDetails.getUsername();
			User user = userRepo.findByEmail(username);
			String output = userService.generateOtp(user);
			UserLoginDTO userLoginDTO = new UserLoginDTO();
			userLoginDTO.setUsername(request.getUsername());
			userLoginDTO.setPassword(request.getPassword());*/
			token = this.jwtTokenHelper.generateToken(request);
			/*System.out.println(token);
			if(output=="success")
			{
				redirectUrl="/login/otpVerification" ;
				return redirectUrl;
			}*/

			JwtAuthResponse response = new JwtAuthResponse();
			response.setToken(token);
			return  new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	public void authenticate(String username , String password)
	{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
//		try
//		{
//			System.out.println("hello2");
			try{
				System.out.println(username);
				System.out.println(password);
				this.authenticationManager.authenticate(authenticationToken);
//				System.out.println("hello4");
				User user = userRepo.findByEmail(username);
				if(user != null)
				{
//					System.out.println("hello3");
					String output = userService.generateOtp(user);
//					System.out.println("hello5");
				}
			}catch (AuthenticationException e){
				e.printStackTrace();
			}

//		}
//		catch (DisabledException e)
//		{
//			e.printStackTrace();
//		}

	}
	
}



//	Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//			for(GrantedAuthority grantedAuthority : authorities)
//			{
//
//
//
//				if(grantedAuthority.getAuthority().equals("USER"))
//				{
//					 return "redirect:/verifyLink";//redirectUrl = "/verifyLink";
//					//break;
//				} else if (grantedAuthority.getAuthority().equals("HOST")) {
//					 return "redirect:/link";//redirectUrl= "/link";
//					//break;
//				}
//
//			}


//			if(redirectUrl == null)
//			{
//				throw  new IllegalStateException();
//			}
//			//new DefaultRedirectStrategy().sendRedirect(request , response , redirectUrl);
//			return "redirect:redirectUrl";     //redirect:/dashboard
//			/*/////////////////////////////////////////////////////////////////////////////////////////////////////*/
//		}
//		else
//			return "redirect:/login/otpVerification?error";
