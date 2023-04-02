package com.tsv.implementation.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tsv.implementation.dao.RoleRepository;
import com.tsv.implementation.dao.UserRepository;
import com.tsv.implementation.dto.UserRegisteredDTO;
import com.tsv.implementation.Entity.Role;
import com.tsv.implementation.Entity.User;


@Service
public class DefaultUserServiceImpl implements DefaultUserService{
   @Autowired
	private UserRepository userRepo;
	
   @Autowired
  	private RoleRepository roleRepo;

	@Autowired
	JavaMailSender javaMailSender;
   
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}


		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),mapRolesToAuthorities(user.getRoles()));
//		return user;
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> role){
		return role.stream().map(roles -> new SimpleGrantedAuthority(roles.getRole())).collect(Collectors.toList());
	}

//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		User user = userRepo.findByEmail(email);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found with email: " + email);
//		}
//
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//				getAuthorities(user));
//	}
//
//	private static final Map<String, List<String>> ROLE_AUTHORITY_MAP = new HashMap<>();
//
//	static {
//		ROLE_AUTHORITY_MAP.put("USER", Arrays.asList("USER"));
//		ROLE_AUTHORITY_MAP.put("HOST", Arrays.asList("USER", "HOST"));
//	}
//
//	private static List<GrantedAuthority> getAuthorities(User user) {
//		List<String> roleAuthorities = ROLE_AUTHORITY_MAP.get(user.getRoles());
//		if (roleAuthorities == null) {
//			throw new IllegalArgumentException("Invalid user role: " + user.getRoles());
//		}
//
//		return roleAuthorities.stream()
//				.map(SimpleGrantedAuthority::new)
//				.collect(Collectors.toList());
//	}
	@Override
	public User save(UserRegisteredDTO userRegisteredDTO) {
		Role role = new Role();
		if(userRegisteredDTO.getRole().equals("USER"))
		{
			role = roleRepo.findByRole("USER");
		} else if (userRegisteredDTO.getRole().equals("HOST"))
		{
			role = roleRepo.findByRole("HOST");
		}


		User user = new User();
		user.setEmail(userRegisteredDTO.getEmail_id());
		user.setName(userRegisteredDTO.getName());
		user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
		user.setRoles(role);
		user.setBranch(userRegisteredDTO.getBranch());
		user.setSem(userRegisteredDTO.getSem());
		user.setCollage_id(userRegisteredDTO.getCollage_id());
		return userRepo.save(user);
	}

	@Override
	public String generateOtp(User user) {
		try {
			//extra properstis added
			Properties props = new Properties();

			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.ssl.protocols", "TLSv1.2");
			props.put("mail.smtp.ssl.trust" , "*");
			/*props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.debug", "true");
			props.put("mail.smtp.socketFactory.port", "587");
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");*/

			//session.getProperties().put("mail.smtp.starttls.enable", "true");

			int randomPIN = (int) (Math.random() * 9000) + 1000;
			user.setOtp(randomPIN);
			userRepo.save(user);
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("devdesai702@gmail.com");
			msg.setTo(user.getEmail());

			msg.setSubject("Welcome To BRAINSTROME");
			msg.setText("Hello \n\n" +"Your Login OTP :" + randomPIN + ".Please Verify. \n\n"+"Regards \n"+"BRAINSTROME INC");



			javaMailSender.send(msg);
			
			return "success";
			}catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
	}

}
