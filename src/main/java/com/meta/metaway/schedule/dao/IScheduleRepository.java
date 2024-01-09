package com.meta.metaway.schedule.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.schedule.model.Schedule;

@Repository
@Mapper
public interface IScheduleRepository {
	void insertSchedule(Schedule schedule);
	int getNextMaxScheduleId();
	void deleteSchedule(long orderId, long staffId);
}
