package assignment1;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.corba.se.spi.ior.TaggedProfileTemplateBase;

/**
 * Created by Aaron on 3/25/17.
 */
public class Dblp {
	private String path;

	public Dblp() {

	}

	public Dblp(String path) {
		this.path = path;
	}
	public void addPaper(Connection connection, String id, String paperName){
		paperName = paperName.replace("'", "''");
		String sql1 = "insert into entityStore Values('" + id + "', 'type','paper')";
		String sql2 = "insert into entityStore Values('" + id + "', 'class','entityNode')";
		String sql3 = "insert into entityStore Values('" + id + "', 'title','"
				+ paperName + "')";

		DB.generalInsert(sql1, connection);
		DB.generalInsert(sql2, connection);
		DB.generalInsert(sql3, connection);
	}
	public void addPaper2(Connection connection, String id, String venue){
		String sql1 = "insert into entityStore2 Values('" + id + "', 'type','paper')";
		String sql2 = "insert into entityStore2 Values('" + id + "', 'class','entityNode')";
		String sql3 = "insert into entityStore2 Values('" + id + "', 'title','"
				+ venue + "')";
		DB.generalInsert(sql1, connection);
		DB.generalInsert(sql2, connection);
		DB.generalInsert(sql3, connection);
	}
	public void addVenue(Connection connection, String id, String venue){
		String sql1 = "insert into entityStore Values('"+id+"', 'type','venus')";
		String sql2 = "insert into entityStore Values('"+id+"', 'class','entityNode')";
		String sql3 = "insert into entityStore Values('"+id+"', 'name','"+venue+"')";
		DB.generalInsert(sql1, connection);
		DB.generalInsert(sql2, connection);
		DB.generalInsert(sql3, connection);
	}
	public void addAuthor(Connection connection, String id, String author){
		author = author.replace("'", "''");
		String sql4 = "insert into entityStore Values('"+id+"', 'type','author')";
		String sql5 = "insert into entityStore Values('"+id+"', 'class','entityNode')";
		String sql6 = "insert into entityStore Values('"+id+"', 'name','"+author+"')";
		DB.generalInsert(sql4, connection);
		DB.generalInsert(sql5, connection);
		DB.generalInsert(sql6, connection);
	}
	
	public void addEdge(Connection connection, String id){
		String sql7 = "insert into entityStore Values('"+id+"', 'type','directedLink')";
		String sql8 = "insert into entityStore Values('"+id+"', 'class','Edge')";
		String sql9 = "insert into entityStore Values('"+id+"', 'label','authoredBy')";
		DB.generalInsert(sql7, connection);
		DB.generalInsert(sql8, connection);
		DB.generalInsert(sql9, connection);
	}
	public void addEdge2(Connection connection, String id){
		String sql7 = "insert into entityStore Values('"+id+"', 'type','directedLink')";
		String sql8 = "insert into entityStore Values('"+id+"', 'class','Edge')";
		String sql9 = "insert into entityStore Values('"+id+"', 'label','citedBy')";
		DB.generalInsert(sql7, connection);
		DB.generalInsert(sql8, connection);
		DB.generalInsert(sql9, connection);
	}
	public List<Map<String, String>> tripleTables() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		NodeList article_list = getArticleList();// get the list of the articles
		int total = article_list.getLength();
		int a = 0, b = 0,v=0,edge = 0, edge2 = 0; // a author, b book, e edge
		Connection connection = DB.getConnection();
		System.out.println(total);
		for (int i = 0; i < total; i++) {
			//System.out.println(i);
			Node article = article_list.item(i);
			if (article.getNodeType() == Node.ELEMENT_NODE) {
				NodeList article_info = article.getChildNodes(); // get the
																	// detail of
																	// each
																	// article
				Element e = (Element) article;
				String entityId = "P" + String.valueOf(b);
				int tempB = b;
				b++;
				if(e.getElementsByTagName("title").getLength()<=0){
					continue;
				}
				addPaper(connection, entityId, e.getElementsByTagName("title").item(0).getTextContent());
				if(e.getElementsByTagName("booktitle").getLength() > 0){					
					String venue = e.getElementsByTagName("booktitle").item(0).getTextContent();
					java.sql.ResultSet rSet = null;
					rSet = DB.generalSelect("select * from entityStore where attribute_value='" + venue + "';",
							connection);
					try {
						if(rSet.next()){
							String venueid = rSet.getString("entity_id");
							String edge2id = "EE"+String.valueOf(edge2);
							edge2++;
							addEdge2(connection, edge2id);
							String sql10 = "insert into graph Values('"+entityId+"', '"+edge2id+"','"+venueid+"')";
							DB.generalInsert(sql10, connection);
						}else{
							String vString= "V"+String.valueOf(v);
							v++;
							addVenue(connection, vString, venue);
							String edge2id = "EE"+String.valueOf(edge2);
							edge2++;
							addEdge2(connection, edge2id);
							String sql10 = "insert into graph Values('"+entityId+"', '"+edge2id+"','"+vString+"')";
							DB.generalInsert(sql10, connection);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				
				for (int j = 0; j < e.getElementsByTagName("author").getLength(); j++) {
					String author = e.getElementsByTagName("author").item(j).getTextContent();
					java.sql.ResultSet rSet = null;
					author = author.replaceAll("'", "''");
					rSet = DB.generalSelect("select * from entityStore where attribute_value='" + author + "';",
							connection);
					try {
						if (!rSet.next()) {
							String authorid = "A"+String.valueOf(a);
							int tempA = a;
							a++;
							addAuthor(connection, authorid, author);
							String edgeid= "E"+String.valueOf(edge);
							edge ++;
							addEdge(connection, edgeid);
							String sql10 = "insert into graph Values('"+entityId+"', '"+edgeid+"','"+authorid+"')";
							DB.generalInsert(sql10, connection);
						}else{
							String authorid = rSet.getString("entity_id");
							String edgeid= "E"+String.valueOf(edge);
							addEdge(connection, edgeid);
							edge ++;
							String sql10 = "insert into graph Values('"+entityId+"', '"+edgeid+"','"+authorid+"')";
							DB.generalInsert(sql10, connection);
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		}

		return list;
	}


	// return the current article information
	public Map<String, String> getDetailofArticle(NodeList article_info, Element e) {
		String info = "", info2 = "";
		String key, value;
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", e.getAttribute("key"));
		int number, i, j;
		for (j = 0; j < article_info.getLength(); j++) {
			info = article_info.item(j).getNodeName();
			if (info.equals(info2)) {
				continue;
			}
			number = e.getElementsByTagName(info).getLength();
			if (number > 0) {
				value = "";
				for (i = 0; i < e.getElementsByTagName(info).getLength(); i++) {
					value = value + e.getElementsByTagName(info).item(i).getTextContent() + ", ";
				}
				key = info;
				value = value.substring(0, value.length() - 2);
				map.put(key, value);
				info2 = article_info.item(j).getNodeName(); // get rid of
															// multiple rows of
															// author, etc
			}
		}
		return map;
	}

	// This function is to check if the book satisfies th limitations such as
	// "author", "year", etc.
	public boolean check(List<String> keyList, List<String> valueList, Element e) {
		boolean flag1, flag2 = true;
		String currentKey, currentValue;
		int i, k;
		for (k = 0; k < keyList.size(); k++) { // check the value of the book
			currentKey = keyList.get(k).toLowerCase();
			currentValue = valueList.get(k).toLowerCase();
			flag1 = false;
			for (i = 0; i < e.getElementsByTagName(currentKey).getLength(); i++) {
				if (e.getElementsByTagName(currentKey).item(i).getTextContent().toLowerCase().contains(currentValue)) {
					flag1 = true; // if the value is the same, go out of the
									// loop
					break;
				}
			}
			if (!flag1) { // the value is not the same
				flag2 = false;
				break;
			}
		}
		// after the comparison, if flag2 is still true, means find the
		// infomation wanted
		if (flag2) {
			return true;
		}
		return false;
	}

	// get the result of the articles wanted
	//
	// type 1: key is "author", "title", etc
	public List<Map<String, String>> getInfoByDetail(List<String> keyList, List<String> valueList, String type) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		NodeList article_list = getArticleList();// get the list of the articles
		for (int temp = 0; temp < article_list.getLength(); temp++) {
			Node article = article_list.item(temp); // get the info of each
													// article
			if (article.getNodeType() == Node.ELEMENT_NODE) {
				String article_type = article.getNodeName();
				NodeList article_info = article.getChildNodes(); // get the
																	// detail of
																	// each
																	// article
				Element e = (Element) article;
				if (type.equals("")) { // this type means no publication type
					if (check(keyList, valueList, e)) {
						list.add(getDetailofArticle(article_info, e));
					}
				} else if (type.equals("journals")) {
					if (article_type.equals("article") && check(keyList, valueList, e)) {
						list.add(getDetailofArticle(article_info, e));
					}
				} else if (type.equals("conf")) {
					if (article_type.equals("inproceedings") && check(keyList, valueList, e)) {
						list.add(getDetailofArticle(article_info, e));
					}
				} else if (type.equals("phd")) {
					if (article_type.equals("phdthesis") && check(keyList, valueList, e)) {
						list.add(getDetailofArticle(article_info, e));
					}
				}
			}
		}
		return list;
	}

	// get the whole list of the file, stored as a list
	public NodeList getArticleList() {
		NodeList article_list = null;
		try {
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			article_list = doc.getDocumentElement().getChildNodes();// get the
																	// list of
																	// the
																	// articles
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article_list;
	}

	public List<Map<String, String>> generateRandomArticles() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		NodeList article_list = getArticleList();// get the list of the articles
		int total = article_list.getLength();
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			int result = r.nextInt(total);
			Node article = article_list.item(result);
			if (article.getNodeType() == Node.ELEMENT_NODE) {
				NodeList article_info = article.getChildNodes(); // get the
																	// detail of
																	// each
																	// article
				Element e = (Element) article;
				list.add(getDetailofArticle(article_info, e));
			} else {
				i--;
			}

		}
		return list;
	}

	public List<Map<String, String>> getAllArticles() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		NodeList article_list = getArticleList();// get the list of the articles
		int total = article_list.getLength();
		Random r = new Random();
		Random index = new Random();
		Connection connection = DB.getConnection();
		for (int i = 0; i < total; i++) {
			int j = i % 100;
			if (j == 0 && i > 100) {
				System.out.println(i);
			}
			int result = r.nextInt(200);

			Node article = article_list.item(i);
			if (article.getNodeType() == Node.ELEMENT_NODE) {
				NodeList article_info = article.getChildNodes(); // get the
																	// detail of
																	// each
																	// article
				Element e = (Element) article;
				Map<String, String> map = new HashMap<>();
				map = getDetailofArticle(article_info, e);
				map.put("price", Integer.toString(result));
				map.put("imgsrc", "/");
				insertToDatabase(map, connection);
			}

		}
		DB.closeConnection(connection);
		return list;
	}

	public void insertToDatabase(Map<String, String> map, Connection connection) {
		String type = map.get("type");
		String sql = "";
		if (map.get("title") == null) {
			map.put("title", "unknown");
		}
		if (map.get("author") == null) {
			map.put("author", "unknown");
		}
		if (map.get("year") == null) {
			map.put("year", "unknown");
		}
		map.put("title", map.get("title").replace("'", "''"));
		map.put("author", map.get("author").replace("'", "''"));

		if (type.contains("journals") || type.contains("phd")) {
			if (type.contains("journals")) {
				type = "journals";
			} else {
				type = "phd";
			}
			sql = "insert into books (title, type, author, publicdate, price, imgsrc)" + "Values ('" + map.get("title")
					+ "','" + type + "', '" + map.get("author") + "', '" + map.get("year") + "', '" + map.get("price")
					+ "', '" + map.get("imgsrc") + "')";
		} else if (type.contains("conf")) {
			if (map.get("booktitle") == null) {
				map.put("booktitle", "unknown");
			}
			sql = "insert into books (title, type, author, publicdate, price, imgsrc, venue)" + "Values ('"
					+ map.get("title") + "','conf', '" + map.get("author") + "', '" + map.get("year") + "', '"
					+ map.get("price") + "', '" + map.get("imgsrc") + "', '" + map.get("booktitle") + "')";

		}
		System.out.println(sql);
		DB.generalInsert(sql, connection);
	}

	public List<Map<String, String>> convertJsonToList(String json) throws JSONException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map;
		String jsonString = json;
		JSONArray resultArray = new JSONArray(jsonString);
		int size = resultArray.length();
		for (int i = 0; i < size; i++) {
			JSONObject temp = resultArray.getJSONObject(i);
			Iterator<?> keys = temp.keys();
			map = new HashMap<String, String>();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String value = temp.getString(key);
				map.put(key, value);
			}
			list.add(map);
		}
		return list;
	}

	public static void main(String argv[]) throws JSONException {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Dblp dblp = new Dblp("dblp.xml");
		//dblp.getAllArticles();
		dblp.tripleTables();
		
	}
}
