package com.tz.campon.login.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clear-error-message")
public class ErrorMessageController {

    @PostMapping
    public ResponseEntity<Void> clearErrorMessage(HttpServletRequest request,@RequestParam(value = "continue", required = false) String continueUrl) {
        HttpSession session = request.getSession();
        session.removeAttribute("errorMessage");
        String redirectUrl = (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : "/";
        return ResponseEntity.status(302)
                .header("Location", redirectUrl)
                .build();
    }

}
