package feddit.security;

//import feddit.services.AdminAuthService;
import feddit.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

        public AdminSecurityConfig() {
            super();
        }

        @Bean
        public UserDetailsService adminDetailsService() {
            return new AdminAuthService();
        }

        @Bean
        public DaoAuthenticationProvider adminAuthenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(adminDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());

            return authProvider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(adminAuthenticationProvider());
        }

        @Override
        //https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/myaccount").authenticated() //<- list of pages that need to be authorized (my account? change password?)
                    .antMatchers("/changepassword").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .formLogin().loginPage("/admin")
                    .usernameParameter("username")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login_admin_error")
                    .permitAll()
                    .and()
                    .logout().logoutSuccessUrl("/").permitAll();
        }
    }
    */

    @Configuration
    @Order(2)
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

        public UserSecurityConfig() {
            super();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new AuthService();
        }

        @Bean
        public DaoAuthenticationProvider userAuthenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());

            return authProvider;
        }

        @Bean
        public AuthenticationSuccessHandler successHandler() {
            SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
            handler.setUseReferer(true);
            return handler;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(userAuthenticationProvider());
        }

        @Override
        //https://www.codejava.net/frameworks/spring-boot/user-registration-and-login-tutorial
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/myaccount").authenticated() //<- list of pages that need to be authorized (my account? change password?)
                    .antMatchers("/changepassword").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .formLogin().loginPage("/login").successHandler(successHandler())
                    .usernameParameter("username")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login_error")
                    .permitAll()
                    .and()
                    .logout().logoutSuccessUrl("/").permitAll();
        }
    }
}
