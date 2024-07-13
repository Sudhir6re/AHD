function funDdo1() {

		var schemeId = $('#cmbSchemeName').val();
		
		if(schemeId != "0"){
			removeErrorClass($("#schemeName"));
		}
	

		if (schemeId != '') {
			$.ajax({
				type : "GET",
				url : "../ddoast/getSchemeCodeAgainstName/" + schemeId,
				async : true,
				contentType : 'application/json',
				error : function(data) {
					console.log(data);
					/* alert("eror is" + data); */
				},

				success : function(data) {
					console.log(data);
					$('#txtSchemeCode').val(0.);
					$("#txtSchemeCode").val(data[0].schemeCode);
					$("#cmbSubschemeName").val(data[0].schemeName);
					$("#RadioPermenantTempBothP").val(data[0].schemeType);
					/*var temp=data
					 $("#txtSchemeCode").val('');
					$.each(temp, function(index, value) {
						$.each(value, function(index, value1) {
							//console.log(index + "-" + value1);
							if(index=="txtSchemeCode"){
								console.log("txtSchemeCode");
							
							}
						});
					}); */
				}
			});
		}

	}

$(".billGrpId")
.click(
		function() {
			
			$('#frmDdoGroupBill').trigger("reset");
			var billGrpId=$(this).text();
				
			 if ((billGrpId !='')) {
					
					$
							.ajax({
								type : "GET",
								url : "../ddoast/getBillDtlsForAlreadySaved/"+ billGrpId,
								async : true,
								contentType : 'application/json',
								error : function(
										data) {
									console.log(data);
									
									$(".loaderMainNew").hide();
									// alert(data);
								},
								success : function(
										data) {
									
									
									var temp=data;
									$(".loaderMainNew").hide();
									//$(".txtSchemeCode").val(data[3]);
									
									
									$.each(temp,function(index,value) {
												$('#txtSchemeCode').val(value[3]);
												$('#txtDescription').val(value[0]);
												 $('#cmbSchemeName').val(value[3]);
												 $('#txtBillGroupNo').val(value[5]); 
												 var typePost= value[2];
												 if(typePost=='P')
													 $('#RadioPermenantTempBothP').prop("checked",true);
												 else if(typePost=='T')
													 $('#RadioPermenantTempBothT').prop("checked",true);
												 else
													 $('#RadioPermenantTempBothB').prop("checked",true);
												 
												var gropuValues=value[1].split(",");
												
												for(var i=0;i<gropuValues.length;i++){
													$('input[name="group"][value="'+ gropuValues[i]+ '"]').prop("checked",true);
												}
												
												
											});
									
									
									
									
								}
							});
					}else{
						swal("Please Select Atleast one Component");
					}

		});