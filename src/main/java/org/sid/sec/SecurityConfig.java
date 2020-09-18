package org.sid.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Cas de l authentification avec les utilisateurs en memoire
	/*	auth.inMemoryAuthentication().withUser("arsene").password("arsene").roles("ADMIN","USER");
		auth.inMemoryAuthentication().withUser("test").password("123").roles("USER");*/
		
		// authentification avec une table en base de donnees
		
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select username as principal , pass as credentials ,active from users where username=?")
		.authoritiesByUsernameQuery("select username as principal , role as role from users_roles where username=?")
		.rolePrefix("ROLE_")
		.passwordEncoder(new Md5PasswordEncoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	// usage du formulaire de base de spring
	  http.formLogin().loginPage("/login");
	// authentification pour l execution de toute requete
	 //  http.authorizeRequests().anyRequest().authenticated();
	// attribution des droits à l' admin
	 // http.authorizeRequests().antMatchers("/entreprises","/taxes","/formEntreprise","/saveEntreprise").hasRole("ADMIN");
	// attribution des droits au user
	  // http.authorizeRequests().antMatchers("/entreprises","/taxes").hasRole("USER");
	  
	  // personnalisation de la page d erreur
	  //http.exceptionHandling().accessDeniedPage("/403");
	}
}
