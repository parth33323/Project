package com.userFront.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.userFront.service.UserServiceImpl.UserSecurityService;

@Configuration
@EnableWebSecurity

//018 need configs on webSecurity so extend the associated class
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	//018 custom service
	private UserSecurityService userSecurityService;
	
	//018 for encrypting pass to be stored in DB; display encrypted pass in db using 'salt' seed str
	private static final String SALT = "salt";
	
	
	@Bean
	//018 ?? and mtd registered as bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	//018 list of specific paths to be xs'ed publicly w/o spring sec protection such as css js and don't require spr sec verification; ** means path and it's subDirs
	private static final String[] PUBLIC_MATCHERS = {
			"/webjars/**",
			"/css/**",
			"/js/**",
			"/images/**",
			"/",
			"/about/**",
			"/contact/**",
			"/error/**/*",
			"/console/**",
			"/signup"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//018 grant request permission for any string in public_matchers arr; otherwise require auth 
		http
			.authorizeRequests().
//          antMatchers("/**").
			antMatchers(PUBLIC_MATCHERS).
			permitAll().anyRequest().authenticated();
		http
		//018 disable protection of csrf cors region attack so that we don't face devpmnt issues related to cors region
			.csrf().disable().cors().disable()
			//log in url  will be post; on failure, path will be err; login success redir to userFront; and login pg is /index
			.formLogin().failureUrl("/index?error").defaultSuccessUrl("/userFront").loginPage("/index").permitAll()
			.and()
			//when trying to access /logout url; if logout successful then redir to /index?logout param; del cookies to disable the user memory func  
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
			.and()
			//add rem me func autobox provided by spr sec
			.rememberMe();
	}
	
	@Autowired
	
	//018 in house auth userDetailsService func; passEncod. will let spr sec use encoded instead of raw passowrd; 019 this service only accepts user details not user obj
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("user").password("password").roles("USER"); //This is in-memory authentication
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());

	}
	

}
