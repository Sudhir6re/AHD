	var contextPath =
$(document).ready(function() {
	 contextPath = $("#appRootPath").val();
});

$('#btnApprove')
							.click(
									function() {
										var zpDdoCode = $('#zpDdoCode').val();
										var flag=1;
										 if (zpDdoCode != '') {
											$
													.ajax({
														type : "GET",
														url : contextPath"/ddo/updateApproveStatus/"
																+ zpDdoCode +"/"+flag,
														async : true,
														contentType : 'application/json',
														error : function(data) {
															console.log(data);
														},
														success : function(data) {
															console.log(data);
															// alert(data);
																swal(
																		"Approved Successfully",
																		{
																			icon : "success",
																		});
															

														}
													});
										}
									});
$('#btnReject')
.click(
		function() {
			var zpDdoCode = $('#zpDdoCode').val();
			var flag=2;
			if (zpDdoCode != '') {
				$
				.ajax({
					type : "GET",
					url : contextPath+"/ddo/updateApproveStatus/"
						+ zpDdoCode +"/"+flag,
						async : true,
						contentType : 'application/json',
						error : function(data) {
							console.log(data);
						},
						success : function(data) {
							console.log(data);
							// alert(data);
							swal(
									"Rejected Successfully",
									{
										icon : "success",
									});
							
							window.location.href = contextPath+"/ddo/approveDdoOfficeDataList";
						}
				});
			}
		});