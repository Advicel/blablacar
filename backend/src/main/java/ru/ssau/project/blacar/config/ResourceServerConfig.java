package ru.ssau.project.blacar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import ru.ssau.project.blacar.data.Role;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/trip/find").permitAll()
                .antMatchers("/trip/delete").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/trip/my-trip").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/trip/my-plans").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/trip/add").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/user/create/").anonymous()
                .antMatchers("/user/logout").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/user/add-car").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/user/current").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/appointment/join").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/appointment/back-join").hasAuthority(Role.USER.getAuthority())
                .antMatchers("/appointment/back-confirm").hasAuthority(Role.USER.getAuthority())
                .and()
                .csrf().disable();
    }
}

