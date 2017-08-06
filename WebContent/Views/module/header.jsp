<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/Assignment2/Views/js/jquery-3.2.0.min.js"></script>
<link rel="stylesheet" href="/Assignment2/Views/js/bootstrap.css">
<style>
a{
cursor:pointer
}
</style>
<title>Book</title>
</head>
<!--   modal-->
<div id="changeInfo" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Change your information</h4>
      </div>
      <div class="modal-body">
      	<div class="row form-group">
      		<div class="col-sm-4">
      			<select name="info" id="change-info" class="form-control">
      			<option disabled selected value> -- select an option -- </option>
      			<option value="firstname">firstname</option>
      			<option value="password">password</option>
      			<option value="lastname">lastname</option>
      			<option value="email">email</option>
      			<option value="nickname">nickname</option>
      			<option value="birthyear">birthyear</option>
      			<option value="address">address</option>
      			<option value="creditcard">creditcard</option>
      			</select>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="change-info-value" class="form-control "/>      			
      		</div>
      	</div>
      	
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="change-info-submit">Submit</button>
      </div>
    </div>
  </div>
</div>
</div>
<!--  end of modal-->
<script>
$(function(){
	
	//******************email**********
	function validateEmail($email) {
			var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			return emailReg.test( $email );
	}
	//******************email**********

	var key= "#change-info";
    var value = "#change-info-value";
	$(key).change(function(){
		if($(key).val() == "password"){
			$(value).val("");
			$(value).attr("type","password");
		}else{
			$(value).val("");
			$(value).attr("type","text");
		}
	});
	$("#change-info-submit").click(function(){
		var final_key = $(key).val();
		var final_value = $(value).val();
		if(final_key == null){
			alert("select the type you want to change!");
			return;
		}
		if(final_value.length == 0){
			alert("Enter the information");
			return;
		}
		if(final_key == "password"){
			if(final_value.length < 6){
				alert("The password should be longer than 6 characters");
				return;
			}
		}
		if(final_key == "email"){
			if(!validateEmail(final_value)){
				alert("The email format is wrong!");
				return;
			}
		}
		if(final_key == "creditcard"){
			if(!final_value.match(/^\d+$/)) {
				alert("The credit card can only contains numbers");
				return;				
			}
		}
		if(final_key =="yearofbirth"){
			if(final_value.length != 4 || !final_value.match(/^\d+$/)){
				alert("The year format is wrong!");
				return;
			}
		}
		console.log(final_key, final_value);
		$.post("/Assignment2/user/changeUserInfo",{
			key: final_key,
			value: final_value
		},function(data){
			alert(data);
		});
		
	});
});
</script>
<body>