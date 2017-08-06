<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<jsp:include page="module/header.jsp" flush="true"/>
<jsp:include page="module/NavBar.jsp" flush="true"/>


<!-- Search Bar -->
<style>
a{
	cursor:pointer
}
</style>
<!-- Advanced modal -->
<div class="modal fade in" id="advanced-search" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h3>Advanced Search</h3>
            </div>
            <div class="modal-body">
                <div class="row" style="margin-left:0px;margin-right:0px">
                    <form method="POST" action="/Assignment2/advancedSearch">
                    		<label for="radio">Type:&nbsp</label>
	                    	<label class="radio-inline">
					      <input class="ad-radio" type="radio" name="type" value="">Any
					    </label>
					    <label class="radio-inline">
					      <input class="ad-radio" type="radio" name="type" value="journal">Journal
					    </label>
					    <label class="radio-inline">
					      <input class="ad-radio" type="radio" name="type" value="conf">Conference
					    </label>
					    <label class="radio-inline">
					      <input class="ad-radio" type="radio" name="type" value="phd">Phd
					    </label>
					    <div class="form-group" id="form-venues" style="display:none">
						  <label for="ad-author">Venues:</label>
						  <input type="text" class="form-control" name="venue" id="venues">
						</div>
                    		<div class="form-group">
						  <label for="ad-author">Author:</label>
						  <input type="text" class="form-control" name="author" id="ad-author">
						</div>
					
						<div class="form-group">
						  <label for="ad-year">Year:</label>
						  <select name="year1" id="form-year1" class="form-control" style="margin-bottom:10px">
      						   <option disabled selected value> -- From -- </option>
      					  </select>	
						  <select name="year2" id="form-year2" class="form-control">
      						   <option disabled selected value> -- To -- </option>
      					  </select>	
      					</div>		
      							
						<div class="form-group">
						  <label for="ad-title">Title:</label>
						  <input type="text" class="form-control" name="title" id="ad-title">
							<input type="hidden" name="search" value="advanced"/>
						</div>
							
						<button type="submit" class="btn btn-primary pull-right">Search</button>
                    </form>
                </div>
            </div>
          
        </div>
    </div>
</div>
<!-- end of Advanced search modal -->

<!-- Search Bar -->
<div class="container" style="padding:0px">
	<div class="row">
				<form method="POST" action="/Assignment2/regularSearch">
    					<div class="form-group">
    						<div class="col-sm-2">
		      				<select id="search_option" class="form-control" name="keyName" >
						        <option value="2">Type</option>
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
	      				<div class="col-sm-2">
	      					<a class="form-control btn btn-success" data-toggle="modal" data-target="#advanced-search">Advanced search</a>
	      				</div>
    					</div>
  				</form>
	</div>
</div>
<!-- end of search bar -->
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
	                			%>
	                			<textarea name="" class="feedback-text" style="width:100%; height:100px"></textarea>
	                			<button class="btn btn-success feedback">Submit feedback</button>
	                			<p></p>
	                			<% 
	                		    out.println("</div>");
	                		    out.println("<div class=\"modal-footer\">");
	                		    out.println("<label class='success-add' style='display:none'>Success</label><button id=\"modal-shopping-"+i+"\" type=\"button\" class=\"btn-shopping btn btn-primary\">");
	                			out.println("Add to shopping cart\r\n                </button><button id=\"modal-wish-"+i+"\" type=\"button\" class=\"btn-wish btn btn-primary\">add to wish list </button>            </div>\r\n        ");
	                			out.println("</div>\r\n    </div>\r\n</div>");
	            				i++;
						}
					}
				}else if(request.getAttribute("error") != null){
					out.println(request.getAttribute("error"));
				}
				
			%>
			<%
				if(request.getAttribute("isRandom") == null){
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
<script>
	$(function(){
		var search_option="#search_option";
		var search_options=['Author', 'Year','type', 'title']; 
		$(search_option).change(function(){
			$("#search_val input").attr("name", search_options[$(search_option).val()]);
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
			$(previous_button).attr("href","/Assignment2/regularSearch?page=" + previous_page);
		}
		var next_page = page + 1;
		$(next_button).attr("href","/Assignment2/regularSearch?page=" + next_page);
		//************************ end of next page and previous page ********
		
		//*************add to shopping cart***********************
		$(".btn-shopping").click(function(){
			var bookids=[];
			var bookid = $(this).parent().parent().find("p:contains('bookid')").html();
			var label = $(this).parent().find("label");
			bookid = bookid.substring(7,bookid.length);
			bookids.push(bookid);
			$.ajax({
					url:"/Assignment2/ShoppingCart",
					type:"POST",
					data:{
						type:"0",
						bookid:bookids
					},
					success:function(data){
						alert(data);
					}
				});
		});
		//**************end of shopping cart************************
		
		//*************add to wish list***********************
		$(".btn-wish").click(function(){
			var bookids=[];
			var bookid = $(this).parent().parent().find("p:contains('bookid')").html();
			var label = $(this).parent().find("label");
			bookid = bookid.substring(7,bookid.length);
			bookids.push(bookid);
			$.ajax({
					url:"/Assignment2/WishList",
					type:"POST",
					data:{
						type:"2",
						bookid:bookids
					},
					success:function(data){
						alert(data);
					}
				});
		});
		//*************end of add to wish list***********************
		
		//********************advanced search************************
		var ad_radio = ".ad-radio:checked";
		$(".ad-radio").change(function(){
			var type = $(".ad-radio:checked").val();
			if(type === "conf"){
				$("#form-venues").show();
			}else{
				$("#form-venues").hide();
			}
		})
		
		//***********************year 1 and year 2*********************
		for (i = new Date().getFullYear(); i > 1900; i--)
		{
		    $('#form-year1').append($('<option />').val(i).html(i));
		}
		
		//***********************year 1 and year 2*********************
		$("#form-year1").change(function(){
			$("#form-year2 option").remove();
			for (i = new Date().getFullYear(); i > $("#form-year1").val(); i--)
			{
			    $('#form-year2').append($('<option />').val(i).html(i));
			}
		});
		//********************end of advanced search*******************
		
		//*********************************submit feedback*****************
		$(".feedback").click(function(){
			var text = $(this).prev().prev().val();
			var p = $(this).next();
			//var row = $(this).parent()
			if(text.length == 0){
				alert("Please input your feedback");
			}else{
				$("#myModal").modal("show");
				 $.post("/Assignment2/Entity",{
					text: text},function(data){
						p.html(data);
						console.log(data);
						$("#myModal").modal("hide");
					}); 
			}
		});
		//*********************************end of submit feedback *********
	});
</script>
<jsp:include page="module/commonScript.jsp" flush="true"/>
<jsp:include page="module/footer.jsp" flush="true"/>