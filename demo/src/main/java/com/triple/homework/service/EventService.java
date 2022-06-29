package com.triple.homework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.triple.homework.dao.EventRepository;
import com.triple.homework.model.UserInfo;

@Service
@MapperScan("com.triple.homework.dao")
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	public List<UserInfo> getUsers(){
		return eventRepository.getUsers();
	}
	public List<UserInfo> getUserInfo(String userId, String placeId) {
		return eventRepository.getUserInfo(userId, placeId);
	}
	public List<UserInfo> getUserPoint(String userId) {
		return eventRepository.getUserPoint(userId);
	}
	public List<UserInfo> getReviewId(UserInfo userInfo) {
		return eventRepository.getReviewId(userInfo);
	}
	public void insertReview(UserInfo userInfo) {
		this.eventRepository.insertReview(userInfo);
	}
	public void updatePoint(UserInfo userInfo) {
		this.eventRepository.updatePoint(userInfo);
	}
	public void insertHistory(UserInfo userInfo) {
		this.eventRepository.insertHistory(userInfo);
	}
	public void updateReview(UserInfo userInfo) {
		this.eventRepository.updateReview(userInfo);
	}
	public void deleteReview(UserInfo userInfo) {
		this.eventRepository.deleteReview(userInfo);
		
	}
	
}
