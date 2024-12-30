document.addEventListener('DOMContentLoaded', () => {
    // 초기 좋아요 상태 확인
    const likeButton = document.querySelector('.like-button');
    const isLiked = likeButton.classList.contains('liked');

    // 좋아요 버튼 초기화
    if (isLiked) {
        likeButton.innerText = '💔 좋아요 취소';
    } else {
        likeButton.innerText = '❤️ 좋아요';
    }
});

function toggleLike(boardId) {
    const likeButton = document.querySelector('.like-button');
    const isLoggedIn = likeButton.getAttribute('data-is-logged-in') === 'true';

    if (!isLoggedIn) {
        alert('좋아요를 누르려면 로그인해야 합니다.');
        return;
    }

    const likeCountSpan = document.querySelector('.post-actions span');

    fetch(`/board/like/${boardId}`, { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            // 서버에서 반환된 좋아요 수 업데이트
            likeCountSpan.innerText = `좋아요: ${data}`;

            // 좋아요 버튼 상태 토글
            if (likeButton.classList.contains('liked')) {
                likeButton.classList.remove('liked');
                likeButton.innerText = '❤️ 좋아요';
            } else {
                likeButton.classList.add('liked');
                likeButton.innerText = '💔 좋아요 취소';
            }
        })
        .catch(error => console.error('Error:', error));
}

// 댓글 작성 후 새로 고침 없이 댓글을 추가하는 기능
document.getElementById("commentForm").addEventListener("submit", function(event) {
    event.preventDefault(); // 폼 제출의 기본 동작을 막음

    if (!isLoggedIn) {
        alert('댓글을 작성하려면 로그인해야 합니다.');
        return;
    }

    let content = document.querySelector("textarea[name='content']").value;
    let boardId = document.querySelector("input[name='boardId']").value;

    // 댓글을 서버로 보내기 위해 Ajax 요청을 보냄
    fetch('/board/comment/add', {
        method: 'POST',
        body: new URLSearchParams({
            'content': content,
            'boardId': boardId
        }),
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
        .then(response => response.json())
        .then(data => {
            // 댓글을 작성 후, 작성된 댓글을 화면에 즉시 추가
            const commentList = document.getElementById('commentList');
            const newComment = document.createElement('div');
            newComment.classList.add('comment-item');
            newComment.innerHTML = `
        <p><strong>${data.userId || '익명'}</strong></p>
        <p>${data.content || '내용 없음'}</p>
        <span>${data.created_at}</span>
      `;
            commentList.appendChild(newComment);

            // 폼 초기화
            document.querySelector("textarea[name='content']").value = '';
        })
        .catch(error => console.error('댓글 추가 실패:', error));
});

// 댓글 삭제
function deleteComment(commentId, boardId) {
    if (!confirm('정말로 삭제하시겠습니까?')) return;

    fetch(`/board/comment/delete`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ commentId }),
    })
        .then(response => response.json())
        .then(data => {
            alert('댓글이 삭제되었습니다.');
            updateComments(boardId); // 댓글 목록 새로고침
        })
        .catch(error => console.error('Error:', error));
}

// 댓글 목록 새로고침
function updateComments(boardId) {
    fetch(`/board/comments/${boardId}`)
        .then(response => response.json())
        .then(comments => {
            const commentList = document.getElementById('commentList');
            commentList.innerHTML = ''; // 기존 댓글 목록 초기화

            comments.forEach(comment => {
                const commentItem = document.createElement('div');
                commentItem.classList.add('comment-item');
                commentItem.innerHTML = `
                    <p><strong>${comment.userId}</strong></p>
                    <p>${comment.content}</p>
                    <p class="comment-date">${comment.created_at}</p>
                    <button class="delete-comment" onclick="deleteComment(${comment.commentId}, ${boardId})">삭제</button>
                `;
                commentList.appendChild(commentItem);
            });
        })
        .catch(error => console.error('Error:', error));
}
