var contextPath ="";
jQuery(document).ready(function($) {
	 contextPath = $("#appRootPath").val();
	var varMessage = $("#message").val();
	if(varMessage != "" && varMessage != undefined) {
		swal(''+varMessage, {
  	      icon: "success",
  	  });
	}
	
	var table = $('#tblDataTable').DataTable();
	
    $('#departmentAllowdeducCode').on('change', function() {
    	var depName=$(this).attr("data2");
        table.column(1).search(depName).draw(); 
    });
    
    $("#isType").change(function(){
  	  var isType=$("#isType").val();
  		$.ajax({
  			type : "GET",
  			url : contextPath+"/mdc/AllowanceDeductionWiseMaster/"
  						+ isType,
  			async : true,
  			contentType : 'application/json',
  			error : function(data) {
  				 console.log(data);
  			},
  			success : function(data) {
  				 console.log(data);
  				var len = data.length;
  				if (len != 0) { 
  				$("#departmentAllowdeducCode").empty();
  				 var newOption = $('<option value="0">Please Select</option>');
              	 $('#departmentAllowdeducCode').append(newOption);
                  for(var i=0;i<len;i++){
                  	  newOption = $('<option value="'+data[i].departmentAllowdeducCode+'">'+data[i].departmentAllowdeducName+'</option>');
                  	 $('#departmentAllowdeducCode').append(newOption);
                  }				
  			   }
  			}
  		});
       });
    
    
        $('form').on('submit', function (event) {
           

            var isType = $('#isType').val();
            var departmentAllowdeducCode = $('#departmentAllowdeducCode').val();
            var payCommissionCode = $('#payCommissionCode').val();
            var amount = $('#amount').val().trim();
            var startDate = $('#startDate').val().trim();
            var endDate = $('#endDate').val().trim();
            var percentage = $('#percentage').val().trim();
            var minBasic = $('#minBasic').val().trim();
            var maxBasic = $('#maxBasic').val().trim();
            var cityClass = $('#cityClass').val();
            var gradePayLower = $('#gradePayLower').val().trim();
            var gradePayHigher = $('#gradePayHigher').val().trim();
            var premiumAmount = $('#premiumAmount').val().trim();
            var cityGroup = $('#cityGroup').val().trim();
            
            var errors = [];

            var payCommissionMandatoryCodes = ["11", "2", "6", "12", "10", "208", "135", "18", "19", "8", "207", "4"];
            if (payCommissionMandatoryCodes.includes(departmentAllowdeducCode) && payCommissionCode == "0") {
                errors.push('Pay Commission is required for the selected Type Of Component.');
            }

            var mandatoryPercentageAllowDeducCode = ["4", "18", "11", "12", "19", "6", "8", "207", "10", "17", "208"];
            if (mandatoryPercentageAllowDeducCode.includes(departmentAllowdeducCode) && !percentage) {
                errors.push('Percentage is required for the selected Type Of Component.');
            }

            var amountMandatoryCodes = ["2", "17", "134", "37", "10", "208", "14"];
            if (amountMandatoryCodes.includes(departmentAllowdeducCode) && !amount) {
                errors.push('Amount is required for the selected Type Of Component.');
            }

            var maxBasicMandatoryCodes = ["208", "2"];
            if (maxBasicMandatoryCodes.includes(departmentAllowdeducCode) && !maxBasic) {
                errors.push('Maximum Basic is required for the selected Type Of Component.');
            }

            var minBasicMandatoryCodes = ["17", "208", "2"];
            if (minBasicMandatoryCodes.includes(departmentAllowdeducCode) && !minBasic) {
                errors.push('Minimum Basic is required for the selected Type Of Component.');
            }

            var startDateMandatoryCodes = ["10", "208", "18", "19", "11", "17", "8", "6", "134", "12", "207"];
            if (startDateMandatoryCodes.includes(departmentAllowdeducCode) && !startDate) {
                errors.push('Start Date is required for the selected Type Of Component.');
            }

            var cityGroupMandatoryCodes = ["208", "2", "134", "37"];
            if (cityGroupMandatoryCodes.includes(departmentAllowdeducCode) && !cityGroup) {
                errors.push('City Group is required for the selected Type Of Component.');
            }

            if (startDate && endDate && new Date(startDate) > new Date(endDate)) {
                errors.push('End Date should be after Start Date.');
            }

            if (departmentAllowdeducCode == "17" && !cityClass) {
                errors.push('City Class is required for the selected Type Of Component.');
            }

            if (departmentAllowdeducCode == "10" && (!gradePayLower || !gradePayHigher)) {
                errors.push('Grade Pay/Pay Scale is required for the selected Type Of Component.');
            }

            if (errors.length > 0) {
            	 event.preventDefault(); 
            	swal({
            	    title: 'Validation Error',
            	    text: errors.join('\n'),  
            	    icon: 'error',
            	    button: 'Ok',
            	});
            } else {
            	
            	swal({
            		 title: 'Success',
                     icon: 'success',
                      text: 'Form submitted successfully!',
            		  buttons: true,
            		  dangerMode: true,
            		})
            		.then((willDelete) => {
            		  if (willDelete && parseInt(departmentAllowdeducCode)>0) {
            			  $(this).off('submit').submit();
            		  }else{
            			  event.preventDefault(); 
            		  }
            		});	
            	
      
            }
        });

    
   
    
});

	
	 
   
  
  
