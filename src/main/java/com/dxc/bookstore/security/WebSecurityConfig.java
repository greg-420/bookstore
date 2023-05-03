package com.dxc.bookstore.security;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Configuration
@Slf4j
public class WebSecurityConfig {

  public String generatePassword() {
    PasswordGenerator gen = new PasswordGenerator();
    CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
    lowerCaseRule.setNumberOfCharacters(2);

    CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
    upperCaseRule.setNumberOfCharacters(2);

    CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
    digitRule.setNumberOfCharacters(2);

    return gen.generatePassword(10, lowerCaseRule, upperCaseRule, digitRule);
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    String userPass = generatePassword();
    String adminPass = generatePassword();
    log.info("user password: " + userPass);
    log.info("admin password: " + adminPass);
    auth.inMemoryAuthentication().withUser("user").password("{noop}" + userPass)
        .authorities("ROLE_USER");
    auth.inMemoryAuthentication().withUser("admin").password("{noop}" + adminPass)
        .authorities("ROLE_ADMIN", "ROLE_USER");
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    BasicAuthenticationEntryPoint entry = new BasicAuthenticationEntryPoint();
    entry.setRealmName("bookstore");
    http.httpBasic().authenticationEntryPoint(entry).and().csrf().disable().authorizeHttpRequests()
        .requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/**").hasRole("USER");
    return http.build();
  }

}
