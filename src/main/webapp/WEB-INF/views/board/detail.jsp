<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>
<div class="container">
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<!-- 작성자와 로그인된 사용자가 같을경우에만 삭제,수정 버튼 활성화 -->
	<c:if test="${board.user.id == principal.user.id }">
		<a href="/board/${board.id}/updateform" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	
	<br><br>
	<div>
		글 번호 : <span id="id"><i>${board.id} </i></span>
		작성자 : <span><i>${board.user.username} </i></span>
	</div>
	<br>
	<div>
		<h3>${board.title}</h3>
	</div>
	<hr/>
	<div>
		<div>${board.content}</div>
	</div>
	<hr/>
</div>

<script src="/js/board.js">
</script>
<%@include file="../layout/footer.jsp"%>
</body>
</html>