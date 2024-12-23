

function initKakaoMap() {
            const mapContainer = document.getElementById('kakao-map');
            const mapOption = {
                center: new kakao.maps.LatLng(37.727872, 127.511138),
                level: 3 // 확대 레벨
            };
            const map = new kakao.maps.Map(mapContainer, mapOption);

            // 마커생성
            const marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(37.727872, 127.511138)
            });
            marker.setMap(map);
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

    toggleButton.addEventListener('click', function() {
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






