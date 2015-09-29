package com.offerme.server.model.business.search;

import java.util.ArrayList;

public class SearchResulat {

	private ArrayList<SearchResulatItem> searchResulat = new ArrayList<SearchResulatItem>();
	private int totalItem = 0;

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public boolean addItem(SearchResulatItem item) {
		searchResulat.add(item);
		return true;
	}

	public int getSize() {
		return searchResulat.size();
	}

	public SearchResulatItem getItem(int position) {
		return searchResulat.get(position);
	}
}
