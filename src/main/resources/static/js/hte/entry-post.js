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



$("#btnFilter").keyup(function(){
	 var asstDdo=$("#cmbAsstDDO").val();
	 var context = $("#appRootPath").val();
	if(asstDddo!='' && asstDddo!="0"){
		 // $("#loaderMainNew").show();
			$.ajax({
				type : "GET",
			    url: context+"/mdc/findDesignation",
				async : true,
				   data: { asstDdo: asstDdo },
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
						for (var i = 0; i < data.length; i++) {
						
						}
						if(data.length==0){
							swal("Please enter valid post");
						}
				}
			});
	}
 });
