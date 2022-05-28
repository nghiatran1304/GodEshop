package com.godEShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.godEShop.Service.AccountService;
import com.godEShop.Service.Impl.UserDetailImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Autowired
    AccountService accountService;

    @Autowired
    UserDetailImpl userDetail;

    // cung cấp nguồn dữ liệu đăng nhập
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	// TODO Auto-generated method stub
	auth.userDetailsService(userDetail);
    }

    // phân quyền
    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable().cors().disable();

	http.authorizeHttpRequests()
		// những địa chỉ bắt đầu bằng order bắt buộc phải đăng nhập
		.antMatchers("/information").authenticated().antMatchers("/order/**").authenticated()
		.antMatchers("/admin/**").hasAnyRole("Admin")
		.anyRequest().permitAll();

	http.formLogin().loginPage("/account/login/form").loginProcessingUrl("/account/login")
		.defaultSuccessUrl("/account/login/success", false).failureUrl("/account/login/error");

	http.rememberMe().tokenValiditySeconds(86400);

	http.exceptionHandling().accessDeniedPage("/ErrorPage");

	http.logout().logoutUrl("/account/logoff").logoutSuccessUrl("/account/logoff/success");

	// OAUTH2 ĐĂNG NHẬP MẠNG XÃ HỘI
	http.oauth2Login()
		// địa chỉ đăng nhập -> form đăng nhập
		.loginPage("/account/login/form")
		// đăng nhập thành công
		.defaultSuccessUrl("/oauth2/login/success", true)
		// đăng nhập thất bại
		.failureUrl("/account/login/error")
		// khai vào link href trong form đăng nhập
		.authorizationEndpoint().baseUri("/oauth2/authorization");
    }

    // cho phép truy xuất rest api từ bên ngoài (domain khác)
    @Override
    public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
