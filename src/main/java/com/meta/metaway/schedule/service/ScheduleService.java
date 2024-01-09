package com.meta.metaway.schedule.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.metaway.schedule.dao.IScheduleRepository;
import com.meta.metaway.schedule.model.Schedule;

@Service
public class ScheduleService implements IScheduleService{
	
	@Autowired 
	IScheduleRepository scheduleRepository;


	@Override
    @Transactional
	public void assignStaff(long orderId, long staffId ,long userId) {
		try {
			System.out.println("insert start");
			System.out.println(userId);
			System.out.println(scheduleRepository.getNextMaxScheduleId());
			Schedule schedule = new Schedule();
			System.out.println(schedule.toString());
			schedule.setScheduleId(scheduleRepository.getNextMaxScheduleId());
			System.out.println(schedule.getScheduleId());
			schedule.setVisitDate(LocalDateTime.now());
			System.out.println("setVisitDate " + schedule.getVisitDate());
			schedule.setVisitCycle(1);
			schedule.setVisitState(1);
			schedule.setVisitType(1);
			schedule.setUserId(userId);
			schedule.setOrderId(orderId);
	        schedule.setStaffId(staffId);
	        System.out.println(schedule.toString());
	        scheduleRepository.insertSchedule(schedule);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}


	@Override
	public void deleteSchedule(long orderId, long staffId) {
		scheduleRepository.deleteSchedule(orderId, staffId);
	}


	

}
