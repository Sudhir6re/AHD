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

