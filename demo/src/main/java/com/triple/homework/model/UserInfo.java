package com.triple.homework.model;

import java.sql.Date;

public class UserInfo {
	
	private String type;		// 리뷰 타입유형
	private String action;		// 리뷰 Event action
	private String reviewId;	// 리뷰 Id (pk)
	private String content;		// 리뷰 내용
	private String attachedPhotoIds;// 첨부 이미지 Id
	private String userId;		// 작성자 id
	private String placeId;		// 작성된 장소 id
	private String userPoint; 	// 사용자 총 포인트
	private Date   regDate; 	// 리뷰 등록일
	private Date   modDate; 	// 리뷰 수정일
	private int	   reviewCount; // 리뷰 카운트
	private Date   pointHistory;// 포인트 적립 이력
	private String duplePoint;	// 포인트 비교
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttachedPhotoIds() {
		return attachedPhotoIds;
	}
	public void setAttachedPhotoIds(String attachedPhotoIds) {
		this.attachedPhotoIds = attachedPhotoIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(String userPoint) {
		this.userPoint = userPoint;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public Date getPointHistory() {
		return pointHistory;
	}
	public void setPointHistory(Date pointHistory) {
		this.pointHistory = pointHistory;
	}
	public String getDuplePoint() {
		return duplePoint;
	}
	public void setDuplePoint(String duplePoint) {
		this.duplePoint = duplePoint;
	}
	
}
