

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

        // 주소 복사
        function copyAddress() {
            const address = document.getElementById("camping-address").innerText;
            navigator.clipboard.writeText(address).then(() => {
                alert("주소가 복사되었습니다");
            }).catch(err => {
                alert("주소 복사에 실패했습니다.");
            });
        }

