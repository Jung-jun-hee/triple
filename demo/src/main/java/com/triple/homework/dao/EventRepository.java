package com.triple.homework.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.triple.homework.model.UserInfo;

@Repository
@Mapper
public interface EventRepository {
	
	List<UserInfo> getUsers();
	List<UserInfo> getUserInfo(String userId, String placeId);
	List<UserInfo> getUserPoint(String userId);
	List<UserInfo> getReviewId(UserInfo userInfo);
	void insertReview(UserInfo userInfo);
	void updatePoint(UserInfo userInfo);
	void insertHistory(UserInfo userInfo);
	void updateReview(UserInfo userInfo);
	void deleteReview(UserInfo userInfo);
}
