package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.InvalidCredentialsException;
import com.example.demo.model.User;
import com.example.demo.serviceimpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
    User user;
	
	@Mock
	UserDao userDao;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	UserDto userDto;
	
	@BeforeEach
	public void setUp()
	{
		
		userDto=new UserDto();
		userDto.setUserName("manasa");
		userDto.setPassword("1234");
		userDto.setMobileNumber("9440293947");
		userDto.setEmailId("chandu@gmail.com");
		userDto.setLocation("kothagattu");
	}
	@Test
	public void saveUser()
	{
		//given
		when(userDao.save(any(User.class))).thenReturn(any(User.class));
		
		//when
		userServiceImpl.addUser(userDto);
		
		//then
		verify(userDao).save(any(User.class));
		
	}
	
	@Test
	public void authenticateTest1() throws InvalidCredentialsException {
		//given
		User user=new User();
		user.setUserName("manasa");
		user.setPassword("1234");
		
		
		when(userDao.findByUserNameAndPassword("manasa","1234")).thenReturn(user);
				
		
		//when
		Boolean isExists = userServiceImpl.authenticateUser("manasa", "1234");
		
		//then
		verify(userDao).findByUserNameAndPassword("manasa","1234");
		assertTrue(isExists);
	}
	
	@Test
	public void authenticateTest2() {
		//given
		User user = new User();
		user.setUserName("manasa");
		user.setPassword("1234");	
		
		when(userDao.findByUserNameAndPassword("manasa","1234")).thenReturn(null);
		
		//when
		
		//then
		assertThrows(InvalidCredentialsException.class, () -> userServiceImpl.authenticateUser("manasa", "1234"));
	}
	
	

}
