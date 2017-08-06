<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<jsp:include page="module/header.jsp" flush="true"/>
<jsp:include page="module/NavBar.jsp" flush="true"/>

<div class="container">
      <div class="row">
      	<div class="col-sm-8">
			<%
				if(request.getAttribute("result") != null){
					List<Map<String, String>> resultList = (List<Map<String, String>>)request.getAttribute("result");
					if(resultList.size() == 0){
						out.println("<h1>Sorry, no matching datasets found!</h1>");
					}else{
						int i=0;
						for(Map<String, String> map: resultList){
							out.println("<div class =\"row booklist\">");
							out.println("<input type='checkbox' name='books' class='check-books' /><a data-toggle=\"modal\" data-target=\"#article_"+i+"\">"+map.get("title")+"</a>");
	            				out.println("<p>Author: "+ map.get("author") + "</span>");
	            				out.println("</div>");
	            				out.println("<div class=\"modal fade in\" id=\"article_"+i+"\" tabindex=\"-1\" role=\"dialog\">");
	                			out.println("    <div class=\"modal-dialog\" role=\"document\">");
	                			out.println("<div class=\"modal-content\">");
	                			out.println("<div class=\"modal-header\">\r\n                <button type=\"button\" class=\"close\" style=\"margin-top:-20px\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n                    <span aria-hidden=\"true\">\u00D7</span>\r\n                </button>");
	                		    out.println("<h4>"+map.get("title")+"</h4>");
	                		    out.println("</div>\r\n            <div class=\"modal-body\">");
	                		    Iterator it = map.entrySet().iterator();
	                		    out.println("<div class=\"row\" style=\"margin-left:0px;\">");
                		        out.println("<img src='"+map.get("imgsrc") + "'alt='null'>");
                		        out.println("</div>");
	                		    //out.println("<img src='"+map.get("imgsrc")+"' alt='Null'");
	                			while (it.hasNext()) {
	                		        Map.Entry pair = (Map.Entry)it.next();
	                		        if(pair.getKey()=="imgsrc"){
	                		        		continue;
	                		        }
	                		        out.println("<div class=\"row\" style=\"margin-left:0px;\">");
	                		        out.println("<p>"+pair.getKey()+": "+pair.getValue()+"</p>");
	                		        out.println("</div>");
	                		        //out.println(pair.getKey() + " = " + pair.getValue());
	                		        it.remove(); // avoids a ConcurrentModificationException
	                		    }
	                		    out.println("</div>");	                		    
	                			out.println("</div>\r\n    </div>\r\n</div>");
	            				i++;
						}
					}
				}else if(request.getAttribute("error") != null){
					out.println(request.getAttribute("error"));
				}
				
			%>
			<%
				List<Map<String, String>> resultList2 = (List<Map<String, String>>)request.getAttribute("result");
				if(resultList2.size()>0){
					%>
					<div class="row">
				<div class="col-sm-6">
				</div>
				<div class="col-sm-6">
					<a class="btn btn-success btn-cart" id="buy">Buy</a>
					<a class="btn btn-info btn-cart" id="remove">Remove</a>
				</div>
			</div>
					<% 
				}
			%>
			
		</div>
  		<div class="col-sm-4">
  	
  		</div>
  	</div>
  
</div>
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Please wait... </h4>
      </div>
    </div>

  </div>
</div>
<jsp:include page="module/commonScript.jsp" flush="true"/>
<script>
	$(function(){
		var bookids = [];
		$(".btn-cart").click(function(){
			var bookids = $(document).test('method');
			console.log(bookids);
			if(bookids == null){
				alert("Please choose the books you want");
				return;
			} 
			
			var type = 7;						//buy
			var url = "/Assignment2/sellbook";
			if($(this).attr("id")=="remove"){	//remove
				type = 1;
				url = "/Assignment2/ShoppingCart";
			}
			console.log(1);
			$("#myModal").modal("show");
			$.ajax({
				url: url,
				type:"POST",
				data:{
					type:type,
					bookid:bookids
				},
				success:function(data){
					$("#myModal").modal("hide");

					alert("success");
				    location.reload();
				}
			});
		});
	});
</script>
<jsp:include page="module/footer.jsp" flush="true"/>