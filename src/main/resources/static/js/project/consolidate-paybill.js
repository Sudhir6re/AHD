jQuery(document).ready(function($) {
	$('#tblConsolidatePayBill').hide();
	$('#tblShowPayBill').hide();
});

var paybillGenerationTransactionIdArr = []; 
var grossAmtAr = []; 
var netAmtAr = []; 

//var schemeNameArr; 
//var schemeCodeArr;
var statusArr;
// var department_allowdeduc_id = []; 
var ddoCodeArr=[];
$("#selectAll").change(function() {
	var isChecked = $(this).prop("checked");
	console.log(isChecked);
	if (isChecked == true) {
		$(".payBillId").prop("checked", true);
	} else if (isChecked == false) {
		$(".payBillId").prop("checked", false);
	}
});


$("#tblShowPayBill").on(
		'change',
		'.payBillId',
		function() {
		//alert($(this).attr("data-status"));
			// get the current row
			var currentRow = $(this).closest("tr");
			var paybillgenId = currentRow.find("td:eq(1)").text(); // get
//			var schemeNamev = currentRow.find("td:eq(4)").text();
			// alert("schemeNamev"+schemeNamev);
//			var schemeCodev = currentRow.find("td:eq(3)").text();
			// alert("schemeCodev"+schemeCodev);
			var statusv = $(this).attr("data-status");
			
			var ddoCode = currentRow.find("td:eq(6)").text();
			var grossAmt = currentRow.find("td:eq(3)").text();
			var netAmt = currentRow.find("td:eq(4)").text();
			//alert("grossAmtAr"+grossAmt);
			// alert("statusv"+statusv);
			if ($(this).is(":checked")) {
				
				if(statusv=='6'){
					paybillGenerationTransactionIdArr.push(paybillgenId);
					ddoCodeArr.push(ddoCode);
					/*schemeNameArr=schemeNamev;
					schemeCodeArr=schemeCodev;*/
			        grossAmtAr.push(grossAmt);
			        netAmtAr.push(netAmt);
					statusArr=statusv;
				}
			
				
			} else if ($(this).is(":not(:checked)")) {
				paybillGenerationTransactionIdArr = $.grep(
						paybillGenerationTransactionIdArr, function(value) {
							return value != paybillgenId;
						});
				
				
				grossAmtAr = $.grep(
						grossAmtAr, function(value) {
							return value != grossAmt;
						});
				
				netAmtAr = $.grep(
						grossAmtAr, function(value) {
							return value != netAmt;
						});
				
				
			
				
				ddoCodeArr = $.grep(
						ddoCodeArr, function(value) {
							return value != ddoCode;
						});

				
			}
			
		});

$("#btnSearch")
		.click(
				function(e) {
					// e.preventDefault();
//					var schemeCode = $("#schemeCode").val();
					var billStatus = $("#billStatus").val();
					var yearName = $("#yearName").val();
					var monthName = $("#monthName").val();
					
					
					$("#btnConsolidatePaybill").attr("disabled", true);

					/*if (schemeCode == "" || schemeCode == "0") {
						e.preventDefault();
						swal("Please select scheme code");
					} else*/ if (billStatus == "" || billStatus == "0") {
						e.preventDefault();
						swal("Please select billStatus");
					} else if (yearName == "" || yearName == "0") {
						e.preventDefault();
						swal("Please select year");
					} else if (monthName == "" || monthName == "0") {
						e.preventDefault();
						swal("Please select month");
					} else {
						$
								.ajax({
									type : "GET",
									url : "../ddo/getBillsForConsolidation/"
//											+ schemeCode
//											+ "/"
											+ billStatus
											+ "/" + yearName + "/" + monthName,
									async : false,
									error : function(data) {
										console.log(data);
									},
									success : function(data) {
										console.log("code updated");
										console.log(data);
										$('#tblShowPayBill').show();
										$('#tblShowPayBill_wrapper').show();
										$('#tblShowPayBill tbody').html('');
										if (parseInt(data.totalRows) > 0) {
											$("#tblShowPayBill").dataTable()
													.fnClearTable();
//											var paybillGenerationTrnId,status,billDescription, schemeCode, schemeName, billGrossAmt, billNetAmt, isActive, ddoCode;
											var paybillGenerationTrnId,status,billDescription,  billGrossAmt, billNetAmt, isActive, ddoCode;
											$
													.each(
															data.resultData,
															function(i, result) {
																paybillGenerationTrnId = result.paybillGenerationTrnId
																billDescription = result.billDescription;
//																schemeCode = result.schemeCode;
//																schemeName = result.schemeName;
																billGrossAmt = result.billGrossAmt;
																billNetAmt = result.billNetAmt;
																ddoCode = result.ddoCode;
                                                                status=result.isActive;
																var chk = "<input type='checkbox' name='payBillId' class='payBillId' data-pid='"
																		+ paybillGenerationTrnId
																		+ "' data-status='"
																		+ status
																		+ "'  value='"
																		+ paybillGenerationTrnId
																		+ "'>";
																if (result.isActive == 5) {
																	isActive = '<span class="label label-success text-center payBillTrnId" data="'
																			+ paybillGenerationTrnId
																			+ '">Paybill Generated</span>';
																} else if (result.isActive == 6) {
																	isActive = '<span class="label label-success text-center" data="'
																			+ paybillGenerationTrnId
																			+ '">Forwarded to Level 2</span>';
																	
																	$("#btnConsolidatePaybill").attr("disabled", false);
																}
																else if (result.isActive == 9) {
																	isActive = '<span class="label label-success text-center" data="'
																		+ paybillGenerationTrnId
																		+ '">Consolidate Paybill</span>';
																
																$("#btnConsolidatePaybill").attr("disabled", false);
															}
																
																else if (result.isActive == 10) {
																	isActive = '<span class="label label-success text-center" data="'
																		+ paybillGenerationTrnId
																		+ '">Paybill Forward to BEAMS</span>';
																	
																	$("#btnConsolidatePaybill").attr("disabled", false);
																}
																else if (result.isActive == 11) {
																	isActive = '<span class="label label-success text-center" data="'
																		+ paybillGenerationTrnId
																		+ '">Paybill Approve from  Beams</span>';
																	
																	$("#btnConsolidatePaybill").attr("disabled", false);
																}
																else if (result.isActive == 12) {
																	isActive = '<span class="label label-success text-center" data="'
																		+ paybillGenerationTrnId
																		+ '">Paybill Rejected by Beams</span>';
																	
																	$("#btnConsolidatePaybill").attr("disabled", false);
																}
																else if (result.isActive == 14) {
																	isActive = '<span class="label label-success text-center" data="'
																		+ paybillGenerationTrnId
																		+ '">Bill Approved</span>';
																	
																	$("#btnConsolidatePaybill").attr("disabled", false);
																}
																
																else if (result.isActive == 15) {
																	isActive = '<span class="label label-success text-center" data="'
																			+ paybillGenerationTrnId
																			+ '">Consolidate Paybill Deleted</span>';
																}
																$(
																		"#tblShowPayBill")
																		.dataTable()
																		.fnAddData(
																				[
																						chk,
																						/*++i,*/
																						paybillGenerationTrnId,
																						billDescription,
//																						schemeCode,
//																						schemeName,
																						billGrossAmt,
																						billNetAmt,
																						isActive,ddoCode ]);
															});
										}
									}
								});
					}
				});




//save data
$("#btnConsolidatePaybill").click(
		function(e) {
			// e.preventDefault();
			var yearName = $("#yearName").val();
			var monthName = $("#monthName").val();
			var userId = $("#userId").val();
			console.log("status" + statusArr + "month"
					+ monthName + "yearname" + yearName + "paygiltnis"
					+ paybillGenerationTransactionIdArr + "grossAmtAr" + grossAmtAr + "netAmtAr" + netAmtAr);

			
			/*alert("statusArr--"+statusArr);
			alert("monthName--"+monthName);
			alert("yearName--"+yearName);
			alert("paybillGenerationTransactionIdArr--"+paybillGenerationTransactionIdArr);
			alert("grossAmtAr--"+grossAmtAr);
			alert("netAmtAr--"+netAmtAr);
			alert("ddoCodeArr--"+ddoCodeArr);*/
			
			
			
			    if(paybillGenerationTransactionIdArr.length>0){
			    	$.ajax({
						type : "GET",
						url : "../ddo/saveConsolidated/"  + statusArr + "/" + monthName
								+ "/" + yearName + "/"
								+ paybillGenerationTransactionIdArr+"/" + ddoCodeArr + "/" + grossAmtAr +"/" + netAmtAr +"/"+userId,
						async : false,
						error : function(data) {
							console.log(data);
						},
						success : function(data) {
							console.log("code updated");
							//console.log(data);
							//if (parseInt(data.totalRows) > 0) {
							//	swal("Bill consolidated successfully !", "", "success");
							//$(".swal-button--cancel").hide();
								 swal({
									   title: "",
									   text: "Bill consolidated successfully !!!",
									   buttons: true
									}).then(function(){
										location.href='../moderator/viewDelconsolidatePayBill';
									});
								
								 
								 $(".swal-button--cancel").hide();
							//}
							//location.href='../paybill/viewDelconsolidatePayBill';
						}
					});
			    }else{
			    	swal("Bill already consolidated !!!");
			    }
			
			
			
			
			

		});
