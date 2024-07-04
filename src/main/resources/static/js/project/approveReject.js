$('#btnApprove')
							.click(
									function() {
										var zpDdoCode = $('#zpDdoCode').val();
										var flag=1;
										 if (zpDdoCode != '') {
											$
													.ajax({
														type : "GET",
														url : "../ddo/updateApproveStatus/"
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
																setTimeout(
																		function() {
																			location
																					.reload(true);
																		}, 3000);
															

														}
													});
										}
									});