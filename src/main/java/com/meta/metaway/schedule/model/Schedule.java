package com.meta.metaway.schedule.model;

import java.time.LocalDateTime;

import com.meta.metaway.admin.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
	private long scheduleId;
	private LocalDateTime visitDate; //null
	private int visitCycle;  //null
	private int visitType;  //default 0
	private int visitState; //default 0
	private long staffId;
	private long userId;
	private long orderId;
	
	public String getVisitDate() {
		return  DateUtil.formatLocalDateTime(visitDate);
	}
	
	
}
