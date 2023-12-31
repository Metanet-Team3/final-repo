package com.meta.metaway.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminStaffDTO {
	private long staffId;
	private long userId;
	private String userEmail;
	private String userPhone;
	private String userName;
	private String userAddress;
	private String workPlace;
	private String authorityName;
}