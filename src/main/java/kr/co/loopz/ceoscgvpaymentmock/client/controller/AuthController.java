package kr.co.loopz.ceoscgvpaymentmock.client.controller;

import kr.co.loopz.ceoscgvpaymentmock.client.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/{nickname}")
    public ResponseEntity<String> getAuthToken(
            @PathVariable String nickname
    ) {
        String token = authService.getAuthToken(nickname);
        return ResponseEntity.ok(token);
    }


}
