
$(document).ready(function() {
	var contextPath = $("#appRootPath").val();
	
	// $("#tblShowPayBill").dataTable().fnClearTable();
	
	
	
	if($("#uniqueId").val()!='' && $("#uniqueId").val()!=null  && $("#uniqueId").val()!=undefined){
	   alert("Unique Institude id Generated keep it for future Use "+$("#uniqueId").val());
    }
	

	var dataTable= $("#ddoMapTable").dataTable();
$("#cmbDistrict").change(function(){
	var context = $("#appRootPath").val();
	var districtId = $("#cmbDistrict").val();
	
	$.ajax({
	      type: "POST",
	      url: context+"/mdc/getAllTalukaByDistrictId/"+districtId,
	      async: false,
	      contentType:'application/json',
	      error: function(data){
	    	  console.log(data);
	      },
		  	beforeSend : function(){
				$( "#loaderMainNew").show();
				},
			complete : function(data){
				$( "#loaderMainNew").hide();
			},	
	      success: function(data){
	    		 var len = data.length;
					if (len != 0) {
							   for(var i=0;i<len;i++){
								   $('#cmbTaluka').append('<option value="' + data[i].talukaId + '">' + data[i].talukaName + '</option>');
				                }		
				}
					
	     }
	 });
});


$("#btnFilter").change(function(){
	var context = $("#appRootPath").val();
	var districtId = $("#cmbDistrict").val();
	var talukaId = $("#cmbTaluka").val();
	var cmbAdminType = $("#cmbAdminType").val();
	$.ajax({
	      type: "POST",
	      url: context+"/mdc/findZpRltDtls",
        contentType: 'application/json',
        data: JSON.stringify({ districtId: districtId, talukaId: talukaId,cmbAdminType:cmbAdminType }),
        dataType: 'json',
	      async: false,
	      contentType:'application/json',
	      error: function(data){
	    	  console.log(data);
	      },
		  	beforeSend : function(){
				$( "#loaderMainNew").show();
				},
			complete : function(data){
				$( "#loaderMainNew").hide();
			},	
	      success: function(data){
	    		 var len = data.length;
					if (len != 0) {
						populateTable(data);	
				}
					
	     }
	 });
});




function populateTable(data) {

    // Iterate over received data and populate table rows
    $.each(data, function(index, row) {
    
    	dataTable.fnClearTable();

    	
    	   var row1 = '<span id="' + row[0] + '"><a href="#" onclick="popupDtls(\'' + row[0] + '\', \'' + index + '\');" >' + row[0] + '</a></span>';
           var row2 = '<span id="' + row[1] + '"><a href="#" onclick="popupDtls(\'' + row[1] + '\', \'' + index + '\');">' + row[1] + '</a></span>';
           
    	var row3=null;
    	
    	   if(row[5]==0){
    		   row3= '<span class="bg bg-warning" >Pending</span>';
    	   }else if(row[5]==1){
    		   row3= '<span  class="bg bg-succes" >Pending</span>';
    	   }else{
    		   row3= '<span  class="bg bg-danger >Pending</span>';
    	   }
    	
        
        dataTable.fnAddData(
				[
					row1,
						change1,
						row2
						]);
        
        
    });
}

$("#txtDDODsgn").keyup(function(){
	 var txtDDODsgn=$("#txtDDODsgn").val();
	 
	 var context = $("#appRootPath").val();
	if(txtDDODsgn!='' && txtDDODsgn!="0"){
		 // $("#loaderMainNew").show();
			$.ajax({
				type : "GET",
			    url: context+"/mdc/findDesignation",
				async : true,
				   data: { txtDDODsgn: txtDDODsgn },
				contentType : 'application/json',
				error : function(data) {
					 console.log(data);
				// alert("error");
					 $("#loaderMainNew").hide();
				},
				success : function(data) {
					 console.log(data);
					 $("#loaderMainNew").hide();
					var len = data.length;
				
					empFound=data.length;
					
					console.log(data);
						$("#loaderMainNew").hide();
						document
								.getElementById("searchDiv").innerHTML = "";
						for (var i = 0; i < data.length; i++) {
							
							$("#searchDiv").show();
							$("#searchDiv")
									.append(
											"<p class='empdata' empid='"+data[i].dsgnName+"' empname='"+data[i].dsgnName+"' empsevaathid='"+data[i].dsgnName+"'   empdesgn='"+data[i].dsgnName+"'>"
													+ data[i].dsgnName
													+ "-"
													+ data[i].dsgnName
													+ "</p>");
							
						//	$("#sevaarthIdCopy").val(data[i].sevaarthId);
							
							$("#searchDiv")
									.css(
											"border:1px solid #A5ACB2;");
						}
						
						if(data.length==0){
							swal("Please enter valid sevaarth id");
						}
					 //end succcess
				}
			});
	}else if(classOfPension=="0"){
		 swal("Please select Class of pension !!!", {
   	      icon: "warning",
   	  });
	}
 });



$('body').on('click', '.empdata', function() {
	 $("#procceed").attr("disabled", false); 
	var sevaarthId=$(this).attr("empsevaathid");  
	$("#txtDDODsgn").val(sevaarthId);
	$("#searchDiv").hide();
});









var empFound=0;

$("#txtDDODsgn").blur(function(){
	setTimeout(function () {
	$("#searchDiv").hide();
}, 200);
	 
});



$("#txtRepDDOCode").blur(function(){
	var context = $("#appRootPath").val();
	var ddoCode=$("#txtRepDDOCode").val();
    $.ajax({
    	url : context+"/mdc/getddoInfo",
        type: 'GET',
        data: { ddoCode: ddoCode },
    	async : true,
		contentType : 'application/json',
        success: function(response) {
        	if(response!=''){
        		 var dropdown = $('#cmbSubTreasury');
                 dropdown.empty();
                 dropdown.append($('<option  value="-1"></option>').text("Please Select")); // Adjust the value index as needed
                 $.each(response.trasuryDetails, function(index, value) {
                    $("#txtTreasuryName").val(value[1]);
                    $("#txtTreasuryCode").val(value[0]);
                    dropdown.append($('<option  value="'+value[0]+'"></option>').text(value[1])); // Adjust the value index as needed
                    
                 });
                 
                 
                 $.each(response.subTreasuryList, function(index, value) {
                     dropdown.append($('<option value="'+value[0]+'"></option>').text(value[1])); // Adjust the value index as needed
                 });
        	}else{
        		alert("No Data Found");
        	}
        },
        error: function(error) {
           alert('Error fetching DDO data:', error);
        }
    });
});



$("#cmbSubTreasury").change(function(){
	var cmbSubTreasury=$("#cmbSubTreasury").val();
	var cmbAdminOffice=$("#cmbAdminOffice").val();

$.ajax({
	type : "GET",
	url : contextPath+"/mdc/generateDDOCode/" + cmbSubTreasury+"/"+cmbAdminOffice,
	async : true,
	contentType : 'application/json',
	error : function(data) {
		 console.log(data);
	},
	success : function(response) {
		 console.log(response);
		 var ddoCode = response.ddoCode;
		 if(ddoCode!=''){
			 $("#txtDDOCode").val(ddoCode);
		 }else{
			 $("#txtDDOCode").val("");
		 }
	}
});
});






$("#cmbAdminOffice").change(function(){
	var context = $("#appRootPath").val();
	var ofcId = $("#cmbAdminOffice").val();
	
	$.ajax({
	      type: "POST",
	      url: context+"/mdc/fetchDistrictOfcByOffcId/"+ofcId,
	      async: false,
	      contentType:'application/json',
	      error: function(data){
	    	  console.log(data);
	      },
		  	beforeSend : function(){
				$( "#loaderMainNew").show();
				},
			complete : function(data){
				$( "#loaderMainNew").hide();
			},	
	      success: function(data){
	    	  $('#cmbDistrict').empty();
	    		 var len = data.length;
					if (len != 0) {
						 $.each(data, function(index, row) {
								  // $('#cmbDistrict').append('<option value="' + data[i].talukaId + '">' + data[i].talukaName + '</option>');
								   $('#cmbDistrict').append('<option value="' + row[1]+ '">' + row[0] + '</option>');
						 });	
				}
					
	     }
	 });
});




$('body').on('click', '.hideDdoCode', function() {
	var field=$(this).attr("data-ddocode");  
	var srno=$(this).attr("data-srno");  
	 hideDtls(field, srno);
});




$('body').on('click', '.ddoCode', function() {
	var field=$(this).attr("data-ddocode");  
	var srno=$(this).attr("data-srno");  
	
	var ddoCode=field;

	$.ajax({
		type : "GET",
		url : contextPath+"/mdc/fetchDdoDetails/" + ddoCode,
		async : true,
		contentType : 'application/json',
		error : function(data) {
			// console.log(data);
		},
		success : function(data) {
			// console.log(data);
			// alert(data);
			var len = data.length;
			if (len != 0) {

				var temp = data;
				$
						.each(
								temp,
								function(index,
										value) {
									console
											.log(value[1]);
									
									setDDOdtls(value[0]+","+value[1], field, srno);
								
																							});
			} else {
			
				swal("Record not found !!!");
			}
		}
	});
});

function setDDOdtls(myAjax, field, srno) {

	var divId = srno.toString() + field.toString();
	
	document.getElementById(divId).innerHTML = '<a href="#"  class="hideDdoCode"  onclick="hideDtls('
			+ field
			+ ','
			+ srno
			+ ')">'
			+ field
			+ '<br>'
			+ myAjax + '</a>';

}

function hideDtls(field, srno) {

	var divId = srno.toString() + field.toString();
	document.getElementById(divId).innerHTML = '<a href="#"   class="ddoCode"    onclick="popupDtls('
			+ field + ',' + srno + ');">' + field + '</a>';
}

    $("form[name='ZpDDOOffice']").validate({
        // Specify validation rules for each input field
        rules: {
            cmbAdminOffice: {
                required: true,
              //  min: 1
            },
            cmbDistOffice: {
                required: true,
              //  min: 1
            },
            radioFinalLevel: "required",
            txtRepDDOCode: {
                required: true,
                //maxlength: 10
            },
            txtFinalDDOCode: {
                required: true,
                //maxlength: 10
            },
            txtSpecialDDOCode: {
                required: false,
                //maxlength: 10
            },
            radioSalutation: "required",
            txtDDOName: {
                required: true,
               // maxlength: 100
            },
            radioGender: "required",
            txtTreasuryName: {
                maxlength: 50
            },
            txtTreasuryCode: {
                maxlength: 4
            },
            cmbSubTreasury: {
                required: true,
                min: 1
            },
            txtDDODsgn: {
                required: true,
                maxlength: 50
            },
            txtOfficeName: {
                required: true,
                maxlength: 500
            },
            txtDDOCode: {
                required: true,
                maxlength: 50
            },
            txtMobileNo: {
                required: true,
                maxlength: 10
            },
            txtEmailId: {
                required: true,
                maxlength: 100,
                email: true // Ensure email format validation
            }
            // Add more fields here as needed
        },
        // Specify validation error messages
        messages: {
            cmbAdminOffice: {
                required: "Please select Admin Office",
                min: "Please select Admin Office"
            },
            cmbDistOffice: {
                required: "Please select District Office",
                min: "Please select District Office"
            },
            radioFinalLevel: "Please select Final Level",
            txtRepDDOCode: {
                required: "Please enter DDO Code Level 2",
                maxlength: "DDO Code Level 2 should not exceed {0} characters"
            },
            txtFinalDDOCode: {
                required: "Please enter DDO Code Level 3",
                maxlength: "DDO Code Level 3 should not exceed {0} characters"
            },
            txtSpecialDDOCode: {
                required: "Please enter DDO Code Level 4",
                maxlength: "DDO Code Level 4 should not exceed {0} characters"
            },
            radioSalutation: "Please select DDO Name Salutation",
            txtDDOName: {
                required: "Please enter DDO Name",
                maxlength: "DDO Name should not exceed {0} characters"
            },
            radioGender: "Please select Gender",
            txtTreasuryName: {
                maxlength: "Treasury Name should not exceed {0} characters"
            },
            txtTreasuryCode: {
                maxlength: "Treasury Code should not exceed {0} characters"
            },
            cmbSubTreasury: {
                required: "Please select Sub Treasury Name",
                min: "Please select Sub Treasury Name"
            },
            txtDDODsgn: {
                required: "Please enter DDO Designation",
                maxlength: "DDO Designation should not exceed {0} characters"
            },
            txtOfficeName: {
                required: "Please enter Institute Name",
                maxlength: "Institute Name should not exceed {0} characters"
            },
            txtDDOCode: {
                required: "Please enter DDO Code",
                maxlength: "DDO Code should not exceed {0} characters"
            },
            txtMobileNo: {
                required: "Please enter Mobile Number",
                maxlength: "Mobile Number should not exceed {0} characters"
            },
            txtEmailId: {
                required: "Please enter Email Id",
                maxlength: "Email Id should not exceed {0} characters",
                email: "Please enter a valid Email Id"
            }
            // Add more messages here as needed
        },
        // Make sure the form is submitted to the destination defined
        // in the "action" attribute of the form when valid
        submitHandler: function(form) {
            form.submit();
        }
    });
});
