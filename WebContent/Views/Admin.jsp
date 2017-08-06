<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<jsp:include page="module/header.jsp" flush="true"/>
<jsp:include page="module/NavBar.jsp" flush="true"/>

<!-- Search Bar -->
<div class="container" style="padding:0px">
	<div class="row">
				<form id="form-act" method="POST" action="/Assignment2/regularSearch">
    					<div class="form-group">
    						<div class="col-sm-3">
		      				<select id="search_option" class="form-control" name="key" required>
		      					<option disabled selected value> -- select an option -- </option>
						        <option value="1">Ban a user</option>
						        <option value="2">Show user log</option>
						        <option value="3">Remove a book</option>
		      				</select>  
	      				</div>    
	      				<div class="col-sm-6" >
	      					<input type="text" id="search_val" class="form-control" name="value" required>
	      					<input type="hidden" name="isAdmin" value="1" />
	      				</div>
	      				
	      				<div class="col-sm-2">
	      					<input id="form-button" type="submit" class="btn btn-primary form-control" value="Confirm"/>
	      				</div>
    					</div>
  				</form>
	</div>
</div>
<!-- end of search bar -->
<!-- remove list -->
<div class="container">
      <div class="row">
      	<div class="col-sm-8">
			<%
				if(request.getAttribute("result") != null && request.getAttribute("isRandom")==null){
					List<Map<String, String>> resultList = (List<Map<String, String>>)request.getAttribute("result");
					if(resultList.size() == 0){
						out.println("<h1>Sorry, no matching datasets found!</h1>");
					}else{
						int i=0;
						for(Map<String, String> map: resultList){
							out.println("<div class =\"row booklist\">");
							out.println("<a data-toggle=\"modal\" data-target=\"#article_"+i+"\">"+map.get("title")+"</a>");
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
	                		    out.println("<div class=\"modal-footer\">");
	                		    out.println("<label class='success-add' style='display:none'>Success</label><button id=\"modal-shopping-"+i+"\" type=\"button\" class=\"btn-remove btn btn-primary\">");
	                			out.println("Remove\r\n</button></div>\r\n        ");
	                			out.println("</div>\r\n    </div>\r\n</div>");
	            				i++;
						}
					}
				}else if(request.getAttribute("error") != null){
					out.println(request.getAttribute("error"));
				}
				
			%>
			<%
				if(request.getAttribute("result") != null){
					%>
					<div class="row">
				<div class="col-sm-6">
				</div>
				<div class="col-sm-6">
					<a class="btn btn-success" id="previous">Previous</a>
					<a class="btn btn-info" id="next">Next</a>
				</div>
			</div>
					<% 
				}
			%>
			<%
				if(request.getAttribute("userlog")!=null){
					List<Map<String, String>> userList = (List<Map<String, String>>)request.getAttribute("userlog");
					if(userList.size() == 0){
						out.println("<h1>Sorry, no matching datasets found!</h1>");
					}
					else{
						
					
					%>
					<table class="table">
					  <thead>
					    <tr>
					      <th>#</th>
					      <th>bookid</th>
					      <th>actiontype</th>
					      <th>actiontime</th>
					      <th>price</th>
					    </tr>
					  </thead>
					  <tbody>
					    <%
					    		int i=0;
					    		for(Map<String, String> map:userList){
					    			%>
					    				<tr>
						    				<th scope="row"> <% out.println(i); %> </th>
						    				<th><% out.println(map.get("bookid")); %></th>
						    				<th><% out.println(map.get("actiontype")); %></th>
						    				<th><% out.println(map.get("actiontime")); %></th>
						    				<th><% out.println(map.get("buyingprice")); %></th>
					    				</tr>
					    			<% 
					    			i++;
					    		}
					    %>
					  </tbody>
					</table>
					
					<% 
				}
					}
			%>
			
		</div>
  		<div class="col-sm-4">
  	
  		</div>
  	</div>
  
</div>
<!-- end of remove list -->
<jsp:include page="module/commonScript.jsp" flush="true"/>
<script>
	$(function(){
		var search_option;
		/* $( "#form-act" ).submit(function( event ) {
			  alert( "Handler for .submit() called." );
			  event.preventDefault();
		}); */
		
		$("#form-button").click(function(e){
			e.preventDefault();
			search_option = $("#search_option").val();
			var value = $("#search_val").val();
			if(search_option == null){
				alert("choose what you want");
				return;
			}
			if(value.length == 0){
				alert("Enter the value!")
				return;
			}
			if(search_option == 3){//remove a book
				$("#search_option").attr("name","keyName");
				$("#search_val").attr("name","title");
				$("#form-act").submit();
			}else if(search_option == 2){//track a user
				$("#form-act").attr("action","/Assignment2/Admin");
				$("#form-act").submit();
			}else{//ban 1 user
				$.post("/Assignment2/Admin",{
					key: search_option,
					value: value
				},function(data){
					alert(data);
				});
			}
		});
		//*********************** get parameter in the url*******
		var getUrlParameter = function getUrlParameter(sParam) {
		    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
		        sURLVariables = sPageURL.split('&'),
		        sParameterName,
		        i;

		    for (i = 0; i < sURLVariables.length; i++) {
		        sParameterName = sURLVariables[i].split('=');

		        if (sParameterName[0] === sParam) {
		            return sParameterName[1] === undefined ? true : sParameterName[1];
		        }
		    }
		};
		//*********************** end of get parameter in the url*******
		
		//************************next page and previous page ********
		var page = getUrlParameter("page");
		var next_button = "#next";
		var previous_button = "#previous";
		if($(".booklist").length == 0){
			$(next_button).css("display","none");
		}
		if(page==null || page == 0){
			$(previous_button).css("display","none");
			page = 0;
			page = parseInt(page);
		}else{
			page = parseInt(page);
			var previous_page = page -1;
			$(previous_button).attr("href","/Assignment2/regularSearch?page=" + previous_page+"&&isAdmin=1");
		}
		var next_page = page + 1;
		$(next_button).attr("href","/Assignment2/regularSearch?page=" + next_page+"&&isAdmin=1");
		//************************ end of next page and previous page ********
		
		$(".btn-remove").click(function(){
			var book = $(this).parent().parent().parent().parent().prev();
			var modal = $(this).parent().parent().parent().parent();
			var bookid = $(this).parent().parent().find("p:contains('bookid')").html();
			bookid = bookid.substring(7,bookid.length);
			console.log(bookid);
			$.post("/Assignment2/delete",{
				type: 6,
				bookid: bookid
			},function(data){
				alert("success");
				book.hide();
				modal.modal("hide");
			}); 
		});
	})
</script>
<style>
.navbar{
display:none;}
</style>
<jsp:include page="module/footer.jsp" flush="true"/>