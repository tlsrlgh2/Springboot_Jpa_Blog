let index = {
		
		init: function() {
			$("#btn-save").on("click", ()=>{	// function() {} 대신 ()=>{} 을 사용하는 이유는 this를 바인딩 하기 위함.
				this.save();
				
			});
		},
		
		save: function() {
			let data = {
				username: $("#username").val(),
				password: $("#password").val(),
				email: $("#email").val()
			};

			// ajax통신을 이용해서 3개의 데이터를 json으로 변경하는 insert 요청
			// ajax호출시 default가 비동기 호출이다
			$.ajax({
				//회원 가입 수행 요청
				type:"post",
				url:"/blog/api/user",
				data:JSON.stringify(data),	//http body 데이터이다
				contentType:"application/json; charset=utf-8",	// body데이터가 어떤 타입인지 작성해주는것
				dataType: "json"	// 요청을 했을경우 응답이 왔을때 기본적으로 모든것을 문자열로 받는데
								// 문자열의 타입이 json이라면 javascript로 변경을 해준다
				success: function(res){
					alert(res+"성공1111");
				},
				error:function(res){
					alert("실패메세지");
				}
			});
//			}).success: (function (res){
//				// 성공시 이쪽 코드
//				alert(res+"성공");
//				
//			}).fail(function (error) {
//				// 실패시 이쪽 코드
//				alert("실패메세지");
//				console.log(JSON.stringify(error));
//
//			});	
			
			// console.log(data);
		}
}

index.init();
