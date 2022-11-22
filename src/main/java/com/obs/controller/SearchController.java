package com.obs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import com.obs.dao.DbConnector;
import com.obs.model.BakeryItem;

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
