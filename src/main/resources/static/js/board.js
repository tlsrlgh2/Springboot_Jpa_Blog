let index = {
		
		init: function() {
			$("#btn-save").on("click", ()=>{	
				this.save();
				console.log("save함수 ...");
			});
			$("#btn-delete").on("click", ()=>{	
				this.deleteByid();
				console.log("delete함수 ...");
			});
			$("#btn-update").on("click", ()=>{	
				this.update();
				console.log("update함수 클릭 ..");
			});
		},
		
		save: function() {
				console.log("save함수 ...");
			let data = {
				title: $("#title").val(),
				content: $("#content").val()
			};
			
			$.ajax({
				
				type:"post",
				url:"/api/board",
				data:JSON.stringify(data),
				contentType:"application/json; charset=utf-8",
				dataType: "json",
								
				success: function(res){
					alert("글쓰기가 완료되었습니다");
					location="/";
				},
				error:function(error){
					alert(JSON.stringify(error));
				}
			});
		},
		deleteByid: function() {
			console.log("deleteByid함수 호출 ...");
			var id = $("#id").text();
			console.log("id값 출력"+id);

			$.ajax({
				
				type:"delete",
				url: "/api/board/" + id,
				dataType: "json",
								
				success: function(res){
					alert("삭제가 완료되었습니다");
					location="/";
				},
				error:function(error){
					alert(JSON.stringify(error));
				}
			});
		}, 
		update: function () {
			let id = $("#id").val();
			let id2 = $("#id").text();
			console.log(id);
			console.log(id2);
			
			let data = {
				title: $("#title").val(),
				content: $("#content").val(),
			};

			$.ajax({

				type: "put",
				url: "/api/board/"+id,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json",

				success: function (res) {
					alert("글 수정이 완료되었습니다");
					location="/";
				},
				error: function (error) {
					alert(JSON.stringify(error));
				}
			});
		}
}

index.init();
