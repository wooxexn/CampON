document.addEventListener('DOMContentLoaded', () => {
    const likeButton = document.querySelector('.like-button');
    const likeCountSpan = document.querySelector('.post-actions span');
    const boardId = likeButton.getAttribute('data-board-id'); // 게시글 ID를 버튼에서 가져오기

    // 서버에서 좋아요 상태를 가져와 초기화
    fetch(`/board/like/status/${boardId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('좋아요 상태를 불러오는 데 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            updateLikeUI(likeButton, likeCountSpan, data.isLiked, data.likeCount);
        })
        .catch(error => console.error('Error fetching like status:', error));
});

function toggleLike(boardId) {
    const likeButton = document.querySelector('.like-button');
    const likeCountSpan = document.querySelector('.post-actions span');
    const isLoggedIn = likeButton.getAttribute('data-is-logged-in') === 'true';

    // 로그인 상태 확인
    if (!isLoggedIn) {
        alert('좋아요를 누르려면 로그인해야 합니다.');
        return;
    }

    // 서버에 좋아요 요청 전송
    fetch(`/board/like/${boardId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 요청 실패');
            }
            return response.json();
        })
        .then(data => {
            updateLikeUI(likeButton, likeCountSpan, data.isLiked, data.likeCount);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('좋아요 처리 중 문제가 발생했습니다. 다시 시도해주세요.');
        });
}

/**
 * 좋아요 UI 업데이트 함수
 * @param {HTMLElement} likeButton 좋아요 버튼 엘리먼트
 * @param {HTMLElement} likeCountSpan 좋아요 개수 표시 엘리먼트
 * @param {boolean} isLiked 서버에서 반환된 좋아요 여부
 * @param {number} likeCount 서버에서 반환된 좋아요 개수
 */
function updateLikeUI(likeButton, likeCountSpan, isLiked, likeCount) {
    // 좋아요 상태 업데이트
    if (isLiked) {
        likeButton.classList.add('liked');
        likeButton.innerText = '💔 좋아요 취소';
    } else {
        likeButton.classList.remove('liked');
        likeButton.innerText = '❤️ 좋아요';
    }

    // 좋아요 개수 업데이트
    likeCountSpan.innerText = `좋아요: ${likeCount}`;
}

// 댓글 작성 후 새로 고침 없이 댓글을 추가하는 기능
document.getElementById("commentForm").addEventListener("submit", function(event) {
    event.preventDefault(); // 폼 제출의 기본 동작을 막음

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
            if (!commentList) {
                console.error('댓글 리스트 컨테이너가 없습니다.');
                return;
            }
            const noCommentsMessage = document.getElementById('noCommentsMessage');

            // "댓글이 없습니다." 메시지 제거
            if (noCommentsMessage) {
                noCommentsMessage.remove();
            }

            const newComment = document.createElement('div');
            newComment.classList.add('comment-item');
            newComment.innerHTML = `
                <p><strong>${data.userId || '익명'}</strong></p>
                <p>${data.content || '내용 없음'}</p>
                <span>${data.created_at}</span>
                <button class="delete-comment" onclick="deleteComment(${data.commentId}, ${boardId})">삭제</button>
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
        body: JSON.stringify({ commentId: commentId }), // JSON 형식으로 전달
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('댓글 삭제 요청 실패');
            }
            return response.json(); // JSON 응답을 파싱
        })
        .then(data => {
            if (data.success) {
                alert(data.message); // 성공 메시지
                updateComments(boardId); // 댓글 목록 새로고침
            } else {
                alert(data.message); // 실패 메시지
            }
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

            if (comments.length === 0) {
                commentList.innerHTML = '<p>댓글이 없습니다.</p>'; // 댓글 없음 메시지 표시
            } else {
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
            }
        })
        .catch(error => console.error('Error:', error));
}
