function initKakaoMap() {
            const mapContainer = document.getElementById('kakao-map');
            const mapOption = {
                center: new kakao.maps.LatLng(35.674840, 127.773876), // 중심 좌표
                level: 3 // 확대 레벨
            };
            const map = new kakao.maps.Map(mapContainer, mapOption);

            // 마커 생성
            const marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(35.674840, 127.773876) // 마커 좌표
            });
            marker.setMap(map);
        }
        // 주소 복사
        function copyAddress() {
            const address = document.getElementById("camping-address").innerText;
            navigator.clipboard.writeText(address).then(() => {
                alert("주소가 복사되었습니다");
            }).catch(err => {
                alert("주소 복사에 실패했습니다.");
            });
        }
        // 초기화 호출
        window.onload = initKakaoMap;



        // 더보기버튼 기능
        function toggleDescription() {
            const description = document.querySelector('.description');
            const button = document.querySelector('.toggle-description');

            description.classList.toggle('open');  // "open" 클래스 토글
            if (description.classList.contains('open')) {
                button.textContent = '접기';  // 접기 버튼으로 변경
            } else {
                button.textContent = '더보기';  // 더보기 버튼으로 변경
            }
        }

