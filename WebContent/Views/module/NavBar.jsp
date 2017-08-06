

<nav class="navbar navbar-light" style="background-color: #e3f2fd;" role="navigation">
    <div class="container-fluid">
	    <div class="navbar-header">
	        <a class="navbar-brand" href="/Assignment2/user/index">DBLP</a>
	    </div>
	    <div>
	    <ul class="nav navbar-nav">
		    <li class="nav-item active">
		      <a class="nav-link" href="/Assignment2/user/index">Home <span class="sr-only">(current)</span></a>
		    </li>
		    <li class="nav-item">
		      <a class="nav-link" href="/Assignment2/ShoppingCart">Shopping Cart</a>
		    </li>
		    <li class="nav-item">
		      <a class="nav-link" href="/Assignment2/WishList">WishList</a>
		    </li>
		    <li class="nav-item">
		      <a class="nav-link" href="/Assignment2/Views/Graph.jsp">Graph</a>
		    </li>
		    <% 
		    if(request.getSession().getAttribute("userid") != null){
		    	%><li class="nav-item">
		      <a class="nav-link" data-toggle="modal" data-target="#changeInfo">Change Info</a>
		    </li>
		    <% }
		    %>
		    <%
		    if(request.getSession().getAttribute("role")!=null && request.getSession().getAttribute("role").equals("1")){
		    		%>
		    		<li class="dropdown nav-item">
          		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Books<span class="caret"></span></a>
          		<ul class="dropdown-menu">
            			<li><a href="/Assignment2/Seller">On sale</a></li>
            			<li><a href="/Assignment2/Seller?pause=1">Paused</a></li>
          		</ul>
       	   </li>
		    		<%
		    }
		    %>
		   
	  	</ul>
	  	</div>
	    
    </div>
</nav>
