package com.meta.metaway.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;

@Service
public class AdminService implements IAdminService {
	
	@Autowired
	IAdminRepository adminRepository;
	
	@Override
	public List<AdminOrderDTO> findAllOrderList(int page) {
		int start =(page-1)*10 +1;
		return adminRepository.findAllOrderList(start, start+9);
	}

	@Override
	public int selectTotalOrdersCount() {
		return adminRepository.selectTotalOrdersCount();
	}

	@Override
	public int selectWaitingOrdersCount() {
		return adminRepository.selectWaitingOrdersCount();
	}

	@Override
	public List<AdminOrderDTO> searchOrderListByKeyword(String keyword, Integer orderState, String orderDate, int page) {
		int start = (page-1)*10 + 1;
		return adminRepository.searchOrderListByKeyword("%"+keyword+"%", orderState, orderDate, start, start+9);
	}

	@Override
	public void updateCancleOrder(long orderId) {
		adminRepository.updateCancleOrder(orderId);
	}

	@Override
	public int selectCompleteOrdersCount() {
		return adminRepository.selectCompleteOrdersCount();
	}

	@Override
	public int getOrderId(long orderId) {
		return adminRepository.getOrderId(orderId);
	}

	@Override
	public AdminOrderDetailDTO selectOneOrderList(long orderId) {
		return adminRepository.selectOneOrderList(orderId);
	}

	@Override
	public void updateCompleteOrder(long orderId) {
		adminRepository.updateCompleteOrder(orderId);
	}

	@Override
	public List<AdminStaffDTO> selectAllCodiList() {
		return adminRepository.selectAllCodiList();
	}

	@Override
	public List<AdminStaffDTO> selectAllDriverList() {
		return adminRepository.selectAllDriverList();
	}

	@Override
	public int getStaffId(long staffId) {
		return adminRepository.getStaffId(staffId);
	}
	
}
