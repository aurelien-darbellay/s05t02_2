package s05t02.interactiveCV.controller;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import s05t02.interactiveCV.dto.RegistrationRequestDto;
import s05t02.interactiveCV.globalVariables.ApiPaths;
import s05t02.interactiveCV.model.TypesConfig;
import s05t02.interactiveCV.model.User;
import s05t02.interactiveCV.model.publicViews.PublicView;
import s05t02.interactiveCV.service.entities.PublicViewService;
import s05t02.interactiveCV.service.entities.UserService;
import s05t02.interactiveCV.service.security.jwt.JwtCookieSuccessHandler;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class RootController {

    private final UserService userService;
    private final PublicViewService publicViewService;
    private final PasswordEncoder encoder;
    private final ReactiveAuthenticationManager authManager;
    private final JwtCookieSuccessHandler successHandler;
    private final TypesConfig config;
    private static final Logger log = LoggerFactory.getLogger(RootController.class);


    @GetMapping(ApiPaths.CSRF_TOKEN_PATH)
    public Mono<Map<String, String>> csrf(ServerWebExchange exchange) {
        return exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName())
                .flatMap(token -> Mono.just(Map.of("token", token.getToken())));
    }

    @PostMapping(ApiPaths.REGISTER_PATH)
    Mono<ResponseEntity<Void>> registerNewUser(ServerWebExchange exchange, @RequestBody RegistrationRequestDto request) {
        return userService.saveUser(User.builder()
                        .username(request.getUsername())
                        .hashedPassword(encoder.encode(request.getPassword()))
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .build())
                .flatMap(user -> {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
                    return authManager.authenticate(token);
                })
                .map(auth -> successHandler.createJwtCookie(exchange, auth))
                .map(webExchange -> ResponseEntity.status(HttpStatus.FOUND).header("Location", ApiPaths.USER_DASHBOARD_PATH.replace("{username}", request.getUsername())).build());
    }

    @GetMapping(ApiPaths.PUBLIC_VIEWS_PATH)
    Mono<PublicView> getPublicViewById(@RequestParam("id") String id) {
        return publicViewService.getPublicViewById(id)
                .doOnSuccess(publicView -> log.atDebug().log("Retrieved public view: {}", publicView.toString()));
    }

    @GetMapping(ApiPaths.TYPES_CONFIG_PATH)
    Mono<TypesConfig> getTypesConfig() {
        return Mono.just(config);
    }

    @GetMapping(ApiPaths.AUTHENTICATION_CHECK_PATH)
    Mono<Void> isAuthenticated() {
        return Mono.empty();
    }
}
