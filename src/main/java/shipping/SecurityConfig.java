package shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("shipping.*")
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/reg", "/user/add","/resources/**","/css/**","/js/**", "/img/**", "/index").permitAll()
                .antMatchers("/wagons", "/wagon/**", "/drivers", "/driver/**", "/cargoes", "/cargo/**", "/orders", "/order/**").hasAnyRole("admin")
                .antMatchers("/wagons", "/wagon/**", "/drivers", "/driver/**", "/cargoes", "/cargo/**", "/orders", "/order/**").hasAnyRole("EMPLOYEE")
                .antMatchers("/driver/updateInfo/**").hasAnyRole("DRIVER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/loginAction")
                .usernameParameter("email")
                .passwordParameter("password")
                .successForwardUrl("/")
                //.failureForwardUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .permitAll();
    }


}
