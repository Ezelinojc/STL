package com.example.STL;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {
	
	

	@Autowired
	private DataSource dataSource;

	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * StrictHttpFirewall firewall = new StrictHttpFirewall();
	 * firewall.setAllowUrlEncodedDoubleSlash(true); web.httpFirewall(firewall); }
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select login as username, senha as password, estado as enable from funcionario where login = ?")
				.authoritiesByUsernameQuery("select login as username, role as authority from funcionario where login = ?")
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/stl/funcionario/foto/{imagem}**", "/stl/funcionario/perfil/**").permitAll()				
				.antMatchers("/sgi/registerActividade/**").hasAuthority("ADMIN").antMatchers("/stl/**").authenticated()
				.and().formLogin().loginPage("/").loginProcessingUrl("/admin").defaultSuccessUrl("/stl")
				.usernameParameter("username").passwordParameter("password").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID").and().csrf().disable();
	}

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
