// 모달 열기
function openModal() {
    const modal = document.getElementById("editModal");

    // 입력 필드 초기화
    document.getElementById("password").value = ""; // 기존 비밀번호 초기화
    document.getElementById("newPassword").value = ""; // 새 비밀번호 초기화
    document.getElementById("confirmPassword").value = ""; // 비밀번호 확인 초기화

    modal.style.display = "block"; // 모달 열기
}

// 모달 닫기
const closeBtn = document.querySelector(".close");
closeBtn.onclick = () => {
    const modal = document.getElementById("editModal");

    // 입력 필드 초기화
    document.getElementById("password").value = ""; // 기존 비밀번호 초기화
    document.getElementById("newPassword").value = ""; // 새 비밀번호 초기화
    document.getElementById("confirmPassword").value = ""; // 비밀번호 확인 초기화

    modal.style.display = "none"; // 모달 닫기
};

// 배경 클릭 시 모달 닫기
window.onclick = (event) => {
    const modal = document.getElementById("editModal");
    if (event.target == modal) {
        // 입력 필드 초기화
        document.getElementById("password").value = ""; // 기존 비밀번호 초기화
        document.getElementById("newPassword").value = ""; // 새 비밀번호 초기화
        document.getElementById("confirmPassword").value = ""; // 비밀번호 확인 초기화

        modal.style.display = "none";
    }
};

// 비밀번호 검증 및 정보 수정 요청
document.getElementById('submitEditForm').addEventListener('click', function () {
    const passwordField = document.getElementById('password'); // 기존 비밀번호
    const newPasswordField = document.getElementById('newPassword'); // 새 비밀번호
    const confirmPasswordField = document.getElementById('confirmPassword'); // 새 비밀번호 확인
    const nameField = document.getElementById('username'); // 이름
    const emailField = document.getElementById('email'); // 이메일
    const phoneField = document.getElementById('phone'); // 전화번호
    const errorMessage = document.getElementById('passwordError');
    const confirmPasswordError = document.getElementById('confirmPasswordError');

    // 기존 비밀번호 검증
    if (!passwordField.value.trim()) {
        errorMessage.innerText = '기본 비밀번호를 입력해주세요.';
        errorMessage.style.display = 'block';
        passwordField.style.border = '1px solid red';
        return;
    } else {
        errorMessage.style.display = 'none';
        passwordField.style.border = '';
    }

    // 새 비밀번호와 비밀번호 확인이 일치하는지 검증
    if (newPasswordField.value && newPasswordField.value !== confirmPasswordField.value) {
        confirmPasswordError.innerText = '비밀번호가 일치하지 않습니다.';
        confirmPasswordError.style.display = 'block';
        confirmPasswordField.style.border = '1px solid red';
        return;
    } else {
        confirmPasswordError.style.display = 'none';
        confirmPasswordField.style.border = '';
    }

    // 수정할 데이터 준비
    const updateData = {
        name: nameField.value.trim(),
        email: emailField.value.trim(),
        phone: phoneField.value.trim(),
        password: passwordField.value.trim(), // 기존 비밀번호는 항상 포함
    };

    // 새 비밀번호가 입력된 경우에만 추가
    if (newPasswordField.value.trim()) {
        updateData.newPassword = newPasswordField.value.trim();
    }

    console.log("전송 데이터:", updateData);

    // 수정 요청
    fetch('/mypage/edit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updateData),
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    throw new Error(error.error || '정보 수정 중 문제가 발생했습니다.');
                });
            }
            return response.json();
        })
        .then(result => {
            alert('정보가 성공적으로 수정되었습니다.');

            // 수정된 데이터를 화면에 반영
            document.querySelector('h2').innerText = `${result.name} 님 안녕하세요.`;
            nameField.value = result.name;
            emailField.value = result.email;
            phoneField.value = result.phone;

            // 모달 닫기
            document.getElementById('editModal').style.display = 'none';
        })
        .catch(error => {
            alert(error.message);
        });
});
