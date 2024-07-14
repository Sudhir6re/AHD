$(document).ready(function() {
	var contextPath = $("#appRootPath").val();
	
	var dataTable= $("#postDetails").dataTable();


$("#orderCmb").change(function(){
	var context = $("#appRootPath").val();
	var grOrderId = $("#orderCmb").val();
	$.ajax({
	      type: "POST",
	      url: context+"/ddo/findGrOrderByGrOrderId/"+grOrderId,
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
	    		 if(len>0){
	    			 
	    			  var orderDate = new Date(data[0].orderDate); // Convert string to Date object
	    			    var formattedDate = orderDate.toISOString().split('T')[0]; // Convert Date to yyyy-MM-dd format

	    			    $("#OrderDate").val(formattedDate);
	    			    
	    			 //$( "#OrderDate").val(new Date(data[0].orderDate));
	    		 }
				}
	 });
});



$("#btnFilter").click(function(){
	 var ddoCode1=$("#cmbAsstDDO").val();
	 var Dsgn=$("#designationCmb").val();
	 var BillNo=$("#billCmb").val();
	 var context = $("#appRootPath").val();
	 
	 
	if(ddoCode1!='' && ddoCode1!="0"){
		 // $("#loaderMainNew").show();
			$.ajax({
				type : "GET",
			    url: context+"/ddo/searchPostDetails",
				async : true,
				   data: { ddoCode1: ddoCode1 ,BillNo:BillNo,Dsgn:Dsgn,lPostName:""},
				contentType : 'application/json',
				error : function(data) {
					 console.log(data);
					 $("#loaderMainNew").hide();
				},
				success : function(data) {
					 console.log(data);
					 $("#loaderMainNew").hide();
					var len = data.length;
						$("#loaderMainNew").hide();
						j=1;
						if(len>0){
							for (var i = 0; i < data.length; i++) {
								 dataTable.fnClearTable();
								 dataTable.fnAddData([j,data[i].empFullName,data[i].postname,data[i].postType,data[i].dsgnname,data[i].billNo]);
								 j++;
							}
						}
						if(data.length==0){
							swal("No data found");
						}
				}
			});
	}
 });


$("#Search").click(function(){
	 var ddoCode1=$("#cmbAsstDDO").val();
	 var Dsgn=$("#designationCmb").val();
	 var BillNo=$("#billCmb").val();
	 var context = $("#appRootPath").val();
	 
	 
	if(Dsgn!='-1' && BillNo!="-1"){
		 // $("#loaderMainNew").show();
			$.ajax({
				type : "GET",
			    url: context+"/ddo/searchPostDetails",
				async : true,
				   data: { ddoCode1: ddoCode1 ,BillNo:BillNo,Dsgn:Dsgn,lPostName:""},
				contentType : 'application/json',
				error : function(data) {
					 console.log(data);
					 $("#loaderMainNew").hide();
				},
				success : function(data) {
					 console.log(data);
					 $("#loaderMainNew").hide();
					var len = data.length;
						$("#loaderMainNew").hide();
						j=1;
						if(len>0){
							for (var i = 0; i < data.length; i++) {
								 dataTable.fnClearTable();
								 dataTable.fnAddData([j,data[i].empFullName,data[i].postname,data[i].postType,data[i].dsgnname,data[i].billNo]);
								 j++;
							}
						}
						if(data.length==0){
							swal("No data found");
						}
				}
			});
	}
});



$("form[name='postEntryModel']").validate({
    rules: {
       /* cmbTaluka: {
            required: true
        },*/
        ddoCode: {
            required: true,
            min:1,
        },
        postTypeCmbBox: {
            required: true,
            min:1,
        },
        cmbSubFieldDept: {
            required: true,
            min:1,
        },
        purposeCmbBox: {
            required: true,
            min:1,
        },
       /* tempPostTypeCmbBox: {
            required: true,
            min:1,
        },*/
        designationCmb: {
            required: true,
            min:1,
        },
        subjectCmb: {
            required: true,
            //minlength:3,
        },
        orderCmb: {
            required: true,
            min:1,
        },
        officeCmb: {
            required: true,
            min:1,
        },
       startDate: {
            required: true,
        },
        endDate: {
            required: true,
        },
         postNumber: {
            required: true,
            digits: true ,
            min:1,
        }
    },
    messages: {
        cmbTaluka: {
            required: "Please select Taluka"
        },
        ddoCodeforFilter: {
            required: "Please enter DDO Code/Office Name"
        },
        postTypeCmbBox: {
            required: "Please select Type Of Post"
        },
        cmbSubFieldDept: {
            required: "Please select Sub Field Department"
        },
        purposeCmbBox: {
            required: "Please select Purpose"
        },
        tempPostTypeCmbBox: {
            required: "Please select Type of Temporary Post"
        },
        designationCmb: {
            required: "Please select Designation"
        },
        subjectCmb: {
            required: "Please select Subject"
        },
        orderCmb: {
            required: "Please select GR No"
        },
        officeCmb: {
            required: "Please select Office"
        },
        startDate: {
            required: "Please enter Sanctioned From Date"
        },
        endDate: {
            required: "Please enter Sanctioned To Date"
        },
        postNumber: {
            required: "Please enter Number of Post",
            digits: "Please enter only digits"
        }
    },
    submitHandler: function(form) {
        form.submit(); 
    }
});


});





   

