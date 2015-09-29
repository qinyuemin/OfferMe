package com.offerme.client.service.search;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchResulat implements Serializable {

	private static final long serialVersionUID = 8414552502758535008L;

	private ArrayList<SearchResulatItem> searchResulat = new ArrayList<SearchResulatItem>();
	private ArrayList<String> resualtIndexList = new ArrayList<String>();

	public void clearResulat() {
		searchResulat = new ArrayList<SearchResulatItem>();
		resualtIndexList = new ArrayList<String>();
	}

	public void addItem(SearchResulatItem item) {
		searchResulat.add(item);
		resualtIndexList.add(item.getOfferID());
	}

	public int getSize() {
		return searchResulat.size();
	}

	public SearchResulatItem getItem(int position) {
		return searchResulat.get(position);
	}

	public boolean addSearchResualt(SearchResulat resualt) {
		for (int count = 0; count < resualt.getSize(); count++) {
			if (!resualtIndexList.contains(resualt.getItem(count).getOfferID())) {
				addItem(resualt.getItem(count));
			}
		}
		return true;
	}
}
