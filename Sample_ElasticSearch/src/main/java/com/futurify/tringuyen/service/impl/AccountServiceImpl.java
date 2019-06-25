package com.futurify.tringuyen.service.impl;

import static com.futurify.tringuyen.util.Constant.INDEX;
import static com.futurify.tringuyen.util.Constant.TYPE_ACCOUNT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurify.tringuyen.model.Account;
import com.futurify.tringuyen.service.AccountService;

@Service(value = "accService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private RestHighLevelClient elasticSearch;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public List<Account> findAll() throws Exception {
		SearchRequest searchRequest = buildSearchRequest(INDEX, TYPE_ACCOUNT);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = elasticSearch.search(searchRequest, RequestOptions.DEFAULT);

		return getSearchResult(searchResponse);
	}

	@Override
	public Account findById(String id) throws Exception {
		GetRequest getRequest = new GetRequest(INDEX, TYPE_ACCOUNT, id);

		GetResponse getResponse = elasticSearch.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();

		return convertMapToProfileDocument(resultMap);
	}

	@Override
	public Account save(Account m) throws Exception {
		UUID uuid = UUID.randomUUID();
		m.setId(uuid.toString());

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE_ACCOUNT, m.getId()).source(convertProfileDocumentToMap(m));
		elasticSearch.index(indexRequest, RequestOptions.DEFAULT);

		return m;
	}

	@Override
	public String delete(String id) throws Exception {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE_ACCOUNT, id);
		DeleteResponse response = elasticSearch.delete(deleteRequest, RequestOptions.DEFAULT);

		return response.getResult().name();
	}

	private Account convertMapToProfileDocument(Map<String, Object> map) {
		return objectMapper.convertValue(map, Account.class);
	}

	private Map<String, Object> convertProfileDocumentToMap(Account profileDocument) {
		return objectMapper.convertValue(profileDocument, Map.class);
	}

	private List<Account> getSearchResult(SearchResponse response) {
		SearchHit[] searchHit = response.getHits().getHits();

		List<Account> profileDocuments = new ArrayList<>();

		for (SearchHit hit : searchHit) {
			profileDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), Account.class));
		}

		return profileDocuments;
	}

	private SearchRequest buildSearchRequest(String index, String type) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.types(type);

		return searchRequest;
	}

}