package com.fkocabay.todolist.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;  

@Configuration 
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {  
    
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
 
	@Autowired
	private DataSource dataSource;
	
	private final String USERS_QUERY = "SELECT email, password, 'true' as enabled FROM user WHERE email=?";
	private final String ROLES_QUERY = "SELECT user_name, 'USER' as roles FROM user WHERE email=?";

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 
		auth.jdbcAuthentication()
			.usersByUsernameQuery(USERS_QUERY)
			.authoritiesByUsernameQuery(ROLES_QUERY)
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
            .antMatchers("/", "/signup", "/login", "/api/**", "/*.css", "/*.jpg").permitAll()
			.antMatchers("/home/**").hasAuthority("USER").anyRequest()
			.authenticated().and().csrf().disable()
			.formLogin().loginPage("/login").failureUrl("/login?error=true")
			.defaultSuccessUrl("/home/todo-list")
			.usernameParameter("email")
			.passwordParameter("password")
			.and().rememberMe()
			.tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(60*60)
			.and()
			.logout().logoutRequestMatcher(
                    new AntPathRequestMatcher("/login?logout")
            ).logoutSuccessUrl("/login").permitAll()
			.and().exceptionHandling().accessDeniedPage("/access-denied");
		
		// added for shown h2 database
		http.headers().frameOptions().disable();
		
	}
		
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		 
		return db;
	}
}
