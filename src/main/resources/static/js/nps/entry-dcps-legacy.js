//$("#displayData").hide();

var contextPath = "";

jQuery(document).ready(function($) {
	var varMessage = $("#message").val();
	if (varMessage != "" && varMessage != undefined) {
		swal('' + varMessage, {
			icon : "success",
		});
	}
	contextPath = $("#appRootPath").val();
	if(!contextPath){
		contextPath=window.origin;
	}
});


function getEmp() {
		$("#action").val("search");
		$("#dcpsLegacyEntryForm").submit();
	//$("#displayData").fadeIn(200);
}


$("#btnSave").click(function(e){
	e.preventDefault();
	$("#dcpsLegacyEntryForm").attr('action',$("#appRootPath").val()+'/ddo/saveDcpsLegacyData');
	if(validatePeriod()==true ){
		swal("Data Exist for given period");
	}else{
		$("#dcpsLegacyEntryForm").submit();
	}
});



$("#period0").change(function(e){
	if(validatePeriod()==true ){
		swal("Data Exist for given period");
	}else{
		$("#dcpsLegacyEntryForm").submit();
	}
});



$("#contriStartDt0").blur(function(e){
	
});









function validatePeriod(){
	var flag=true;
	var sevaarthId=$("#sevaarthId0").val();
	var period=$("#period0").val();
	var params = {
		sevaarthId : sevaarthId,
		period : period
 	};
	
	$.ajax({
		type : "POST",
		url : contextPath + '/ddo/checkDataExistsForPeriod',
		data : JSON.stringify(params),
		contentType : 'application/json',
		error : function(xhr, status, error) {
			console.error('Error occurred:', error);
			$("#loaderMainNew").hide();
		},
		success : function(data) {
			$("#loaderMainNew").hide();
			console.log(data);
			if (parseInt(data)>0) {
				flag=true;
			}
		}
	});
	
	return flag;
}


function validatezeros() {
	var emp = parseInt(document.getElementById("empContriInt").value);
	var employer = parseInt(document
			.getElementById("employerContriInt").value);
	if (emp < 0.1 || employer < 0.1) {
		alert("Please Enter Correct value");
		document.getElementById("empContriInt").value = "";
		document.getElementById("employerContriInt").value = "";
	}
}





function validateAmount(){

	 var period = document.getElementById("period").value;
	 var contri = parseInt(document.getElementById("empContri").value);
	 var interest = parseInt(document.getElementById("empInterest").value);
		 if(contri>6000000 || interest>6000000 )
		 {
			 alert("Maximum acceptable value is 60 lakhs only");
			 document.getElementById("empContri").value="";
			 document.getElementById("employerContri").value="";
			 document.getElementById("empInterest").value="";
			 document.getElementById("employerInterest").value="";
			 document.getElementById("total").value ="";
			 	}
	 if(period==10001198212)
	 {
		 if(contri>6000000 || interest>6000000 )
		 {
			 alert("Maximum acceptable value is 60 lakhs only");
			 document.getElementById("empContri").value="";
			 document.getElementById("employerContri").value="";
			 document.getElementById("empInterest").value="";
			 document.getElementById("employerInterest").value="";
			 document.getElementById("total").value ="";
			 	}
	 }
	}



function validateInterest() {
		var contri = parseInt(document.getElementById("empContri").value);

		var interest = parseInt(document.getElementById("empInterest").value);
		
	if (interest > contri) {
			 alert("Interest should not be greater than contribution");
			document.getElementById("empContri").value = "";
			document.getElementById("employerContri").value = "";
			document.getElementById("empInterest").value = "";
			document.getElementById("employerInterest").value = "";
			document.getElementById("total").value = "";
		}  
	}

	
	function setEmployerContri() {

		var empContri = document.getElementById("empContri").value;
		var period1 = document.getElementById("period").value;
		if (period1 == '10001198277') {
			var contri = (Number(empContri * 40)/100).toFixed(2);
			var contriemplr = (Number(empContri) + Number(contri));
		document.getElementById("employerContri").value = contriemplr;
		}
		else if(period1 =='10001198258')
			{
			document.getElementById("employerContri").value = empContri;
			}
	}

	function setEmployerInt() {
		if(document.getElementById("empContri").value == '' || document.getElementById("empContri").value == null)
		{	
			document.getElementById("empInterest").value = '';
		}
		var empContri = document.getElementById("empInterest").value;
		document.getElementById("employerInterest").value = empContri;
	}
	
	

	function compareDExs( fielname1,fielname2 , alrtStr, flag)
	{
		var period1 = document.getElementById("period").value;
		var contriStartDt = document.getElementById("contriStartDt").value; 
		var contriEndDt = document.getElementById("contriEndDt").value; 
		
		 if (period1 == '10001198277') {
				var a = '31/03/2023';
				compareDatExs(contriEndDt, a, alrtStr, flag);
			}

			else if (period1 == '10001198258') {
				var a = '31/03/2021';
				alrtStr = "Contribution End Date should not be greater than 31/03/2021";
				compareDatExs(contriEndDt,a, alrtStr, flag)
				

			}else if (period1 == '10001198259') {
				var a = '31/03/2022';
				alrtStr = "Contribution End Date should not be greater than 31/03/2022";
				compareDatExs(contriEndDt,a, alrtStr, flag)
				

			}else if (period1 == '10001198260') {
				var a = '31/03/2023';
				alrtStr = "Contribution End Date should not be greater than 31/03/2023";
				compareDatExs(contriEndDt,a, alrtStr, flag)
				

			}else if (period1 == '10001198261') {
			var a = '31/03/2024';
			alrtStr = "Contribution End Date should not be greater than 31/03/2024";
			compareDatExs(contriEndDt,a, alrtStr, flag)
		}
		}
	
	
	
function compareDatExs(fieldName1, fieldName2, alrtStr, flag) {
		
		var one_day = 1000 * 60 * 60 * 24;

		var Date1 = fieldName1;
		var Date2 = fieldName2;
		
		var la_Date1 = new Array();
		la_Date1 = Date1.split("/");
		var la_Date2 = new Array();
		la_Date2 = Date2.split("/");

		var date1 = new Date(la_Date1[2], la_Date1[1] - 1, la_Date1[0]);
		var date2 = new Date(la_Date2[2], la_Date2[1] - 1, la_Date2[0]);
		
		var Diff = Math.floor((date2.getTime() - date1.getTime()) / (one_day));
				
		if (flag == '=' && Diff == 0) {
			alert(alrtStr);
			document.getElementById("contriEndDt").value = "";
			return false;
		}

		else if ((flag == '<' && Diff < 0) || (flag == '>' && Diff > 0)) {
			alert(alrtStr);
			document.getElementById("contriEndDt").value = '';
			return false;
		} else {
			return true;
		}
	}

function validTotal() {
	var total = document.getElementById("total").value;
	if (total > 6000000) {
		alert("Total amount should not be greater than 60 Lacs");
		document.getElementById("total").value = "";
		document.getElementById("empContri").value = "";
		document.getElementById("employerContri").value = "";
	}

}

function sum() {
	// alert("hii");
	var empContri = Number(document.getElementById("empContri").value);
	var employerContri = Number(document.getElementById("employerContri").value);
	var empInterest = Number(document.getElementById("empInterest").value);
	var employerInterest = Number(document
			.getElementById("employerInterest").value);

	var total = empContri + employerContri + empInterest + employerInterest;
	if (isNaN(total)) {
		total = "";
	}
	document.getElementById("total").value = total;
	validTotal();
}
