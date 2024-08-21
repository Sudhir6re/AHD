var contextPath =
$(document).ready(function() {
	 contextPath = $("#appRootPath").val();
	  if ($('#cmbSchemeName').length) {
	        $('#cmbSchemeName').select2();
	    }
});


$("#delayedMonthAndYearCombos").hide()
$("#DAArrearsDatesDivDisplay").hide()
$("#PayArrearsDatesDivDisplay").hide()
function addMonthYearComboForDelayed() {
	var typeOfPaymentMaster = $("#cmbTypeOfPaymentMaster").val();
	if (typeOfPaymentMaster == '700047') {
		$("#delayedMonthAndYearCombos").fadeIn(100);
	} else {
		$("#delayedMonthAndYearCombos").hide()
	}

	if (typeOfPaymentMaster == '700048') {
		$("#DAArrearsDatesDivDisplay").fadeIn(100);
	} else {
		$("#DAArrearsDatesDivDisplay").hide();
	}

	if (typeOfPaymentMaster == '700049') {
		$("#PayArrearsDatesDivDisplay").fadeIn(100);
	} else {
		$("#PayArrearsDatesDivDisplay").hide();
	}
}



function funDdo1() {

	var schemeId = $('#billGroup').val();
	
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
			},
			beforeSend : function(){
				$( "#loaderMainNew").show();
				},
			complete : function(data){
				$( "#loaderMainNew").hide();
			},	
			success : function(data) {
				console.log(data);
				$("#txtSchemeName").val(data[0].schemeName);
				$("#txtSubSchemeName").val("-");
			}
		});
	}

}