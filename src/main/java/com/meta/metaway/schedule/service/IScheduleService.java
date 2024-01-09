package com.meta.metaway.schedule.service;

public interface IScheduleService {
	void assignStaff(long orderId, long staffId, long userId);
	void deleteSchedule(long orderId, long staffId);
}
