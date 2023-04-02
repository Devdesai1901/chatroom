package com.tsv.implementation.config;

import com.tsv.implementation.Security.JwtAuthenticationEntryPoint;
import com.tsv.implementation.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tsv.implementation.service.DefaultUserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private  DefaultUserService userDetailsService;
	
	@Autowired
	AuthenticationSuccessHandler successHandler;


	

	

	
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {    
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login/authenticate","/registration/registeruser" ) //"/login/authenticate", "/registration" , "/login"
//                .antMatchers("/login/*") //"/login/authenticate", "/registration" , "/login"
                .permitAll()
                .anyRequest()
                .authenticated()
               // .antMatchers("/registration/**").permitAll()
                //.antMatchers("/link").hasAnyRole("HOST")
        //.antMatchers("/topic").hasAnyRole("HOST")
                //.antMatchers("/verifyLink").hasAnyRole("USER")
                .and()
                .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
        .formLogin(
                form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .permitAll()  //.successHandler(successHandler)
        ).logout(
                logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()

        );

        http.addFilterBefore(this.jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);
return http.build();

    }

    /*@Bean
    protected  void  configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }*/





    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

   /* @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config ) throws Exception {
        return config.getAuthenticationManager();
    }*/

    /*@Bean
    @Override
    public  AuthenticationManager authenticationManagerBean() throws  Exception
    {
        return  super.authica
    }*/
}
