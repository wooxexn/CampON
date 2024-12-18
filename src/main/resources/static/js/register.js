function checkIdDuplicate() {
    const idInput = document.getElementById("id");
    if (!idInput || !idInput.value.trim()) {
        alert("아이디를 입력하세요.");
        return;
    }

    fetch(`/check-duplicate?id=${encodeURIComponent(idInput.value)}`, { method: "GET" })
        .then(response => response.json())
        .then(data => {
            if (data.isDuplicate) {
                alert("이미 사용 중인 아이디입니다.");
            } else {
                alert("사용 가능한 아이디입니다.");
            }
        })
        .catch(error => {
            console.error("ID 중복 확인 중 오류:", error);
            alert("오류가 발생했습니다. 다시 시도해주세요.");
        });
}
