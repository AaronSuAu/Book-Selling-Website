<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<jsp:include page="module/header.jsp" flush="true"/>
<jsp:include page="module/NavBar.jsp" flush="true"/>
<!--   modal-->
<div id="addBooks" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Add the book</h4>
      </div>
      <div class="modal-body">
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#book-title">Book Title</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="book-title" class="form-control" />
      		</div>
      	</div>
      	
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#book-type">Type</label>
      		</div>
      		<div class="col-sm-8">
      			<select name="" id="book-type" class="form-control">
      				<option disabled selected value> -- select an option -- </option>
      				<option value="journals">journals</option>
      				<option value="conf">conf</option>
      				<option value="phdthesis">phdthesis</option>
      			</select>
      		</div>
      	</div>
      	
      	
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#book-type">Author</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="book-author" class="form-control" />
      		</div>
      	</div>
      	<div class="row form-group">
      		<div class="col-sm-3">
      			<label for="#book-public-date">Public-date</label>
      		</div>
      		<div class="col-sm-8">
      			<select name="yearpicker" id="book-public-date" class="form-control">
      			<option disabled selected value> -- select an option -- </option></select>      		
      		</div>
      	</div>
      	
      	<div class="row form-group" id="venue-input"style="display:none">
      		<div class="col-sm-3">
      			<label for="#book-venue">Venue</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="book-venue" class="form-control" />
      		</div>
      	</div>
      	
      	<div class="row form-group" style="">
      		<div class="col-sm-3">
      			<label for="#book-price">Price</label>
      		</div>
      		<div class="col-sm-8">
      			<input type="text" id="book-price" class="form-control" />
      		</div>
      	</div>
      	
      	<div class="row form-group" style="">
      		<form enctype="multipart/form-data" id="image_upload">
      			<div class="col-sm-6">
      			    	<input name="file" id="file" type="file"/>
      			</div>
      			<div class="col-sm-6">
      			    	<button type="button" id="upload_button" class="btn btn-primary">Upload</button>
      			</div>
      			
			</form>		
      	</div> 
      	
      </div>
      	
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="submit">Submit</button>
      </div>
    </div>
  </div>
</div>
</div>
<!--  end of modal-->

<div class="container">
      <div class="row">
      	<div class="col-sm-8" style="border-right:1px solid #eeeeee">
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
	                		        if(pair.getValue().equals("")){
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
					<%if(request.getParameter("pause") == null){
						%>
						<a class="btn btn-success btn-seller" id="pause">Pause</a>
					<% 
					}else{
						%>
						<a class="btn btn-info btn-seller" id="unpause">Recover</a>
						<%
					}
					
					
					%>
					
					
				</div>
			</div>
					<% 
				}
			%>
		</div>
  		<div class="col-sm-4">
			<a class="btn btn-primary" data-toggle="modal" data-target="#addBooks">Add Books</a>  		
  		</div>
</div>
<script>
	$(function(){
		for (i = new Date().getFullYear(); i > 1900; i--)
		{
		    $('#book-public-date').append($('<option />').val(i).html(i));
		}
		
		var book_type = "#book-type";
		var isConf = false;
		$(book_type).change(function(){
			if($(book_type).val() == "conf"){
				$("#venue-input").show();
				isConf = true;
			} else{
				$("#venue-input").hide();
				isConf = false;
			}
		});
		//************************************upload image******
		var flag = false;						//after uploading the image, it will become true
		$("#upload_button").click(function(){
			var file = $('#file')[0].files[0];
			var ext = $("#file").val().match(/\.(.+)$/)[1];
			console.log(ext);
			if(ext != "jpeg" && ext != "jpg" && ext != "png"){
				alert("Wrong file format!");
				return;
			}
			if(file == null){
				alert("Please choose the picture!");
				return;
			}		
			$.ajax({
		        // Your server script to process the upload
		        url: '/Assignment2/ImageServlet',
		        type: 'POST',

		        // Form data
		        data: new FormData($('#image_upload')[0]),
		        		
		        // Tell jQuery not to process data or worry about content-type
		        // You *must* include these options!
		        cache: false,
		        contentType: false,
		        processData: false,

		        // Custom XMLHttpRequest
		        xhr: function() {
		            var myXhr = $.ajaxSettings.xhr();
		            if (myXhr.upload) {
		                // For handling the progress of the upload
		                myXhr.upload.addEventListener('progress', function(e) {
		                    if (e.lengthComputable) {
		                        $('progress').attr({
		                            value: e.loaded,
		                            max: e.total,
		                        });
		                    }
		                } , false);
		            }
		            return myXhr;
		        },
		        success:function(){
		        		flag = true;
		        		console.log("111");
		        		alert("success");
		        }
		    });
		});
		//************************************end of upload image******
		
		//*************************************upload a book*********
		$("#submit").click(function(){
			if(flag == false){
				alert("You have to upload the image!");
				return;
			} 
			var title = $("#book-title").val();
			if(title.length == 0){
				alert("Plaese enter the book title!")
				return;
			}
			
			var type = $("#book-type").val();
			if(type.length == 0){
				alert("Please enter the book type!");
				return;
			}
			
			var author = $("#book-author").val();
			if(author.length == 0){
				alert("Please enter the author!");
				return;
			}
			
			var publicDate = $("#book-public-date").val();
			if(publicDate.length==0){
				alert("please enter the public date!");
				return;
			}
			var venue = "";
			if(type == "conf"){
				var venue = $("#book-venue").val();
				if(venue.length == 0){
					alert("Please enter the book venue");
					return;
				}
			}
			var price = $("#book-price").val();
			if(price.length==0){
				alert("Please enter price");
				return;
			}
			if(!$.isNumeric(price)){
				alert("The price format is wrong");
				return;
			}
			
			$.post("/Assignment2/ImageServlet",{
				title: title,
				type: type,
				author: author,
				publicdate: publicDate,
				venue: venue,
				price: price,
			},function(data){
				alert("success");
			});
		});
		//*************************************end of upload a book*********
	
		//**************************************pause and unpause*******************
		$(".btn-seller").click(function(){
			var bookids = $(document).test('method');
			if(bookids == null){
				alert("Please choose the books you want");
				return;
			} 
			var type = 5;
			if($(this).attr("id")=="pause"){
				type = 4;
			}
			$.ajax({
				url:"/Assignment2/Seller",
				type:"POST",
				data:{
					type:type,
					bookid:bookids
				},
				success:function(data){
				    location.reload();
				}
			});
		});
		//**************************************end of pause*******************

	});
</script>
<jsp:include page="module/commonScript.jsp" flush="true"/>
<jsp:include page="module/footer.jsp" flush="true"/>