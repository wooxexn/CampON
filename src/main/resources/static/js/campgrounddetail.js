

function initKakaoMap() {
    const mapContainer = document.getElementById('kakao-map');
    if (!mapContainer) {
        console.error("Map container not found");
        return;
    }

    // 지도 생성 옵션
    const mapOption = {
        center: new kakao.maps.LatLng(latitude, longitude), // 중심 좌표
        level: 3 // 확대 레벨
    };

    // 지도 생성
    const map = new kakao.maps.Map(mapContainer, mapOption);

    // 마커 생성
    const markerPosition = new kakao.maps.LatLng(latitude, longitude);
    const marker = new kakao.maps.Marker({
        position: markerPosition
    });
    marker.setMap(map); // 마커 지도에 추가
}

window.onload = initKakaoMap;

// 주소복사
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById("copy-address").addEventListener("click", function() {
            // 복사 기능
            var addressText = document.getElementById("camping-address").innerText.replace('주소: ', '');
            navigator.clipboard.writeText(addressText).then(function() {
                alert("주소가 복사되었습니다!");
            }).catch(function(error) {
                alert("복사 실패: " + error);
            });
        });
    });

// 헤더, 푸터
document.addEventListener("DOMContentLoaded", function() {
    const currentPath = window.location.pathname;  // 현재 페이지 URL 경로
    const menuLinks = document.querySelectorAll('.menu-link');  // 모든 메뉴 항목을 선택

    menuLinks.forEach(link => {
        const linkPath = link.getAttribute('href'); // 각 메뉴 항목의 href 속성 값

        // 현재 경로가 메뉴 항목의 href와 일치하면 'active' 클래스를 추가
        if (currentPath === linkPath || (currentPath === '/main' && linkPath === '/main')) {
            link.classList.add('active');
        }
    });
});

// 더보기 기능
// 캠핑장 소개글 더보기/간략히 보기 토글
document.addEventListener('DOMContentLoaded', function() {
    const descriptionText = document.getElementById('camping-description');
    const toggleButton = document.getElementById('toggle-description');
    const initialText = descriptionText.innerText.substring(0, 130); // 첫 100자만 보여줍니다.
    const fullText = descriptionText.innerText; // 전체 텍스트

    // 처음에는 100자까지만 보여주고, 버튼을 "더보기"로 설정
    descriptionText.innerText = initialText + '...';
    toggleButton.innerText = '더보기';

    toggleButton.addEventListener('click', function() {2
        // 더보기 버튼 클릭 시, 텍스트 변경
        if (descriptionText.innerText === initialText + '...') {
            descriptionText.innerText = fullText;
            toggleButton.innerText = '접기';
        } else {
            descriptionText.innerText = initialText + '...';
            toggleButton.innerText = '더보기';
        }
    });
});

// 리뷰글자수 제한
document.addEventListener('DOMContentLoaded', function () {
    const reviewContents = document.querySelectorAll('.review-content');
    const maxLength = 50;  // 최대 글자 수 설정

    reviewContents.forEach((content) => {
        const fullText = content.innerText;

        // 글자 수가 초과할 경우
        if (fullText.length > maxLength) {
            content.innerText = fullText.substring(0, maxLength) + '...';
        }
    });
});




