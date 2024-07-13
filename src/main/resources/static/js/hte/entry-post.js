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


});
