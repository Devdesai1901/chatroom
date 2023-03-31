package com.tsv.implementation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
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


@Controller
@CrossOrigin("http://localhost:3000" )
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private DefaultUserService userService;
	
	@Autowired
	UserRepository userRepo;
    
    @ModelAttribute("user")
    public UserLoginDTO userLoginDTO() {
        return new UserLoginDTO();
    }
    
	@GetMapping
	public String login() {
		return "login";
	}
	
	@PostMapping("/loginuser")
	public ResponseEntity<HttpStatus> loginUser(@RequestBody
	UserLoginDTO userLoginDTO) {
		try {
			System.out.println("UserDTO" + userLoginDTO);
			System.out.println(userLoginDTO.getEmail_id());
			System.out.println(userLoginDTO.getPassword());
			userService.loadUserByUsername(userLoginDTO.getEmail_id());
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
//	@GetMapping("/otpVerification")
//	public String otpSent(Model model,UserLoginDTO userLoginDTO) {
//		model.addAttribute("otpValue", userLoginDTO);
//		return "otpScreen";
//
//	}

	@PostMapping("/otpVerification")
	public ResponseEntity<HttpStatus> otpVerification(@RequestBody UserLoginDTO userLoginDTO, Authentication authentication) throws IOException {
		try {
			if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				User users = userRepo.findByEmail(userDetails.getUsername());

				if (users != null) {
					if (users.getOtp() == userLoginDTO.getOtp()) {
						return new ResponseEntity<>(HttpStatus.OK);
					} else {
						System.out.println("OTP verification failed: OTP entered by user does not match stored OTP");
						return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					System.out.println("User not found in database for email: " + userDetails.getUsername());
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				System.out.println("Authentication object is null or principal is not an instance of UserDetails");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught during OTP verification: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*//////////////////////////////////////////////////////////////////////////////////////////////////////*/


//			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//			for(GrantedAuthority grantedAuthority : authorities)
//			{
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
	
}
