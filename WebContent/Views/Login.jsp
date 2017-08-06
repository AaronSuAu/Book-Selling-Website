<jsp:include page="module/header.jsp" flush="true"/>

<style>  
.col-center-block {  
    float: none;  
    display: block;  
    margin-left: auto;  
    margin-right: auto;  
}  
.navbar{
	display:none;
}
</style> 
<%
	if(request.getSession().getAttribute("userid")!=null){
		response.sendRedirect("/Assignment2/user/index");
	}
%>
<jsp:include page="module/NavBar.jsp" flush="true"/>
<div id="signUp" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Sign Up</h4>
      </div>
      <div class="modal-body">
        
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-username">Username</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="signup-username" class="form-control" />
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-password">Password</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="password" id="signup-password" class="form-control" />
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-email">Email</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="signup-email" class="form-control" />
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-nickname">Nickname</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="signup-nickname" class="form-control" />
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-firstname">First name</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="signup-firstname" class="form-control" />
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-lastname">Last name</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="signup-lastname" class="form-control" />
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-yearofbirth">Year of Birth</label>
      		</div>
      		<div class="col-sm-8">
      			<select name="yearpicker" id="signup-yearofbirth" class="form-control">
      			<option disabled selected value> -- select an option -- </option></select>
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-fulladdress">Full address</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="signup-fulladdress" class="form-control" />
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#signup-creditcard">Credit card</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="signup-creditcard" class="form-control" />
      		</div>
      	</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="signup-submit">Submit</button>
      </div>
    </div>
  </div>
</div>
<div class="container">  
	<br><br><br><br><br><br>
  <div class="row">  
    <div class="col-xs-6 col-md-4 col-center-block">  
      <form class="form-signin" action="/Assignment2/login" method="POST">  
         <h2 class="form-signin-heading">DBLP Login</h2> 
         <%String error = (String)request.getAttribute("Error");
         	if(error!=null){%>
         <div class="alert alert-danger">
		    <a class="close" data-dismiss="alert">×</a>
		    <strong>Error!</strong><%out.print(error); %>
		</div>
		<%	} %>
        <label for="username" class="sr-only">Username</label>  
        <input style="margin-bottom:10px;" type="text" id="username" class="form-control" name="username" placeholder="Username" required autofocus>  
        <label for="inputPassword" class="sr-only">Password</label>  
        <input type="password" id="inputPassword" class="form-control" name="password" placeholder="Password" required>  
        <div class="checkbox">  
          <label style="padding-left:0px;">  
            <a data-toggle="modal" data-target="#signUp">Sign up</a></label>  
        </div>  
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>  
      </form>  
    </div>  
  </div>  
           
</div>  
<script>
	$(function(){
		for (i = new Date().getFullYear(); i > 1900; i--)
		{
		    $('#signup-yearofbirth').append($('<option />').val(i).html(i));
		}
		//*****************year********************
		//******************submit button**********
		function validateEmail($email) {
  			var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
  			return emailReg.test( $email );
		}
		$("#signup-submit").click(function(){
			var password = $("#signup-password").val();
			if(password.length<6){
				alert("the password should be more than 6 characters");
				return;
			} 
			
			var username = $("#signup-username").val();
			if(username.length == 0){
				alert("The username shouldn't be empty!");
				return;
			}
			var email = $("#signup-email").val();
			if(!validateEmail(email)){
				alert("The email format is wrong");
				return;
			}
			var nickname = $("#signup-nickname").val();
			if(nickname.length == 0){
				alert("The nickname shouldn't be empty");
				return;
			}
			var first_name = $("#signup-firstname").val();
			if(first_name.length == 0){
				alert("The firstname shouldn't be empty");
				return;
			}
			var last_name = $("#signup-lastname").val();
			if(last_name.length == 0){
				alert("The lastname shouldn't be empty");
				return;
			}
			var year_of_birth = $("#signup-yearofbirth").val();
			console.log(year_of_birth);
			if(year_of_birth == null){
				alert("The year of birth shouldn't be empty");
				return;
			}
			var full_address = $("#signup-fulladdress").val();
			if(full_address.length == 0){
				alert("The address shouldn't be empty");
				return;
			}
			var credit_card = $("#signup-creditcard").val();
			if(credit_card.length == 0){
				alert("The credit card shouldn't be empty");
				return;
			} else{
				if(!credit_card.match(/^\d+$/)) {
					alert("The credit card can only contains numbers");
					return;				
				}
			}
			
			$.post("/Assignment2/register",{
				username: username,
				password: password,
				email: email,
				nickname: nickname,
				first_name: first_name,
				last_name: last_name,
				year_of_birth: year_of_birth,
				full_address: full_address,
				credit_card: credit_card
			},function(data){
				if(data == "success"){
					alert("success!");
					$("#signUp").modal('hide');
				}else{
					alert(data);
				}	
			});
				
		});
	});
</script>
<jsp:include page="module/commonScript.jsp" flush="true"/>
<jsp:include page="module/footer.jsp" flush="true"/>
