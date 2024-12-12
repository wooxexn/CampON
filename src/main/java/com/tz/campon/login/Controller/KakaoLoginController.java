package com.tz.campon.login.Controller;

import com.tz.campon.login.DTO.KakaoUserInfoResponseDto;
import com.tz.campon.login.DTO.UserDTO;
import com.tz.campon.login.Exception.UserNotFoundException;
import com.tz.campon.login.Service.KakaoService;
import com.tz.campon.login.Service.KakaoUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final KakaoUserService kakaoUserService;

    @GetMapping("/callback")
    public RedirectView callback(@RequestParam("code") String code, HttpServletResponse response, HttpServletRequest request, RedirectAttributes attributes) throws IOException {
        try {
            // 1. 카카오에서 Access Token 받기
            String accessToken = kakaoService.getAccessTokenFromKakao(code);

            // 2. Access Token으로 사용자 정보 가져오기
            KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

            // 3. 사용자 회원가입 또는 로그인 처리
            UserDTO user = kakaoUserService.findOrRegisterUser(userInfo);

            // 4. Spring Security 인증 처리
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user.getId(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            HttpSession session = request.getSession(); // 기존 세션 가져오기 또는 새로 생성
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // 5. 응답 반환
            return new RedirectView("/");
        } catch (UserNotFoundException e) {
            // 회원가입 페이지로 리다이렉트
            attributes.addFlashAttribute("alertMessage", "카카오 회원이 존재하지 않습니다. 회원가입 후 이용해주세요.");
            return new RedirectView("/register?kakaoId=" + URLEncoder.encode(String.valueOf(e.getKakaoUserInfo().getId()), StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("카카오 로그인 실패: ", e);
            // 에러 페이지로 리다이렉트
            attributes.addFlashAttribute("errorMessage", "카카오 로그인 중 오류가 발생했습니다.");
            return new RedirectView("/error");
        }
    }

}
