package com.meta.metaway.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;

@Repository
@Mapper
public interface IAdminRepository {
	List<AdminOrderDTO> findAllOrderList(@Param("start") Integer start, @Param("end") Integer end);
	int selectTotalOrdersCount();
	int selectWaitingOrdersCount();
	int selectCompleteOrdersCount();
	int getOrderId(long orderId);
	List<AdminOrderDTO> searchOrderListByKeyword(@Param("keyword")  String keyword, 
											@Param("orderState") Integer orderState, 
											@Param("orderDate") String orderDate,
											@Param("start") int start, @Param("end") int end);
	void updateCancleOrder(long orderId);
	void updateCompleteOrder(long orderId);
	AdminOrderDetailDTO selectOneOrderList(long orderId);
	AdminStaffDTO selectOneStaffList(long staffId);
	List<AdminStaffDTO> selectAllCodiList();
	List<AdminStaffDTO> selectAllDriverList();
	int getStaffId(long staffId);
	
	}
