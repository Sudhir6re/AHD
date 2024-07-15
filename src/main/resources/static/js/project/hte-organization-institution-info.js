$("#cmbBankName").change(function() 
		 {
		 	  var bankId = $("#cmbBankName").val();
		 //alert("bank  is "+bankId);
		     	 if (bankId != '') 
		     	 {
		     		 $.ajax({
		 			      type: "GET",
		 			      url: "mstBank/"+bankId,
		 			      async: true,
		 			      contentType:'application/json',
		 			      error: function(data){
		 			    	 //console.log(data);
		 			      },
		 			      success: function(data){
		 			    	 //console.log(data);
		 			    	  //alert(data);
		 			    	 var len=data.length;
		 			    	  if(len!=0)
		 			    		  {
		 			    		//console.log(data);
		 			    		 $('#cmbBranchName').empty();
		 			    		$('#cmbBranchName').append("<option value='0'>Please Select</option>");
		 				    	 var temp = data;
		 				   		  $.each( temp, function( index, value ){
		 					    		console.log( value[3] ); 
		 					    		 $('#cmbBranchName').append("<option  data-id="+value[6]+" value="+value[0]+">" + value[3] + "</option>");
		 				    		});
		 				   		  
		 				   		  
		 			    		  }
		 			    	  else
		 			    		  {
		 			    		 $('#cmbBranchName').empty();
		 			    		 $('#cmbBranchName').append("<option value='0'>Please Select</option>");
		 			    		//  swal("Record not found !!!");
		 			    		  }
		 			    	}
		 			 });	
		     	 }
		     	 
		     	
		     		
		 });


$("#cmbBranchName").change(function() 
		 {
		 	  var branchId = $("#cmbBranchName").val();
		 //alert("bank  is "+bankId);
		     	 if (bankId != '') 
		     	 {
		     		 $.ajax({
		 			      type: "GET",
		 			      url: "getIfscCodeByBranchId/"+branchId,
		 			      async: true,
		 			      contentType:'application/json',
		 			      error: function(data){
		 			    	 //console.log(data);
		 			      },
		 			      success: function(data){
		 			    	 //console.log(data);
		 			    	  //alert(data);
		 			    	 var len=data.length;
		 			    	  if(len!=0)
		 			    		  {
		 			    		//console.log(data);
		 			    		 $('#txtIFSCCode').empty();
		 			    		$('#txtIFSCCode').append("<option value='0'>Please Select</option>");
		 				    	 var temp = data;
		 				   		  $.each( temp, function( index, value ){
		 					    		console.log( value[3] ); 
		 					    		 $('#txtIFSCCode').append("<option  data-id="+value[6]+" value="+value[0]+">" + value[3] + "</option>");
		 				    		});
		 				   		  
		 				   		  
		 			    		  }
		 			    	  else
		 			    		  {
		 			    		 $('#txtIFSCCode').empty();
		 			    		 $('#txtIFSCCode').append("<option value='0'>Please Select</option>");
		 			    		//  swal("Record not found !!!");
		 			    		  }
		 			    	}
		 			 });	
		     	 }
		     	 
		     	
		     		
		 });