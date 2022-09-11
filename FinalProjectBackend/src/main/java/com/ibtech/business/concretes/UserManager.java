package com.ibtech.business.concretes;

import java.util.List;

import com.ibtech.business.abstracts.UserService;
import com.ibtech.business.contants.message.UserResultMessage;
import com.ibtech.core.utilities.result.DataResult;
import com.ibtech.core.utilities.result.ErrorDataResult;
import com.ibtech.core.utilities.result.SuccessDataResult;
import com.ibtech.entities.User;
import com.ibtech.repository.UserRepository;

public class UserManager implements UserService{

	private UserRepository userRepository;
	
	public UserManager(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public DataResult<List<User>> getAll() {
		try {
			String sql = "Select * from users";
			List<User> userList = userRepository.listAll(sql);
			return new SuccessDataResult<List<User>>(userList);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<List<User>>(UserResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<User> getById(long id) {
		try {
			String sql = "Select * from users where id = ?";
			User user = userRepository.find(sql,id);
			return user != null ? new SuccessDataResult<User>(user) : new ErrorDataResult<User>(UserResultMessage.UserNotFound);
		}catch(Exception e){
			e.printStackTrace();
			return new ErrorDataResult<User>(UserResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<User> getByUserName(String name) {
		try {
			String sql = "Select * from users where name = ?";
			User user = userRepository.findByName(sql,name);
			return user != null ? new SuccessDataResult<User>(user) : new ErrorDataResult<User>(UserResultMessage.UserNotFound);
		}catch(Exception e){
			e.printStackTrace();
			return new ErrorDataResult<User>(UserResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<Long> add(User user) {
		try {
			User dbUser = getByUserName(user.getName()).getData();
			if(dbUser != null) {
				return new ErrorDataResult<Long>(UserResultMessage.UserAlreadyExist);
			}
			Long id = userRepository.add(user);
			return id > 0 ? new SuccessDataResult<Long>(id) : new ErrorDataResult<Long>(UserResultMessage.UserNotRegistered);
		}catch(Exception e) {
			e.printStackTrace();
			return new ErrorDataResult<Long>(UserResultMessage.ErrorMessage);
		}
	}

	@Override
	public DataResult<User> check(User user) {
		try {
			User dbUser = userRepository.check(user);
			return dbUser != null ? new SuccessDataResult<User>(dbUser) : new ErrorDataResult<User>(UserResultMessage.LoginFailed);
		}catch(Exception e){
			e.printStackTrace();
			return new ErrorDataResult<User>(UserResultMessage.ErrorMessage);
		}
	}

}
