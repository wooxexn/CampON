// 로그인 창 닫기 버튼 동작: 메인 홈으로 이동
function closeLoginBox() {
    window.location.href = '/main'; // 메인 페이지로 이동
}

// 페이지 로드 후 에러 메시지 초기화 요청
document.addEventListener('DOMContentLoaded', function() {
    fetch('/clear-error-message', { method: 'POST' });
});
// 쿼리 매개변수로 전달된 메시지를 alert로 표시
window.onload = function() {
    const params = new URLSearchParams(window.location.search);
    const message = params.get("message");
    if (message === "loginRequired") {
        alert("로그인이 필요합니다. 로그인 후 다시 시도해주세요.");
    }
}
