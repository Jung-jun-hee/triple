package com.triple.homework.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.triple.homework.model.UserInfo;
import com.triple.homework.service.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;
	
	// 통신 테스트
	@GetMapping("/api/test")
	public List<UserInfo> getAllUsers(){
		return eventService.getUsers();
	}
	
	// 사용자 정보 조회
	@GetMapping("/getUserInfo/{userId}/{placeId}")
	public Map<String,Object> getUserInfo(@PathVariable("userId") String userId,
									  @PathVariable("placeId") String placeId) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userInfo", eventService.getUserInfo(userId,placeId));
		map.put("userPoint", eventService.getUserPoint(userId));
		return map;
	}
	
	// 사용자 리뷰 등록
	@PostMapping("/regReview")
	public void regReview(@RequestBody UserInfo userInfo) {
		
		int point;
		// 리뷰내용과 사진이 '한장'이라도 있으면 점수 2점 부여 , 둘 중 하나만 등록하면 1점 부여 
		if(userInfo.getContent() != "" && userInfo.getAttachedPhotoIds() != "") {
			point = Integer.parseInt(userInfo.getUserPoint()) + 2;
		}else {
			point = Integer.parseInt(userInfo.getUserPoint()) + 1;
		}
		
		// 신규 reviewId의 UUID 생성
		String uuid = UUID.randomUUID().toString();
		
		// 리뷰 등록 파라미터 세팅
		userInfo.setReviewId(uuid);
		userInfo.setType("REVIEW");
		userInfo.setAction("ADD");
		
		// if 해당 장소 리뷰가 이미 있을 경우 보너스 point 증가 없음 
		// 최초 리뷰 검사를 위해 placeId 에 해당하는 리뷰 조회
		List<UserInfo> reviewCnt = eventService.getReviewId(userInfo);
		
		// 등록하는 리뷰가 최초 리뷰인지 유효성 검사
		if(reviewCnt.get(0).getReviewCount() == 0) {
			// 최초 리뷰라면 보너스 점수 2점 부여
			point += 2;
			userInfo.setUserPoint(Integer.toString(point));
			//사용자 리뷰 등록 서비스 호출
			eventService.insertReview(userInfo);
			//사용자 포인트 반영 서비스 호출
			eventService.updatePoint(userInfo);
			//사용자 포인트 이력 등록 서비스 호출
			eventService.insertHistory(userInfo);
			return;
		}else if(reviewCnt.get(0).getReviewCount() > 0){ // 최초 리뷰가 아니면 추가 포인트 없이 로직 수행
			userInfo.setUserPoint(Integer.toString(point));
			eventService.insertReview(userInfo);
			eventService.updatePoint(userInfo);
			eventService.insertHistory(userInfo);
			return;
		}
		
		//return map;
	}
	
	// 사용자 리뷰 수정
	@PutMapping("/modifyReview")
	public void modifyReview(@RequestBody UserInfo userInfo) {
		userInfo.setAction("MOD");
		eventService.updateReview(userInfo);
		// 수정을 통해 추가/차감된 포인트가 없다면 포인트 이력은 남기지 않는다.
		if(userInfo.getDuplePoint() != userInfo.getUserPoint()) {
			//사용자 포인트 이력 등록 서비스 호출
			eventService.insertHistory(userInfo);
			//사용자 포인트 반영 서비스 호출
			eventService.updatePoint(userInfo);
		}
		
		
	}
	
	// 사용자 리뷰 삭제
	@DeleteMapping("/deleteReview")
	public void deleteReview(@RequestBody UserInfo userInfo) {
		userInfo.setAction("DELETE");
		//사용자 리뷰 삭제 서비스 호출
		eventService.deleteReview(userInfo);
		
		
	}
}
