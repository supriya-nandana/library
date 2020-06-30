package com.example.demo.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	
	ObjectMapper objectMapper;
	
	
	@Mock
	UserService userService;
	
	@InjectMocks
	UserController userController;
	
	MockMvc mockMvc;

	UserDto userDto;
	
	LoginDto loginDto;
	
	
	@BeforeEach
	public void setUp()
	{
		objectMapper=new ObjectMapper();
		mockMvc=MockMvcBuilders.standaloneSetup(userController).build();
		
		userDto=new UserDto();
		userDto.setUserName("manasareddy");
		userDto.setPassword("reddy");
		userDto.setMobileNumber("9440293947");
		userDto.setEmailId("chandu@gmail.com");
		userDto.setLocation("kothagattu");
		
		loginDto=new LoginDto();
		loginDto.setPassword("1234");
		loginDto.setUserName("manasa");
		
	}

	@Test
	public void registerTest() throws Exception{
		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(userDto)))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$", Matchers.is("user added successfully")));
		        
	}
	
	@Test
	public void loginTest() throws Exception
	{
		//given
		when(userService.authenticateUser("manasa", "1234")).thenReturn(true);
		
		//when and then
		mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(loginDto)))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$", Matchers.is("logged in successfully")));
		
		verify(userService).authenticateUser("manasa", "1234");
	}
	
	@Test
	public void loginTest1() throws Exception
	{
		//given
		when(userService.authenticateUser("manasa", "1234")).thenReturn(false);
		
		//when and then
		mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(loginDto)))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$", Matchers.is("Credentials are incorrect")));
		
		verify(userService).authenticateUser("manasa" ,"1234");
	}

	

}
