package com.offerme.server.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.offerme.server.model.business.search.SearchKeyword;
import com.offerme.server.model.business.search.SearchResulat;
import com.offerme.server.model.business.search.SearchResulatItem;
import com.offerme.server.service.SearchSrvc;
import com.offerme.server.test.core.BaseTestCase;

public class SearchSrvcTest extends BaseTestCase {

	@Autowired
	SearchSrvc searchSrvc;
	
	@Test
	public void testGetSearchResulat() {
		SearchKeyword searchKeyword = new SearchKeyword(37);
		searchKeyword.setCity("上海");
		searchKeyword.setEntreprise("ceo");
		
		SearchResulat res = searchSrvc.getSearchResulat(searchKeyword);
		SearchResulatItem item = res.getItem(0);
		
		assertEquals("qinyuemin", item.getName());
		assertEquals("银行", item.getDomain());
	}
}
