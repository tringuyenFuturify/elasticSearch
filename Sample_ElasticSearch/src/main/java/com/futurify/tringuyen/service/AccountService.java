package com.futurify.tringuyen.service;

import java.util.List;

import com.futurify.tringuyen.model.Account;

public interface AccountService {

	List<Account> findAll() throws Exception;
	
	Account findById(String id) throws Exception;
	
	Account save(Account m) throws Exception;
	
	String delete(String id) throws Exception;
	
}