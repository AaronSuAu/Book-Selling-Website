package com.servlets;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;

import model.Books;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
@MultipartConfig
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String getSubmittedFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 2) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("type") == null){
			Part filePart;
			InputStream fileContent = null;
			OutputStream outputStream = null;
			try {
				filePart = request.getPart("file");
				fileContent = filePart.getInputStream();
			    String fileName = getSubmittedFileName(filePart); // MSIE fix.
				File file = new File("/Applications/apache-tomcat-7.0.76/webapps/Assignment2/"+fileName);
				while(file.exists()){
					fileName = getSaltString() + fileName;
					file = new File(fileName);
				}
				file.createNewFile();
				request.getSession().setAttribute("fileName", "/Assignment2/"+fileName);
				outputStream = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int len;
				while((len = fileContent.read(buf)) > 0){
					System.out.println("111");
					outputStream.write(buf, 0, len);
				}
				outputStream.flush();
				outputStream.close();
				fileContent.close();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				if(fileContent != null){
					try {
						fileContent.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(outputStream != null){
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		    
		}
		//end of image 
		else{
			Map<String, String[]> parameters = request.getParameterMap();
			String keyString="";
			String valueString="";
	        	for(String parameter : parameters.keySet()) {
        	        String[] values = parameters.get(parameter);
	        		keyString = keyString +", " + parameter;
	        		valueString = valueString + "'"+ values[0] + "',";	        		
	        	}
	        	
	        	//, title, type, author, publicdate, venue, price
	        	//'adfasdf','conf','asdf','2016','asdf','123',
	        	keyString = keyString.substring(1);
	        	valueString = valueString.substring(0, valueString.length()-1);
	        	keyString = "("+keyString + ", sellerid, pause, imgsrc)";
	        	valueString = "(" + valueString +",'"+request.getSession().getAttribute("userid")+"','0','"+request.getSession().getAttribute("fileName")+"')";
	        	String sql = "insert into books "+keyString+" values "+valueString+";";
	        	if(Books.insertBook(sql)){
	        		response.getWriter().write("Success");
	        	}else{
	        		response.getWriter().write("Fail");
	        	}
	        	System.out.println(keyString);
	        	System.out.println(valueString);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
