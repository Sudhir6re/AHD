var contextPath ="";
$(document).ready(function() {
	 contextPath = $("#appRootPath").val();
});

$("#cmbTaluka").select2();
$("#cmbDesignation").select2();
$("#stateId").select2();
$("#districtId").select2();
$("#talukaId").select2();
$("#txtTown").select2();
$("#instituteType").select2();
$("#cmbDesignation").select2();
$("#cmbBankName").select2();
$("#cmbBranchName").select2();

function funDdo1() {

		var schemeId = $('#cmbSchemeName').val();
		
		if(schemeId != "0"){
			removeErrorClass($("#schemeName"));
		}
	

		if (schemeId != '') {
			$.ajax({
				type : "GET",
				url : contextPath+"/ddoast/getSchemeCodeAgainstName/" + schemeId,
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

$(".officeName")
.click(
		function() {
			
			$('#approveDDOOffice').trigger("reset");
			var ddoCode=$(this).attr("value");
				
			 if (ddoCode !='') {
					
					$
							.ajax({
								type : "GET",
								url : contextPath+"/ddo/getAlreadySavedDataforDDO/"+ ddoCode,
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
									$.each(temp,function(index,value) {
												$('#txtNameOfOffice').val(value[0]);
												$('#stateId').val(value[1]);
												$('#districtId').val(value[2]);
												 $('#talukaId').val(value[3]);
												 $('#txtTown').val(value[4]);
												 $('#village').val(value[5]);
												 $('#txtAddress1').val(value[6]);
												 $('#txtPin').val(value[7]);
												 $('#ddoOffClass').val(value[8]);
												 $('#txtdiceCode').val(value[9]);
												 $('#txtGrant').val(value[10]);
												 $('#txtTelNo1').val(value[11]);
												 $('#txtfax').val(value[12]);
												 $('#txtEmail').val(value[14]);
												 $('#hiddenddoCode').val(ddoCode);
												 var tribalArea= value[13];
												 if(tribalArea=='Yes')
													 $('#RadioButtonTriableAreaYes').prop("checked",true);
												 else
													 $('#RadioButtonTriableAreaNo').prop("checked",true);
												 
												 var hillyArea= value[15];
												 if(tribalArea=='Yes')
													 $('#RadioButtonHillyAreaYes').prop("checked",true);
												 else
													 $('#RadioButtonHillyAreaNo').prop("checked",true);
												 
												 var naxaliteArea= value[16];
												 if(tribalArea=='Yes')
													 $('#RadioButtonNaxaliteAreaYes').prop("checked",true);
												 else
													 $('#RadioButtonNaxaliteAreaNo').prop("checked",true);
												 
												 
												 $('#txtDDOName').val(value[17]);
												 $('#cmbDesignation').val(value[18]);
												 $('#txtWEFDate').val(dateToYMD(value[19]));
												 $('#txtTANNo').val(value[20]);
												 $('#txtITWardCircle').val(value[21]);
												 $('#cmbBankName').val(value[22]);
												 $('#cmbBranchName').val(value[23]);
												 $('#txtIFSCCode').val(value[24]);
												 $('#txtAccountNo').val(value[25]);
												 $('#txtRemarks').val(value[26]);
												 $('#instituteType').val(value[27]);
												 
												
												
											});
									
									
									
									
								}
							});
					}else{
						swal("Please Select Atleast one Component");
					}

		});
$('#approve')
.click(
		function() {
			var ddoCode = $('#hiddenddoCode').val();
			var cityClass=$('#ddoOffClass').val();
			var flag=1;
			 if (ddoCode != '') {
				$
						.ajax({
							type : "GET",
							url : contextPath+"/ddo/updateApproveRejectStatus/"
									+ ddoCode +"/"+flag +"/"+cityClass,
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
									location.reload();

							}
						});
			}
		});
$('#btnRjct')
.click(
		function() {
			var ddoCode = $('#hiddenddoCode').val();
			var flag=2;
			if (ddoCode != '') {
				$
				.ajax({
					type : "GET",
					url : contextPath+"/ddo/updateApproveRejectStatus/"
						+ ddoCode +"/"+flag,
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
							location.reload();
							
							
						}
				});
			}
		});