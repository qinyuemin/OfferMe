package com.offerme.server.service;

import com.offerme.server.model.business.search.SearchKeyword;
import com.offerme.server.model.business.search.SearchResulat;

public interface SearchSrvc {

	SearchResulat getSearchResulat(SearchKeyword searchKeyword);
}
