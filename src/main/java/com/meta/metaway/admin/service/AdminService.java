package com.meta.metaway.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.dto.SoldRankDTO;

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
		// TODO Auto-generated method stub
		return adminRepository.selectAllCodiList();
	}

	@Override
	public List<AdminStaffDTO> selectAllDriverList() {
		// TODO Auto-generated method stub
		return adminRepository.selectAllDriverList();
	}
	
	@Override
	public int getStaffId(long staffId) {
		return adminRepository.getStaffId(staffId);
	}

	@Override
	public List<AdminScheduleStaffDTO> selectListScheduleStaff(long orderId) {
		// TODO Auto-generated method stub
		return adminRepository.selectListScheduleStaff(orderId);
	}
	
	@Override
	public void deleteSchedule(long orderId, long staffId) {
		adminRepository.deleteSchedule(orderId, staffId);
		adminRepository.updateStaffStatus(staffId);
	}

	@Override
	public List<AdminOrderDetailDTO> selectAllOrderList(long orderId) {
		return adminRepository.selectAllOrderList(orderId);
	}
	
	@Override
    public List<SoldRankDTO> getSoldRankProductWithoutImage(int orderState) {
        return adminRepository.getSoldRankProductWithoutImage(orderState);
    }
	
    @Override
    public List<SoldRankDTO> getSoldRankProductWithImage(int orderState) {
        return adminRepository.getSoldRankProductWithImage(orderState);
    }
	
	
}
