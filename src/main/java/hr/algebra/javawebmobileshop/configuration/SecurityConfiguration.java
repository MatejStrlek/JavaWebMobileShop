package hr.algebra.javawebmobileshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login").anonymous()
                        .requestMatchers(
                                "/h2-console/**",
                                "/rest/mobilewebshop/**"
                        ).permitAll()/*.hasRole("ADMIN")*/
                        .requestMatchers(
                                "/rest/mobilewebshop/purchase",
                                "/mvc/mobilewebshop/getPurchase.html",
                                "/mvc/mobilewebshop/getHistory.html"
                        )
                        .hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
