<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>


<jsp:include page="module/header.jsp" flush="true"/>
<jsp:include page="module/NavBar.jsp" flush="true"/>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.19.1/vis.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/vis/4.19.1/vis.min.css" rel="stylesheet" type="text/css" />

    <style type="text/css">
        #mynetwork {
            width: 600px;
            height: 400px;
            border: 1px solid lightgray;
        }
    </style>
<div class="container" style="padding:0px">
	<div class="row">
				<form method="POST" action="/Assignment2/GraphPaperServlet">
    					<div class="form-group">
    						<div class="col-sm-2">
		      				<select id="search_option" class="form-control" name="keyName" >
						        <option value="2">Venus</option>
						        <option value="0">Author</option>
						        <option value="3">Title</option>
		      				</select>  
	      				</div>    
	      				<div class="col-sm-6" id="search_val">
	      					<input type="text" class="form-control" name="type" required>
	      				</div>
	      				
	      				<div class="col-sm-2">
	      					<input type="submit" class="btn btn-primary form-control" value="Search"/>
	      				</div>
    					</div>
  				</form>
	</div>
</div>
<!-- end of search bar -->

<div class="graph" id="mynetwork">
	
</div>
<% 
	if(request.getAttribute("resultGraph") != null){
		//Set<String> resultList = (Set<String>)request.getAttribute("resultGraph");
		List<String> resultList  = new ArrayList((Set<String>)request.getAttribute("resultGraph"));
		String key = request.getAttribute("target").toString();
		if(resultList.size() == 0){
			out.println("No result");
		}else{
			%>
			<script type="text/javascript">
    // create an array with nodes
    var nodes = new vis.DataSet([
    	{id:1, label:'<% out.print(key.replaceAll("'", "\\\\'")); %>'},
    	<%
    		for(int i=0; i < resultList.size()-1; i++){
    			%>
    			{id: <% out.print(i+2); %>, label:'<% out.print(resultList.get(i).replaceAll("'", "\\\\'"));%>'},
    		<% } %>
		{id: <% out.print(resultList.size()+1); %>, label:'<% out.print(resultList.get(resultList.size()-1).replaceAll("'", "\\\\'"));%>'}
    ]);

    // create an array with edges
    var edges = new vis.DataSet([
    	<% 
    		for(int i=0; i < resultList.size() - 1; i++){
    			%>
    			{from: 1, to:<%out.print(i+2); %>},
    			<%
    		}
    	%>
        {from: 1, to: <%out.print(resultList.size() + 1);%>}
    ]);

    // create a network
    var container = document.getElementById('mynetwork');

    // provide the data in the vis format
    var data = {
        nodes: nodes,
        edges: edges
    };
    var options = {};

    // initialize your network!
    var network = new vis.Network(container, data, options);
</script>
			<% 
		}
	}
%>
<jsp:include page="module/commonScript.jsp" flush="true"/>
<jsp:include page="module/footer.jsp" flush="true"/>