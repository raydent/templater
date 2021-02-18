package com.example.templater.security;

import com.example.templater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final String LOCALHOST = "127.0.0.1";

    /*@Autowired
    UserService userService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
*/

    //roles admin allow to access/admin/** **
    //roles user allow to access/user/** **
    //custom 403 access denied handler

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/about", "/registration**", "/test", "/test_angular").permitAll()
                .antMatchers("/home", "/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/home", "/user/**", "/template").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user")
                .permitAll()
                .failureUrl("/login?error=true")//
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);*/
        /*http.authorizeRequests()
                .antMatchers("/about", "/registration**", "/test", "/test_angular").hasIpAddress(LOCALHOST)
                .antMatchers("/home", "/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/home", "/user/**", "/template").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);
        http.addFilterAfter(new CustomFilter(),
                BasicAuthenticationFilter.class);*/
        //http.authorizeRequests().antMatchers("/**").permitAll();
       http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
            .and()
                .logout().permitAll()
            .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/registration_angular");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*");
        registry.addMapping("/logout")
                .allowedOrigins()
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
