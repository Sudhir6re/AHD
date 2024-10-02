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


/*$("#delayedMonthAndYearCombos").hide();
$("#DAArrearsDatesDivDisplay").hide();
$("#PayArrearsDatesDivDisplay").hide();*/


$("#save").click(function(e){
    //	e.preventDefault();
     $("#onlineEntryForm").attr("action",contextPath+"/ddoast/saveOnlineContriEntry");
	 $("#onlineEntryForm").submit();   
});

$("#trnDCPSTable").on('blur', ".basicPay", function() {
    var row = $(this).closest("tr");
    var basic = parseFloat(row.find('.basicPay').val());
    var payCommission = row.find('.payCommission').val();
    var DP;

    if (payCommission === '700015' || payCommission === '700345') {
        DP = basic / 2;
    } else {
        DP = 0;
    }

    var DARate = parseFloat(row.find('.daRate').val());
    var DA = ((basic + DP) * DARate).toFixed(2);
    var tempContribution = basic + Math.round(DA) + DP;
    var contribution = (tempContribution * 0.10).toFixed(2);
    var contributionNps = (tempContribution * 0.14).toFixed(2);

    row.find('.da').val(Math.round(DA));
    row.find('.contribution').val(Math.round(contribution));
    row.find('.emprContribution').val(Math.ceil(contributionNps));
});


$("#trnDCPSTable").on('blur', ".da", function() {
    var row = $(this).closest("tr");
    var basic = parseFloat(row.find('.basicPay').val());
    var payCommission = row.find('.payCommission').val();
    var DP;

    if (payCommission === '700015' || payCommission === '700345') {
        DP = basic / 2;
    } else {
        DP = 0;
    }

    //var DARate = parseFloat(row.find('.daRate').val()) || 0;
    var DA = parseFloat(row.find('.da').val()) || 0;  
    var tempContribution = basic + Math.round(DA) + DP;
    var contribution = (tempContribution * 0.10).toFixed(2);
    var contributionNps = (tempContribution * 0.14).toFixed(2);

    row.find('.da').val(Math.round(DA));
    row.find('.contribution').val(Math.round(contribution));
    row.find('.emprContribution').val(Math.ceil(contributionNps));
});



$("#trnDCPSTable").on('blur', ".endDate, .startDate,.payCommission", function() {
    var row = $(this).closest("tr");
    var startDate = row.find('.startDate').val();
    var endDate = row.find('.endDate').val();

    
    
    if (endDate!='' && (startDate > endDate) ) {
        swal('Select start date less than or equal to End Date');
        row.find('.startDate').val("");
        row.find('.endDate').val("");
    } else {
        var sevaarthId = row.find('.sevaarthId').val();
        var typeOfPayment = row.find('.typeOfPayment').val();
        var payCommission = row.find('.payCommission').val();
        
        var monthId = $('#monthId').val();
        var finYearId = $('#finYearId').val();
        var noOfDays = (new Date(endDate) - new Date(startDate)) / (1000 * 60 * 60 * 24) + 1; 

        var params = {
        	startDate: startDate,
        	endDate: endDate,
        	sevaarthId: sevaarthId,
        	typeOfPayment: typeOfPayment,
        	payCommission: payCommission,
        	monthId: monthId,
        	finYearId: finYearId
        };

        $.ajax({
            type: "POST",
            url: contextPath+'/ddoast/calculateDcpsArrear',
            data: JSON.stringify(params), 
            contentType: 'application/json',
            error: function(xhr, status, error) {
                console.error('Error occurred:', error);
            },
            success: function(data) {
                console.log(data);
                if (data.length !== 0) {
                    row.find('.basicPay').val(data.basicPay.toFixed(2));
                    row.find('.da').val(data.da.toFixed(2));
                    row.find('.daRate').val(data.daRate);
                    row.find('.contribution').val(data.contribution.toFixed(2));
                    row.find('.emprContribution').val(data.emprContribution.toFixed(2));
                }
            }
        });
    }
});








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
