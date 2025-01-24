package ru.sovfor.Notes_Microservice.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.sovfor.Notes_Microservice.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringConfiguration
    {
        @Autowired
        private UserService userService;




        @Bean
        public BCryptPasswordEncoder bcryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        http.authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/main","/registration").permitAll()
//                        .requestMatchers("/private-data").hasAnyRole("USER")
                        //.requestMatchers("/public-data").hasRole("USER")
                        .anyRequest().authenticated()
                ).formLogin(login -> login
                        .defaultSuccessUrl("/main",true)
//                        .loginPage("/registration")
                        /*.permitAll()*/)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/main"));

        return http.build();
    }


//        @Autowired
//        protected AuthenticationManager authManager(HttpSecurity http) throws Exception {
//            AuthenticationManagerBuilder authenticationManagerBuilder =
//                    http.getSharedObject(AuthenticationManagerBuilder.class);
//
//            authenticationManagerBuilder.userDetailsService(userManager).passwordEncoder(new BCryptPasswordEncoder());
//            return authenticationManagerBuilder.build();
//        }

//        @Autowired
//        protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(userService).passwordEncoder(bcryptPasswordEncoder());
//        }



    }
