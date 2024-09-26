var contextPath =
$(document).ready(function() {
	 contextPath = $("#appRootPath").val();
	  if ($('#cmbSchemeName').length) {
	        $('#cmbSchemeName').select2();
	    }
	  if ($('#billGroupId').length) {
		  $('#billGroupId').select2();
	  }
	  
	  
	  if ($('#ddoCode').length) {
		  $('#ddoCode').select2();
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


$("#delayedMonthAndYearCombos").hide();
$("#DAArrearsDatesDivDisplay").hide();
$("#PayArrearsDatesDivDisplay").hide();


$("#save").click(function(e){
    //	e.preventDefault();
     $("#onlineEntryForm").attr("action","../saveOnlineContriEntry");
	 $("#onlineEntryForm").submit();   
});


function changeDpDaAndContri(counter)
{
	var nthElement = counter ; 
	var basic = document.getElementById("basic"+nthElement).value ;
	var DP;
	var payCommission = document.getElementById("cmbPayCommission"+nthElement).value.trim();
	if(payCommission == '700015' || payCommission == '700345'){
		DP = Number(basic/2);
	}else{
		DP = 0;
	}
	var DARate = Number (document.getElementById("daRate"+nthElement).value);
	var DA = (Number(Number(basic)+Number(DP)) * DARate).toFixed(2);
	var tempcontribution = Number(basic) + Number(Math.round(DA)) + Number(DP); 
	var contribution = (Number( tempcontribution * 0.10)).toFixed(2);
	var contributionNps = (Number( tempcontribution * 0.14)).toFixed(2);
	document.getElementById("DP" + nthElement).value = Math.round(DP) ;
	document.getElementById("DA" + nthElement).value = Math.round(DA) ; 
	document.getElementById("contribution" + nthElement).value = Math.ceil(contribution) ;
	document.getElementById("contributionNps" + nthElement).value = Math.ceil(contributionNps) ;
}





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
	
	var paymentType = document.getElementById("typeOfPayment").value;
	var yearId = document.getElementById("finYearId").value;
	var monthId = document.getElementById("monthId").value;
	
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
			ddoCode : {
				required : true,
			},
			billGroupId : {
				required : true,
			},
			txtSchemeName : {
				required : true,
			},
			txtSubSchemeName : {
				required : true,
			},
			monthId : {
				required : true,
			},
			finYearId : {
				required : true,
			},

		},
		messages : {
			cmbTreasuryCode : {
				required : "Please select Treasury Name",
			},
			ddoCode : {
				required : "Please select DDO Name",
			},
			billGroupId : {
				required : "Please select Bill Group",
			},
			txtSchemeName : {
				required : "Please Insert Scheme",
			},
			txtSubSchemeName : {
				required : "Please Insert Sub Scheme",
			},
			monthId : {
				required : "Please select Pay Month",
			},
			finYearId : {
				required : "Please select Pay year",
			},

		},
		submitHandler : function(form) {
			form.submit();
		}
	});

});*/
