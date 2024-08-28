var contextPath =
$(document).ready(function() {
	 contextPath = $("#appRootPath").val();
	  if ($('#cmbSchemeName').length) {
	        $('#cmbSchemeName').select2();
	    }
});
jQuery(document).ready(function($) {
	var varMessage = $("#message").val();
	if(varMessage != "" && varMessage != undefined) {
		swal(''+varMessage, {
  	      icon: "success",
  	  });
	}
	});
$("#delayedMonthAndYearCombos").hide()
$("#DAArrearsDatesDivDisplay").hide()
$("#PayArrearsDatesDivDisplay").hide()

function addMonthYearComboForDelayed() {
    var user = $("#levelRoleVal").val().trim();
     var use = $("#Use").val().trim();
    
    if (user == 3  && use=="ViewAll") { 
        var typeOfPaymentMaster = $("#typeOfPayment").val().trim();
        
        // Handle delayed month and year combo visibility
        if (typeOfPaymentMaster == '700047') {
            $("#delayedMonthAndYearCombos").show();
        } else {
            $("#delayedMonthAndYearCombos").hide();
        }
        
        // Handle DA Arrears Dates visibility
        if (typeOfPaymentMaster == '700048') {
            $("#DAArrearsDatesDivDisplay").show();
        } else {
            $("#DAArrearsDatesDivDisplay").hide();
        }
        
        // Handle Pay Arrears Dates visibility
        if (typeOfPaymentMaster == '700049') {
            $("#PayArrearsDatesDivDisplay").css("display", "inline");
        } else {
            $("#PayArrearsDatesDivDisplay").hide();
        }
    }
}



function funDdo1() {

	var schemeId = $('#billGroupId').val();
	
	if(schemeId != "0"){
		removeErrorClass($("#txtSchemeName"));
	}

	if (schemeId != '') {
		$.ajax({
			type : "GET",
			url : contextPath+"/ddoast/getSchemeCodeAgainstName/" + schemeId,
			async : true,
			contentType : 'application/json',
			error : function(data) {
				console.log(data);
				$( "#loaderMainNew").hide();
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
				$("#schemeCode").val(data[0].schemeCode);
				//$("#txtSubSchemeName").val("-");
			}
		});
	}

}



var status = false;
function SearchEmployee(e) {
	
	$('#action').val('SEARCH_EMP');
	
	var paymentType = document.getElementById("paymentType").value;
	var yearId = document.getElementById("paybillYear").value;
	var monthId = document.getElementById("paybillMonth").value;
	
	if(paymentType == "" || paymentType=='0')
	{
		swal('Please Select Payment Type');
		return false;
	}
	if(yearId == 0)
	{
		swal('Please enter pay year');
		return false;
	}
	if(monthId == 0)
	{
		swal('Please enter pay month');
		return false;
	}
	var flag=true;
		if(flag){
			$(".tblEmpDtlsBody").empty();
			$("#onlineEntryForm").submit();   
			return true;
		}
	
}

/*
$(document).ready(function() {
	$("form[name='onlineEntryForm']").validate({
		ignore : "",
		rules : {
			cmbTreasuryCode : {
				required : true,
			},
			cmbDDOCode : {
				required : true,
			},
			cmbBillGroup : {
				required : true,
			},
			txtSchemeName : {
				required : true,
			},
			txtSubSchemeName : {
				required : true,
			},
			cmbMonth : {
				required : true,
			},
			cmbYear : {
				required : true,
			},

		},
		messages : {
			cmbTreasuryCode : {
				required : "Please select Treasury Name",
			},
			cmbDDOCode : {
				required : "Please select DDO Name",
			},
			cmbBillGroup : {
				required : "Please select Bill Group",
			},
			txtSchemeName : {
				required : "Please Insert Scheme",
			},
			txtSubSchemeName : {
				required : "Please Insert Sub Scheme",
			},
			cmbMonth : {
				required : "Please select Pay Month",
			},
			cmbYear : {
				required : "Please select Pay year",
			},

		},
		submitHandler : function(form) {
			form.submit();
		}
	});

});
*/