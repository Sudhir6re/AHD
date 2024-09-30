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




function changeContriForSelectedPrd(counter) {
    var str3 = document.getElementById("startDate" + counter).value.trim(); // Contribution Start Date
    var str4 = document.getElementById("endDate" + counter).value.trim(); // Contribution End Date
    
    var startDateForThisMonth = document.getElementById("hidEmpStartDate" + counter).value; // You may need to define this
    var endDateForThisMonth = document.getElementById("hidEmpEndDate" + counter).value; // You may need to define this
    
    if (str3 !== "" && str4 !== "") {
        var dt3 = parseInt(str3.substring(8, 10), 10);
        var mon3 = parseInt(str3.substring(5, 7), 10);
        var yr3 = parseInt(str3.substring(0, 4), 10);

        var dt4 = parseInt(str4.substring(8, 10), 10);
        var mon4 = parseInt(str4.substring(5, 7), 10);
        var yr4 = parseInt(str4.substring(0, 4), 10);
        
        var date3 = new Date(yr3, mon3 - 1, dt3);
        var date4 = new Date(yr4, mon4 - 1, dt4);
        
        // Assuming startDateForThisMonth and endDateForThisMonth are defined and formatted correctly
        var dt5 = parseInt(startDateForThisMonth.substring(8, 10), 10);
        var mon5 = parseInt(startDateForThisMonth.substring(5, 7), 10);
        var yr5 = parseInt(startDateForThisMonth.substring(0, 4), 10);
        
        var dt6 = parseInt(endDateForThisMonth.substring(8, 10), 10);
        var mon6 = parseInt(endDateForThisMonth.substring(5, 7), 10);
        var yr6 = parseInt(endDateForThisMonth.substring(0, 4), 10);
        
        var date5 = new Date(yr5, mon5 - 1, dt5);
        var date6 = new Date(yr6, mon6 - 1, dt6);
       
        var contributionDays = daysBetween(date3, date4);
        var totalDaysInMonth = daysBetween(date5, date6);
        
        var nthElement = counter; 
        var basic = document.getElementById("basicPay" + nthElement).value;
        var DP;
        var payCommission = document.getElementById("payCommission" + nthElement).value.trim();
        
        if (payCommission === '700015' || payCommission === '700345') {
            DP = Number(basic) / 2;
        } else {
            DP = 0;
        }

        var DARate = Number(document.getElementById("daRate" + nthElement).value);
        var DA = (Number(Number(basic) + Number(DP)) * DARate).toFixed(2);
        var tempContribution = Number(basic) + Number(Math.round(DA)) + Number(DP);
        var contribution = (Number(tempContribution * 0.10)).toFixed(2);

        var contributionNps = 0;
        let date1 = new Date(str3).getTime();
        let date2 = new Date("2019-04-01").getTime();
        
        if (date1 < date2) {
            contributionNps = (Number(tempContribution * 0.10)).toFixed(2);
        } else if (date1 > date2) {
            contributionNps = (Number(tempContribution * 0.14)).toFixed(2);
        }
        
        var newBasic = (Number(basic) * Number(contributionDays) / Number(totalDaysInMonth));
        var newDP = (Number(DP) * Number(contributionDays) / Number(totalDaysInMonth));
        var newDA = (Number(DA) * Number(contributionDays) / Number(totalDaysInMonth));
        var newContri = (Number(contribution) * Number(contributionDays) / Number(totalDaysInMonth));
        var newContriNps = (Number(contributionNps) * Number(contributionDays) / Number(totalDaysInMonth));

        document.getElementById("basicPay" + nthElement).value = Math.round(newBasic);
        document.getElementById("DP" + nthElement).value = Math.round(newDP); // Ensure you have an element with this ID
        document.getElementById("da" + nthElement).value = Math.round(newDA); // Ensure you have an element with this ID
        document.getElementById("contribution" + nthElement).value = Math.ceil(newContri);
        document.getElementById("contributionNps" + nthElement).value = Math.ceil(newContriNps); // Ensure you have an element with this ID
    }
}

function daysBetween(first, second) {
    var one = new Date(first.getFullYear(), first.getMonth(), first.getDate());
    var two = new Date(second.getFullYear(), second.getMonth(), second.getDate());
    var millisecondsPerDay = 1000 * 60 * 60 * 24;
    var millisBetween = two.getTime() - one.getTime();
    var days = millisBetween / millisecondsPerDay;
    days = Number(days) + 1;
    return Math.floor(Number(days));
}










$(document).on('click', '.addRow', function() {
	
	var rowIndex=$('#trnDCPSTable>tr').length;
	
	 var currentRow = $(this).closest('tr').clone();
	 currentRow.find(".employeeName").attr("id","employeeName"+rowIndex);
	 
	 
	 currentRow.find('.dcpsNO')
     .attr('name', 'lstDcpContributionModel[' + rowIndex + '].dcpsNO')
     .attr('id', 'dcpsNO' + rowIndex);
	 
	 
	 currentRow.find('.startDate')
       .attr('name', 'lstDcpContributionModel[' + rowIndex + '].startDate')
       .attr('id', 'startDate' + rowIndex)
       .val('');
	 
   
	 currentRow.find('.endDate')
       .attr('name', 'lstDcpContributionModel[' + rowIndex + '].endDate')
       .attr('id', 'endDate' + rowIndex)
       .val('');
	 
	 
	 currentRow.find('.payCommission')
     .attr('name', 'lstDcpContributionModel[' + rowIndex + '].payCommission')
     .attr('id', 'payCommission' + rowIndex)
     .val("0");
	 
	 
	 currentRow.find('.dcpContributionId')
     .attr('name', 'lstDcpContributionModel[' + rowIndex + '].dcpContributionId')
     .attr('id', 'dcpContributionId' + rowIndex);
    // .val("0");
	 
	 
	 currentRow.find('.typeOfPayment')
	 .attr('name', 'lstDcpContributionModel[' + rowIndex + '].typeOfPayment')
	 .attr('id', 'typeOfPayment' + rowIndex)
	 .val("0");
	 
	 currentRow.find('.basicPay')
	 .attr('name', 'lstDcpContributionModel[' + rowIndex + '].basicPay')
	 .attr('id', 'basicPay' + rowIndex);
	 //.val("0");
	 
	 
	 currentRow.find('.sevaarthId')
	 .attr('name', 'lstDcpContributionModel[' + rowIndex + '].sevaarthId')
	 .attr('id', 'sevaarthId' + rowIndex);
	 
	 
	 
	 currentRow.find('.dcpEmpId')
	 .attr('name', 'lstDcpContributionModel[' + rowIndex + '].dcpEmpId')
	 .attr('id', 'dcpEmpId' + rowIndex);
	 
	 
	 currentRow.find('.daRate')
	 .attr('name', 'lstDcpContributionModel[' + rowIndex + '].daRate')
	 .attr('id', 'daRate' + rowIndex);
	 
	 
	 currentRow.find('.da')
	 .attr('name', 'lstDcpContributionModel[' + rowIndex + '].da')
	 .attr('id', 'da' + rowIndex);
	 
	 
	 currentRow.find('.contribution')
	 .attr('name', 'lstDcpContributionModel[' + rowIndex + '].contribution')
	 .attr('id', 'contribution' + rowIndex)
	 .val("0");
	 
	 
	 currentRow.find('.emprContribution')
	 .attr('name', 'lstDcpContributionModel[' + rowIndex + '].emprContribution')
	 .attr('id', 'emprContribution' + rowIndex)
	 .val("0");
	 
	 
	 currentRow.find('.hidBasic').attr('id', 'hidBasic' + rowIndex);
	 
	 
	 currentRow.find('.regStatus').attr('text', 'Not Saved');
	 
      $('#trnDCPSTable').append(currentRow);
});

$(document).on('click', '.deleteRow', function() {
    $(this).closest('tr').remove();
});




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

	var billGroupId = $('#billGroupId').val();
	
	if(billGroupId != "0"){
		removeErrorClass($("#billGroupId"));
	}

	if (billGroupId != '') {
		$.ajax({
			type : "GET",
			url : contextPath+"/ddoast/getSchemeCodeByBillGroupId/" + billGroupId,
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
			$(".trnDCPSTable").empty();
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
