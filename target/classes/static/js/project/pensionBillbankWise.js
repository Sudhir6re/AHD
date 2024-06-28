/*	$("#rptBtn_GenerateReport").click(function(){
		
	
		var billNumber= $("#billGroup").val();
		var yearName = $("#yearId").val();
		var monthName = $("#monthId").val();
		
		if((billNumber!=''|| billNumber==undefined)   
				&& (yearName!='0'|| yearName==undefined)  && (monthName!='0'|| monthName==undefined)){
			var flag=checkIsPaybillInProcess(billNumber,monthName,yearName);
			
			if(parseInt(flag)==0){
				e.preventDefault();
				swal.fire("Paybill not Generated !!!!!");
				$("#rptBtn_GenerateReport").prop("disabled",false);
			}
			else{
				submitHandler: function(form) {
				      form.submit();
				    }
				}
		}
		
	});
	*/


	function checkIsPaybillInProcess(monthName,yearName) ///CheckPaybill/{billNumber}/{monthName}/{yearName}
	{
		flag=0;
		 $.ajax({
		      type: "GET",
		      url: "../master/CheckPaybillPension/"+monthName +"/"+yearName,
		      async: false,
		      dataType : 'json',
		    // contentType:'application/json',
		      error: function(data){
		    	  console.log(data);
		    alert(data);
		      },
		      success: function(data){
		    	  console.log("response from server"+data);
		    	  if(parseInt(data) >0 ) {
		    		  flag=1;
		    	  }
		    	  else
		    		  {
		    		 flag=0;
		    		  }
		    }
		 });
		 return flag;
	}
	
	function checktheEntryForForm2Regularpansion(monthName,yearName) ///CheckPaybill/{billNumber}/{monthName}/{yearName}
	{
		flag=0;
		 $.ajax({
		      type: "GET",
		      url: "../master/checktheEntryForForm2Regularpansion/"+monthName +"/"+yearName,
		      async: false,
		      dataType : 'json',
		    // contentType:'application/json',
		      error: function(data){
		    	  console.log(data);
		    alert(data);
		      },
		      success: function(data){
		    	  console.log("response from server"+data);
		    	  if(parseInt(data) >0 ) {
		    		  flag=1;
		    	  }
		    	  else
		    		  {
		    		 flag=0;
		    		  }
		    }
		 });
		 return flag;
	}
	
	$("#GenerateReport").click(function(e){
    	
 	 	 var month = $('#monthId').val();
 	 	 var year = $('#yearId').val(); 
 	 	 
 	 	removeErrorClass($('#monthId'));
 	 	removeErrorClass($('#yearId'));
// 	 	removeErrorClass($('#billGroup'));
 	 	 //alert("month and year  ="+month+''+year);
 	 	 
 	 	 if(month=="0"){
 	 		addErrorClass($('#monthId'),"Please select Month !!!");
 	 		e.preventDefault();
 	 	 }
 	 	 
 	 	 
 	 	 if(year=="0"){
 	 		addErrorClass($('#yearId'),"Please select Year !!!");
 	 		e.preventDefault();
 	 	 }
 	 	 
 	 	 
 	 	 
 	 	
 	 	 
 	 	 if(month!="0" && year!="0" ){
 	 		var ispaybillinProcess = checkIsPaybillInProcess(month,year);
 	 		if(ispaybillinProcess == 0)
 	 			{
 	 			swal.fire("Paybill not generated!");
 	 			e.preventDefault();
 	 			}else
 	 				{
 	 				var isdatapresent = checktheEntryForForm2Regularpansion(month,year);
 	 	 	 		if(isdatapresent == 0)
 	 	 	 			{
 	 	 	 			swal.fire("Record not found!");
 	 	 	 			e.preventDefault();
 	 	 	 			}
 	 				}
 	 	 }
	});
 	 	