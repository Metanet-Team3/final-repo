package com.meta.metaway.schedule.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.metaway.schedule.service.IScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	IScheduleService scheduleService;

	@PostMapping("/driver/{orderId}/{staffId}")
	public ResponseEntity<String> assignDriver(@PathVariable long orderId, @PathVariable long staffId, @RequestBody Map<String, Long> schedule) {
		System.out.println("기사배정 컨트롤러");
		System.out.println(schedule.get("userId"));
		try {
			long userId = schedule.get("userId");
			System.out.println(orderId+","+staffId+","+userId);
			scheduleService.assignStaff(orderId, staffId, userId);
			return new ResponseEntity<>("기사 배정 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/codi/{orderId}/{staffId}")
	public ResponseEntity<String> assignCodi(@PathVariable long orderId, @PathVariable long staffId, @RequestBody Map<String, Long> schedule) {
		System.out.println("코디배정 컨트롤러");
		System.out.println(schedule.get("userId"));
		try {
			long userId = schedule.get("userId");
			System.out.println(orderId+","+staffId+","+userId);
			scheduleService.assignStaff(orderId, staffId, userId);
			return new ResponseEntity<>("코디 배정 성공", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{orderId}/{staffId}")
	public ResponseEntity<String> cancleScheduleDriver(@PathVariable long orderId ,@PathVariable long staffId) {
		System.out.println("배정 삭제 컨트롤러");
		try {
			scheduleService.deleteSchedule(orderId, staffId);
			return new ResponseEntity<String>("배정 삭제 성공", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}


}
