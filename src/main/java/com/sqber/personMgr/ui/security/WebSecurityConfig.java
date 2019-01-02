package com.sqber.personMgr.ui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Bean
    UserDetailsService customUserService() { // 注册UserDetailsService 的bean
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()); // user Details Service验证

    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    // @Bean
    // public static BCryptPasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests()
        // // .antMatchers("/css/**", "/index").permitAll()
        // .antMatchers("/").hasRole("USER").and().formLogin().loginPage("/login").failureUrl("/login-error");
        // http.authorizeRequests().anyRequest().authenticated() // 任何请求,登录后可以访问
        // .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
        // // 登录页面用户任意访问
        // .and().logout().permitAll(); // 注销行为任意访问

        // http.authorizeRequests().antMatchers("/barsearch").permitAll().and().headers().frameOptions().disable();
        // //允许iframe 加载
        // http.formLogin().loginPage("/login").failureUrl("/login?error=true");

        http.authorizeRequests().antMatchers("/barsearch").permitAll().and().headers().frameOptions().disable(); // 允许iframe
        http.formLogin().loginPage("/login").failureUrl("/login?error=true");

        // http.csrf().disable();//关闭csrf
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        //http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
