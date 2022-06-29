<%@ page contentType = "text/html; charset=utf-8" %>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<meta charset="utf-8">
<title>Triple Home Work !</title>
</head>
<body>

사용자ID : <input type= "text" id="userId" name="userId"> 
<label>장소</label>
<select id="place">
	<option value='seoul'>서울</option>
	<option value='daejeon'>대전</option>
	<option value='daegu'>대구</option>
	<option value='busan'>부산</option>
</select>
<button type="button" id="searchBtn">조회</button>
<div id='userPoint'></div>
<br>
<label id= 'regTitle' style= display:none>사용자 리뷰 등록</label>
<div id = 'regReviewDiv'>
	<textarea id = "reviewContent" style= display:none>
	</textarea>
	<button type="button" id="regBtn" style= display:none>등록</button>
</div>
<div id= "checkDiv" style= display:none>
	<input type= 'checkbox' name='checkPhoto' id= 'Rphoto1' value='photo1'/>사진1
	<input type= 'checkbox' name='checkPhoto' id= 'Rphoto2' value='photo2'/>사진2
	<input type= 'checkbox' name='checkPhoto' id= 'Rphoto3' value='photo3'/>사진3
</div>
<br>
사용자 리뷰 목록
<div id = 'textBox'>
	
</div>
<button id="modifyBtn" style= display:none>수정</button>
<button id="delBtn" style= display:none>삭제</button>
</body>
<script type="text/javascript">

	$(document).ready(function(){
		var userPoints = 0;		// userPoint 전역 변수 선언
		var beforeInfo = {		// 목록 조회 시 수정/삭제/등록에 사용될 데이터 obj 선언
				content : '',
				attachedPhotoIds : '',
				userId : '',
				reviewId : ''
		};
		
		// 사용자 정보 조회 이벤트
		$("#searchBtn").on("click",function(){
			
			var userId =  $("#userId").val(); // 필드 유저id
			var placeId = $("#place").val();  // 선택 장소id
			
			$.ajax({
				url : '/events/getUserInfo/'+userId+'/'+placeId,
				method : 'GET',
				contentType : 'application/json;charset=utf-8',
				dataType : 'json',
				erroe:function(error,status,msg){
					console.log("상태코드 " + status + "에러메시지" + msg);
				},
				success:function(result){
					console.log(result);
					
					 if(result.userPoint == ""){
						alert("등록된 사용자가 아닙니다 !");
						return;
					}
					var reviewList = new Array();
					var totalPoint =result.userPoint[0].userPoint;
					
					reviewList = result.userInfo;
					var html = '';
					var point = '';
					
					point += '<div id= "userPoint">';
					point += 'TRIPLE POINT : ';
					point += totalPoint;
					point += '</div>'
					
					$('#userPoint').empty();
					$('#userPoint').append(point);
					
					// ajax 통신 완료 후 html 생성
					 for(key in reviewList){
						html += '<div>';
						 html += '<textarea id="modiContent">';
						html += reviewList[key].content;
						html += '</textarea>'; 
						html += '</div>';
						html += '<div>';
						html += '<input type="checkbox" name="modPhoto" id= "photo1" value="photo1"/>사진1';
						html += '<input type="checkbox" name="modPhoto" id= "photo2" value="photo2"/>사진2';
						html += '<input type="checkbox" name="modPhoto" id= "photo3" value="photo3"/>사진3';
						html += '</div>';
					} 
					$('#textBox').empty();
					$('#textBox').append(html);
					
					// 신규 리뷰, 등록 리뷰에 따른 화면 show/hide 이벤트
					if(reviewList.length == 0){
						// 신규 리뷰 등록 버튼 show/hide
						$("#reviewContent").show();
						$("#regBtn").show();
						$("#checkDiv").show();
						$("#regTitle").show();
						$("#modifyBtn").hide();
						$("#delBtn").hide();
					}else{
						// 기존 리뷰 수정/삭제 show/hide
						$("#reviewContent").hide();
						$("#regBtn").hide();
						$("#checkDiv").hide();
						$("#modifyBtn").show();
						$("#delBtn").show();
						$("#regTitle").hide();
						//$("#reviewContent").show();
						//$("#regBtn").show();
					}
					userPoints = totalPoint;
					
					// 리뷰에 등록된 사진 유무 유효성 검사, 사진이 있으면 화면에 표시
					if(result.userInfo.length > 0){
						
						if(reviewList[0].attachedPhotoIds != null){
							var photos = reviewList[0].attachedPhotoIds;
							var photoList =[];
							photoList = photos.split(",");
							console.log(photoList);
							for(var i = 0; i < photoList.length; i++){
								$('input:checkbox[id="'+photoList[i]+'"]').prop("checked",true);
							} 
							
						}
					}else{
						return;
					}
					// 수정/삭제 이벤트에 사용할 데이터 세팅
					beforeInfo.content = result.userInfo[0].content;
					beforeInfo.attachedPhotoIds= result.userInfo[0].attachedPhotoIds;
					beforeInfo.userId= result.userInfo[0].userId;
					beforeInfo.reviewId= result.userInfo[0].reviewId;
				}
			});
		});
		
		
		// 리뷰 등록 클릭 이벤트
		$("#regBtn").on("click",function(){
			
			// 등록 파라미터 세팅
			var placeId = $("#place").val();
			var userId =  $("#userId").val();
			var content = "";
			var checkList = "";
			var checkData = $("input[name='checkPhoto']:checked");

			$(checkData).each(function(){
				checkList += ","+$(this).val();
			});
			
			var attachedPhotoIds = checkList.substr(1);
			// 등록일은 서버에서 현재로 날짜 처리
			// review Content 유효성 검사(공백x)
			 if($("#reviewContent").val().trim() == ""){
				alert("리뷰 내용을 입력해주세요 !");
				return;
			} 
				content = $("#reviewContent").val();
			
			// 리뷰 등록 파라미터 세팅
			var reviewInfo = {
				"placeId" : placeId,
				"userId"  : userId,
				"content" : content,
				"userPoint" : userPoints,
				"attachedPhotoIds" : attachedPhotoIds
			}
			
			$.ajax({
				url : '/events/regReview/',
				method : 'POST',
				contentType : 'application/json;charset=utf-8',
				dataType :'json',
				data : JSON.stringify(reviewInfo),
				erroe:function(error,status,msg){
					console.log("상태코드 " + status + "에러메시지" + msg);
				},
				success:function(result){
					
				}
				
			});
			alert("리뷰 등록이 완료되었습니다.");
			location.reload();
		});
		
		// 리뷰 수정 클릭 이벤트
		 $("#modifyBtn").on("click",function(){
			var content =$("#modiContent").val();	// 리뷰 내용 수정값
			var checkList = "";
			var checkData = $("input[name='modPhoto']:checked");
			var duplePoint = userPoints; // 포인트 값 변경 확인을 위한 변수

			$(checkData).each(function(){
				checkList += ","+$(this).val();
			});
			var attachedPhotoIds = checkList.substr(1); // 사진첨부 수정값
			
			// 등록된 리뷰의 첨부한 사진을 모두 지우면 점수 차감
			if(beforeInfo.attachedPhotoIds != "" && attachedPhotoIds.length == 0){
				var minuPoint =userPoints -1;
				userPoints = minuPoint;
			}
			// 사진이 없는 등록된 리뷰에 사진을 추가하면 점수 부여
			if(beforeInfo.attachedPhotoIds == "" && attachedPhotoIds.length > 0){
				var plusPoint =parseInt(userPoints) + 1;
				userPoints = plusPoint;
			}
			
			// 리뷰 수정 파라미터 세팅
			var modifyInfo = {
					"content" : content,
					"attachedPhotoIds" : attachedPhotoIds,
					"userPoint"	: userPoints,
					"userId"	: beforeInfo.userId,
					"reviewId"	: beforeInfo.reviewId,
					"duplePoint": duplePoint
			}
			var userId = beforeInfo.userId;
			  $.ajax({
				url : '/events/modifyReview/',
				contentType : 'application/json;charset=utf-8',
				type:'put',
				dataType :'json',
				data : JSON.stringify(modifyInfo),
				erroe:function(error,status,msg){
					console.log("상태코드 " + status + "에러메시지" + msg);
				},
				success:function(result){
					
				}
			});
			  alert("리뷰 수정이 완료되었습니다!");
			  location.reload();
		});
		
		// 리뷰 삭제 이벤트
		$('#delBtn').on("click",function(){
			
			// 리뷰 삭제 파라미터 세팅
			var delInfo = {
					"reviewId"	: beforeInfo.reviewId,
			}
			$.ajax({
				url : '/events/deleteReview/',
				contentType : 'application/json;charset=utf-8',
				type:'delete',
				dataType :'json',
				data : JSON.stringify(delInfo),
				erroe:function(error,status,msg){
					console.log("상태코드 " + status + "에러메시지" + msg);
				},
				success:function(result){
					
				}
			});
			alert("리뷰 삭제를 완료하였습니다!");
			location.reload();
		});
				
			
	});// end document
	
	
</script>
</html>























