  $(document).ready(function() {
	var contextPath = $("#appRootPath").val();
  $("#txtSchemeCode").blur(function(){
	  var schemeCode=$("#txtSchemeCode").val();
	  $.ajax({
		  type : "GET",
		  url : contextPath+"/ddo/displaySchemeNameForCode/"
		  + schemeCode,
		  async : true,
		  contentType : 'application/json',
		  error : function(data) {
			  console.log(data);
		  },
		  success : function(data) {
				var len = data.length;
				if (len != 0) {
					$('#cmbSchemeName').empty();
					$('#cmbSchemeName')
							.append(
									"<option value='0'>Please Select</option>");
					var temp = data;
					$
							.each(
									temp,
									function(index,
											value) {
										console
												.log(value[1]);
										$(
												'#cmbSchemeName')
												.append(
														"<option value="
																+ value[0]
																+ ">"
																+ value[1]
																+ "</option>");
									});
				} else {
					$('#cmbSchemeName').empty();
					$('#cmbSchemeName')
							.append(
									"<option value='0'>Please Select</option>");
					swal("Record not found !!!");
				}
			}
	  });
  });
  
  });