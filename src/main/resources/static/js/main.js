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
