$(document).ready(function(){
	
	var varMessage = $("#message").val();
	if (varMessage != "" && varMessage != undefined) {
		swal('' + varMessage, {
			icon : "success",
		});
	}
	
	/* $(".attchDeattachTable").datatable(); */
	 $('.attchDeattachTable').DataTable();
	var selecteditems  = [];
	
	
	/*
	 * $("#customFields").on('click', '.btnSelect', function() { // get the
	 * current row var currentRow = $(this).closest("tr"); var col1 =
	 * currentRow.find("td:eq(1)").text(); // get current row 2nd table cell TD
	 * value var col2 = currentRow.find("td:eq(2)").text(); var col3 =
	 * currentRow.find("td:eq(3)").text(); var col4 =
	 * currentRow.find("td:eq(4)").text(); var data = col1 + "\n";
	 * 
	 * if($(this).is(":checked")){ selecteditems.push(col1); // alert("Checkbox
	 * is checked."); } else if($(this).is(":not(:checked)")){
	 * selecteditems.pop(col1); //alert("Checkbox is unchecked."); }
	 * 
	 * });
	 * 
	 * console.log(selecteditems);
	 * 
	 */
     
     $("#btnSave").click(function(e){
    	
   	 	 var input = $('#schemebillGroupId').val();
   	 	 var sevaarthId = $('#sevaarthId').val(); 
   	 	// alert("sevaarthId ="+sevaarthId);
   	 	var checkPaybillInProcess=isPaybillIsInProcessForAttach(sevaarthId);
		
		if(checkPaybillInProcess=="0"){
    		 $.ajax({
			      type: "GET",
			      url: "../level1/attachEmployee/"+sevaarthId+"/"+input,
			      async: true,
			      contentType:'application/json',
			      error: function(data){
			    	  console.log(data);
			    	  alert(data);
			      },
			      success: function(data){
			    	 location.reload(true);
			    	 swal("Employee Attach Successfuly");
			    }
			 });	
		}
		else{
			swal("Paybill is in process !!!");
			$("#btnSave").prop("disabled",true);
			 e.preventDefault();
		}
    	  });
   
  
     $(".delete").click(function(){
    	   var sevaarthId=$(this).attr('value');
    	   // alert("hi" + sevaarthId);
    	   var checkPaybillInProcess=isPaybillIsInProcessForAttach(sevaarthId);
      		
      		if(checkPaybillInProcess=="0"){
    	  // alert("hi" + sevaarthId);
    	   swal({
    		   title: "Are you sure?",
    		   text: " to Dettach Employee!",
    		   icon: "warning",
    		   buttons: true,
    		   dangerMode: true,
    		 })
    		 .then((willDelete) => {
    		   if (willDelete) {
    			   
    			   
    			     $.ajax({
    				      type: "GET",
    				      url: "../level1/dettachBillGroupId/"+sevaarthId,
    				      async: true,
    				      error: function(data){
    				    	  console.log(data);
    				    	  alert("eror is"+data);
    				      },
    				      success: function(data){
    				    	   swal("Dettach Successfully....!", {
    				    	       icon: "success",
    				    	     });
    				    	   setTimeout(function() {
    							    location.reload(true);
    							}, 3000);
    				      }
    				 });
    		   }
    		 });
      		}else{
    			swal("Paybill is in process !!!");
    			$("#btnSave").hide();
    			
    		}
    	   
    	  });
     

 });

function isPaybillIsInProcessForAttach(sevaarthId) {
	var len=0;
	$.ajax({
		type : "GET",
		url : "../level1/isPaybillIsInProcessForAttach/" + sevaarthId,
		async : false,
		contentType:'application/json',
		error : function(data) {
			console.log(data);
			alert(data);
		},
		success : function(data) {
			console.log(data);
			len=data.length;
		}
	});
	return len;
}

function findAttachDettachEmp(){
	var billgroupid = document.getElementById("schemebillGroupId");
	if (billgroupid.value === "0") {
		swal('Please select Bill Description');
		return false;
	}
	document.getElementById("status").value="SEARCH";
	$("#myForm").submit();
}

function validateBeforeAttach(){
	var chkBoxArr=document.getElementsByName('GroupCheck'); 
	var chkLength=chkBoxArr.length;

	for(var i=0;i<chkLength;i++){ 
		if(chkBoxArr[i].checked)
		{
			AddRowInEmpBGTable();
			return true; 
		}
	} 
	return false;
}
function AddRowInEmpBGTable()
{
	var color1 = "#F0F0F0";
	var color2 = "#7B68EE";
	
	var counterEmp = document.getElementById("counterEmp").value ;
	var counterEmpBG = document.getElementById("counterEmpBG").value ;
	var dcpsEmpIdsToBeAddedToBGTable = new Array() ;
	var dcpsEmpNamesToBeAddedToBGTable = new Array() ;
	var selectedEmpIds = new Array();
	var totalSelectedEmpIds=0;
	var newRow;
	var Cell1;
	var Cell2;
	var counter = 1 ;
	var tableEmpBG = document.getElementById("tableEmpBG");
	var tableEmp =document.getElementById("tableEmp");
	for(var i=1;i<=counterEmp;i++)
	{
		if(document.getElementById("GroupCheck"+i).checked)
		{
			dcpsEmpIdsToBeAddedToBGTable[counter] = document.getElementById("GroupCheck"+i).value ;
			dcpsEmpNamesToBeAddedToBGTable[counter] = document.getElementById("empName"+i).innerHTML ;
			totalSelectedEmpIds = totalSelectedEmpIds + 1 ; 
			selectedEmpIds[counter] = i ;
			counter++ ;
		}
	}

	
	for(i=1;i<=totalSelectedEmpIds;i++)
	{
		counterEmpBG = Number(counterEmpBG) + 1 ;
		newRow = tableEmpBG.insertRow(tableEmpBG.rows.length);
		newRow.style.backgroundColor = color1;
		newRow.style.borderColor = color2;
		
		Cell1 = newRow.insertCell(-1);
		Cell2 = newRow.insertCell(-1);
		Cell1.align="center";
	   	Cell2.align="left";
	   	
	   	Cell1.innerHTML = '<input type="checkbox" name="GroupCheckBG" id="GroupCheckBG'+counterEmpBG+'" value="'+dcpsEmpIdsToBeAddedToBGTable[i]+'" />' ;
	    Cell2.innerHTML = '<label id="empNameBG'+counterEmpBG+'"><b>'+dcpsEmpNamesToBeAddedToBGTable[i]+'</b></label>' ;

	    document.getElementById("counterEmpBG").value = Number(document.getElementById("counterEmpBG").value) + 1 ;
	    document.getElementById("dcpsEmpIdstoBeAttached").value = document.getElementById("dcpsEmpIdstoBeAttached").value +  dcpsEmpIdsToBeAddedToBGTable[i] + "~" ;

	    document.getElementById("dcpsEmpIdstoBeDetached").value = document.getElementById("dcpsEmpIdstoBeDetached").value.replace(dcpsEmpIdsToBeAddedToBGTable[i] + "~","") ;
	    
	}
	

	for(i=counterEmp;i>=1;i--)
	{
		if(document.getElementById("GroupCheck"+i).checked)
		{
			tableEmp.rows[i].style.display = 'none' ;
			document.getElementById("GroupCheck"+i).checked = false ;
		}
	}
}
function validateBeforeDetach(){
	var chkBoxArr=document.getElementsByName('GroupCheckBG'); 
	var chkLength=chkBoxArr.length;

	for(var i=0;i<chkLength;i++){ 
		if(chkBoxArr[i].checked)
		{
			AddRowInEmpTable();
			return true; 
		}
	} 
	return false;	
}
function AddRowInEmpTable()
{

	var color1 = "#F0F0F0";
	var color2 = "#7B68EE";
	
	var counterEmp = document.getElementById("counterEmp").value ;
	var counterEmpBG = document.getElementById("counterEmpBG").value ;
	
	var dcpsEmpIdsToBeAddedToTable = new Array() ;
	var dcpsEmpNamesToBeAddedToTable = new Array() ;
	var selectedEmpIds = new Array();
	var totalSelectedEmpIds=0;
	var newRow;
	var Cell1;
	var Cell2;
	var counter = 1 ;
	var tableEmpBG = document.getElementById("tableEmpBG");
	var tableEmp = document.getElementById("tableEmp");
	
	for(var i=1;i<=counterEmpBG;i++)
	{
		if(document.getElementById("GroupCheckBG"+i).checked)
		{
			dcpsEmpIdsToBeAddedToTable[counter] = document.getElementById("GroupCheckBG"+i).value ;
			dcpsEmpNamesToBeAddedToTable[counter] = document.getElementById("empNameBG"+i).innerHTML ;
			totalSelectedEmpIds = totalSelectedEmpIds + 1 ; 
			selectedEmpIds[counter] = i ;
			counter++ ;
		}
	}

	for(i=1;i<=totalSelectedEmpIds;i++)
	{
		counterEmp = Number(counterEmp) + 1 ;
		newRow = tableEmp.insertRow(tableEmp.rows.length);
		newRow.style.backgroundColor = color1;
		newRow.style.borderColor = color2;
		
		Cell1 = newRow.insertCell(-1);
		Cell2 = newRow.insertCell(-1);
		Cell1.align="center";
	   	Cell2.align="left";
	   	
	   	Cell1.innerHTML = '<input type="checkbox" name="GroupCheck" id="GroupCheck'+counterEmp+'" value="'+dcpsEmpIdsToBeAddedToTable[i]+'" />' ;
	    Cell2.innerHTML = '<label id="empName'+counterEmp+'"><b>'+dcpsEmpNamesToBeAddedToTable[i]+'</b></label>' ;

	    document.getElementById("counterEmp").value = Number(document.getElementById("counterEmp").value) + 1 ;
	    document.getElementById("dcpsEmpIdstoBeDetached").value = document.getElementById("dcpsEmpIdstoBeDetached").value +  dcpsEmpIdsToBeAddedToTable[i] + "~" ;

	    document.getElementById("dcpsEmpIdstoBeAttached").value = document.getElementById("dcpsEmpIdstoBeAttached").value.replace(dcpsEmpIdsToBeAddedToTable[i] + "~","") ;
	}

	for(i=counterEmpBG;i>=1;i--)
	{
		if(document.getElementById("GroupCheckBG"+i).checked)
		{
			tableEmpBG.rows[i].style.display = 'none' ;
			document.getElementById("GroupCheckBG"+i).checked = false ;
		}
	}

}


function checkUncheckAll(theElement)
{
	var theForm = theElement.form, z = 0;	
	 for(z=0; z<theForm.length;z++)
	 {		 
	      if(theForm[z].type == 'checkbox' && theForm[z].name == 'GroupCheck' )
		  {
			  theForm[z].checked = theElement.checked;
		  }
     }   
}	
function checkUncheckAllBG(theElement)
{
	var theForm = theElement.form, z = 0;	
	 for(z=0; z<theForm.length;z++)
	 {		 
	      if(theForm[z].type == 'checkbox' && theForm[z].name == 'GroupCheckBG')
		  {
			  theForm[z].checked = theElement.checked;
		  }
     }   
}


function AttachAndDetachEmp()
{

	document.getElementById("status").value="SAVE";
	$("#myForm").submit();
}



