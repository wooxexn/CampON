// 로그인 창 닫기 버튼 동작: 메인 홈으로 이동
function closeLoginBox() {
    window.location.href = '/main'; // 메인 페이지로 이동
}

// 페이지 로드 후 에러 메시지 초기화 요청
document.addEventListener('DOMContentLoaded', function() {
    fetch('/clear-error-message', { method: 'POST' });
});
