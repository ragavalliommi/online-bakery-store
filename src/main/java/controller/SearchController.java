package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import dao.DbConnector;
import model.BakeryItem;

public class SearchController extends HttpServlet{
	
	private List<BakeryItem> searchItems(String searchString) throws Exception{
		DbConnector db = DbConnector.getInstance();
		List<BakeryItem> items = new ArrayList<>();
		
		try {
			
			items = db.getItems(searchString);
			
		}catch(Exception E) {
			
			throw new Exception(E);
		}
		
		return items;
	}

}
