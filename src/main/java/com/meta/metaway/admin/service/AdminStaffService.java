package com.meta.metaway.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.dao.IAdminStaffRepository;
import com.meta.metaway.admin.dto.AdminStaffDTO;

@Service
public class AdminStaffService implements IAdminStaffService{
	
	@Autowired
	IAdminStaffRepository adminStaffRepository;
	
	@Override
	public List<AdminStaffDTO> findAllStaffList(int page) {
		int start =(page-1)*10 +1;
		return adminStaffRepository.selectAllStaff(start, start+9);
	}

	@Override
	public int selectTotalStaffCount() {
		return adminStaffRepository.selectTotalStaffCount();
	}

	@Override
	public List<AdminStaffDTO> searchAllStaff(String keyword, String authorityName, int page) {
		int start = (page-1)*10 + 1;
		return adminStaffRepository.searchAllStaff("%"+keyword+"%", authorityName, start, start+9);
	}

	@Override
	public void deleteStaff(long staffId) {
		adminStaffRepository.deleteStaff(staffId);
	}

	@Override
	public int getStaffId(long staffId) {
		return adminStaffRepository.getStaffId(staffId);
	}
	
}
