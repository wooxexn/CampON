// Summernote 초기화
    $(document).ready(function () {
    $('#content').summernote({
        placeholder: '내용을 입력하세요.',
        tabsize: 2,
        height: 300,
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'italic', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['insert', ['link', 'picture', 'video']],
            ['view', ['fullscreen', 'codeview', 'help']]
        ],
        callbacks: {
            onImageUpload: function (files) {
                uploadImage(files[0]);
            },
            onChange: function() {
                toggleSubmitButton(); // Summernote 내용이 바뀌면 활성화 조건 확인
            }
        }
    });

    // 제목과 내용 입력 상태 확인
    const captionInput = document.getElementById('caption');
    const submitButton = document.querySelector('.submit-button');
    captionInput.addEventListener('input', toggleSubmitButton);

    function toggleSubmitButton() {
    const contentText = $('#content').summernote('isEmpty') ? '' : $('#content').summernote('code').trim();
    if (captionInput.value.trim() && contentText) {
    submitButton.disabled = false; // 활성화
} else {
    submitButton.disabled = true; // 비활성화
}
}
});

    // 이미지 업로드 처리
    function uploadImage(file) {
    const data = new FormData();
    data.append("file", file);

    $.ajax({
    url: '/upload-image',
    method: 'POST',
    data: data,
    contentType: false,
    processData: false,
    success: function (url) {
    $('#content').summernote('insertImage', url);
},
    error: function () {
    alert('이미지 업로드에 실패했습니다.');
}
});
}

// 이미지 업로드 처리
function uploadImage(file) {
    const data = new FormData();
    data.append("file", file);

    $.ajax({
        url: '/upload-image',
        method: 'POST',
        data: data,
        contentType: false,
        processData: false,
        success: function (url) {
            $('#content').summernote('insertImage', url);
        },
        error: function () {
            alert('이미지 업로드에 실패했습니다.');
        }
    });
}

