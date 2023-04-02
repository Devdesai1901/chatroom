package com.tsv.implementation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.tsv.implementation.dto.UserRegisteredDTO;
import com.tsv.implementation.service.DefaultUserService;

@Controller
@CrossOrigin("http://localhost:3000")
@RequestMapping("/registration")
public class RegistrationController {

	 private DefaultUserService userService;

	    public RegistrationController(DefaultUserService userService) {
	        super();
	        this.userService = userService;
	    }

	    @ModelAttribute("user")
	    public UserRegisteredDTO userRegistrationDto() {
	        return new UserRegisteredDTO();
	    }

//	    @GetMapping
//	    public String showRegistrationForm() {
//	        return "register";
//	    }

	    @PostMapping("/registeruser")
	    public ResponseEntity<HttpStatus> registerUserAccount(@RequestBody
	              UserRegisteredDTO registrationDto) {
			try{
				userService.save(registrationDto);
//	        	return "redirect:/login";
				return new ResponseEntity<>(HttpStatus.OK);
			}catch (Exception e){
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

	    }
}
