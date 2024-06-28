$(document).ready(function() {
	if($('#messageReject').val() =='REJCTED') {
		swal({
	    	  title: 'Deleted!',
			  text: 'Deleted Successfully',
			  type: 'success',
			  allowOutsideClick: false
	    })
	}
    $('#example').DataTable();
});

function ConfirmDeleteRecord(departmentId,isActive) {
	if(isActive==1){
		swal({
			  title: 'Are you sure ?',
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes,Delete It !',
			  allowOutsideClick: false,
			}).then((result) => {
			    if (result.value) {   
					$.ajax({
					      type: "GET",
					      url: "/master/deleteDept/"+departmentId,
					      async: true,
					      success: function(data){
					    	  //location.reload(true);
					    	  swal({
						    	  title: 'Deleted!',
								  text: 'Deleted Successfully',
								  type: 'success',
								  allowOutsideClick: false,
								  //timer: 3000
						    });
					    	  setTimeout(function() {
								    location.reload(true);
								}, 3000);
					      }
					 });
			     }
			/*if (result.value) {
				 swal({
			    	  title: 'Deleted!',
					  text: 'Deleted Successfully',
					  type: 'success',
					  allowOutsideClick: false
			    })
			}*/
		})
	}
}







