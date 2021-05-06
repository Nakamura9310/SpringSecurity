package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.util.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsService userDetailsService;
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	//ignoring()により、cssとwebjarsとjsファイルについてセキュリティを適用しない
    	web.ignoring().antMatchers("/css/**", "/webjars/**", "/js/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
    		
    		.antMatchers("/login", "/register").permitAll()//login.html register.htmlはSecurityなしでOK
    		.antMatchers("/admin/**").hasRole(Role.ADMIN.name())//[/admin]はADMINユーザーだけアクセス可能にする　demo.util内に作成したRole.java
    		.anyRequest().authenticated()//他はSecurity必要
    		
    		.and().formLogin()
    			.loginPage("/login")//ログインURL指定
    			.defaultSuccessUrl("/")//認証後にリダイレクトするURL指定
    		
    		.and().logout()
    			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//ログアウト時URL指定
    		
    		//Rmember-Me ブラウザを閉じてもログイン維持
    		.and().rememberMe();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        // userDetailsServiceを使用して、DBからユーザを参照できるようにします
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    		
//    		.withUser("admin")
//    			.password(passwordEncoder().encode("pass"))
//    			.authorities("ROLE_ADMIN")
//    			.and()
//    			
//    		.withUser("user")
//    			.password(passwordEncoder().encode("pass"))
//    			.authorities("ROLE_USER");
    }
	

}
