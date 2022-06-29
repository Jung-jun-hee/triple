package com.triple.homework.events;


import org.junit.Test;

import com.triple.homework.model.UserInfo;

public class EventTest {
	
	@Test
	public void builder() {
		UserInfo event = new UserInfo();
		String userId = "TestJun";
		event.setUserId("userId");
		System.out.println(event.getUserId().equals(userId));
	}
}
