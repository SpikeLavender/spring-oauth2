package com.natsumes.ame.config;

import com.natsumes.ame.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 该配置类，主要处理用户名和密码的校验等事宜
 */
@EnableWebSecurity(debug = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置拦截资源
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.requestMatchers().antMatchers("/**")
                .and().authorizeRequests()
                .antMatchers("/oauth/**").permitAll() // /oauth/**接口对外开放
                .anyRequest().authenticated()   // 在创建过滤器的基础上的一些自定义配置
                //.and().formLogin()
                .and().httpBasic(); //Basic认证
    }

    /**
     * 处理用户名和密码验证事宜
     * 1）客户端传递username和password参数到认证服务器
     * 2）一般来说，username和password会存储在数据库中的用户表中
     * 3）根据用户表中数据，验证当前传递过来的用户信息的合法性
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * 密码解析器，这里使用 BCryptPasswordEncoder 加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
