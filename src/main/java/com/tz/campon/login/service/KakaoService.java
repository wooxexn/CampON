package com.tz.campon.login.service;

import com.tz.campon.login.dto.KakaoTokenResponseDTO;
import com.tz.campon.login.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private String clientId;
    private final String KAUTH_TOKEN_URL_HOST;
    private final String KAUTH_USER_URL_HOST;
    private final RestTemplate restTemplate;

    @Autowired
    public KakaoService(@Value("${kakao.client_id}") String clientId, RestTemplate restTemplate) {
        this.clientId = clientId;
        this.restTemplate = restTemplate;
        KAUTH_TOKEN_URL_HOST ="https://kauth.kakao.com";
        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
    }

    public String getAccessTokenFromKakao(String code) {
        String url = KAUTH_TOKEN_URL_HOST + "/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&code=" + code;

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<KakaoTokenResponseDTO> response = restTemplate.postForEntity(url, request, KakaoTokenResponseDTO.class);

        KakaoTokenResponseDTO kakaoTokenResponseDto = response.getBody();
        if (kakaoTokenResponseDto != null) {
            return kakaoTokenResponseDto.getAccessToken();
        } else {
            throw new RuntimeException("Failed to retrieve access token");
        }
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
        String url = KAUTH_USER_URL_HOST + "/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfoResponseDto> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request, KakaoUserInfoResponseDto.class);

        return response.getBody();
    }

}
