package com.ibtech.business.abstracts;

import java.util.List;

import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.entities.User;

public interface UserService {
	public DataResult<List<User>> getAll();
	public DataResult<User> getById(long id);
	public DataResult<User> getByUserName(String name);
	
	public DataResult<User> check(User user);
	public DataResult<Long> add(User user);
}
