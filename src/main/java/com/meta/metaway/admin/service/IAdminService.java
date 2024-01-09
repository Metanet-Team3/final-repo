package com.meta.metaway.admin.service;

import java.util.List;

import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;

public interface IAdminService {
	List<AdminOrderDTO> findAllOrderList(int page);
	int selectTotalOrdersCount();
	int selectWaitingOrdersCount();
	int selectCompleteOrdersCount();
	List<AdminOrderDTO> searchOrderListByKeyword(String keyword, Integer orderState, String orderDate, int page);
	void updateCancleOrder(long orderId);
	void updateCompleteOrder(long orderId);
	int getOrderId(long orderId);
	AdminOrderDetailDTO selectOneOrderList(long orderId);
	List<AdminStaffDTO> selectAllCodiList();
	List<AdminStaffDTO> selectAllDriverList();
	int getStaffId(long staffId);
}
