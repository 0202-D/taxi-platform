package ru.petrov.monitoring.web.security.config;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import ru.petrov.monitoring.web.ui.LoginView;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            ClientRegistrationRepository clientRegistrationRepository
    ) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint
                                .authorizationRequestResolver(
                                        authorizationRequestResolver(
                                                clientRegistrationRepository
                                        )
                                )
                        )
                )
                .with(VaadinSecurityConfigurer.vaadin(), configurer -> {
                    configurer.loginView(LoginView.class);
                });

        return http.build();
    }

    @Bean
    OAuth2AuthorizationRequestResolver authorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository
    ) {
        DefaultOAuth2AuthorizationRequestResolver resolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository,
                        OAuth2AuthorizationRequestRedirectFilter
                                .DEFAULT_AUTHORIZATION_REQUEST_BASE_URI
                );

        return new RegistrationAwareAuthorizationRequestResolver(resolver);
    }

    private static class RegistrationAwareAuthorizationRequestResolver
            implements OAuth2AuthorizationRequestResolver {

        private final OAuth2AuthorizationRequestResolver delegate;

        private RegistrationAwareAuthorizationRequestResolver(
                OAuth2AuthorizationRequestResolver delegate
        ) {
            this.delegate = delegate;
        }

        @Override
        public OAuth2AuthorizationRequest resolve(
                jakarta.servlet.http.HttpServletRequest request
        ) {
            OAuth2AuthorizationRequest authorizationRequest =
                    delegate.resolve(request);

            return customize(request, authorizationRequest);
        }

        @Override
        public OAuth2AuthorizationRequest resolve(
                jakarta.servlet.http.HttpServletRequest request,
                String registrationId
        ) {
            OAuth2AuthorizationRequest authorizationRequest =
                    delegate.resolve(request, registrationId);

            return customize(request, authorizationRequest);
        }

        private OAuth2AuthorizationRequest customize(
                jakarta.servlet.http.HttpServletRequest request,
                OAuth2AuthorizationRequest authorizationRequest
        ) {
            if (authorizationRequest == null) {
                return null;
            }

            String prompt = request.getParameter("prompt");

            if (!"create".equals(prompt)) {
                return authorizationRequest;
            }

            return OAuth2AuthorizationRequest
                    .from(authorizationRequest)
                    .additionalParameters(parameters ->
                            parameters.put("prompt", "create")
                    )
                    .build();
        }
    }
}