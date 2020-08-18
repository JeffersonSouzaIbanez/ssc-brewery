package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestParameterAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestParameterAuthFilter restParamAuthFilter(AuthenticationManager authenticationManager) {
        RestParameterAuthFilter filter = new RestParameterAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                .addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(restParamAuthFilter(authenticationManager()), RequestHeaderAuthenticationFilter.class)
                .csrf().disable();

        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll() //do not user in production
                        .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll())
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();

        //h2 console config
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    //@Autowired
    //JpaUseDetailsService jpaUseDetailsService;

    // Creating in memory users (Fluent Configuration)
    //@Override
    //protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(jpaUseDetailsService).passwordEncoder(passwordEncoder());

        /*auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{bcrypt15}$2a$15$FEQfyk169efRAhDxFHTDVOLqo0xmFhnpw8uOXuPQxMUJS/Sxja9XW")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{sha256}83bf66c0555d2b203eec1131ccee1c59c3dad7937141aeedc6d111e47a7f345bd9d297dbde2f39ae")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{ldap}{SSHA}c+dh3IDaqarHmQ9hjjO5eWJji73CRQJrJCYREw==")
                .roles("CUSTOMER");*/
    //}


// Creating in memory users overriding userdetailsService
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        final UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        final UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}
