<jsp:include page="module/header.jsp" flush="true"/>
<style>  
.col-center-block {  
    float: none;  
    display: block;  
    margin-left: auto;  
    margin-right: auto;  
}  
</style> 
<div class="container">  
	<br><br><br><br><br><br>
  <div class="row">  
    <div class="col-xs-6 col-md-4 col-center-block">  
      <form class="form-signin" action="/Assignment2/login" method="POST">  
         <h2 class="form-signin-heading">DBLP Admin Login</h2> 
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
          <input type="hidden" name="isFromAdmin" value="yes" />
        <button class="btn btn-lg btn-primary btn-block" style="margin-top:10px" type="submit">Login</button>  
      </form>  
    </div>  
  </div>  
           
</div>  
<jsp:include page="module/commonScript.jsp" flush="true"/>
<jsp:include page="module/footer.jsp" flush="true"/>