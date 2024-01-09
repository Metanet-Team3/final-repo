package com.meta.metaway.admin.dto;

import java.time.LocalDateTime;

import com.meta.metaway.admin.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderDTO {
	private long orderId;
	private String orderName;
	private String orderAddress;
	private String orderContent;
	private String productModel;
	private String productName;
	private int orderCount;
	private int orderPrice;
	private LocalDateTime orderDate;
	private int orderState;
	
	private int page;
	
	//날짜 포맷
    public String getOrderDate() {
        return DateUtil.formatLocalDateTime(orderDate);
    }
    
    public String getOrderState() {
    	if (orderState == 0) {
            return "주문 대기";
        } else if (orderState == 1) {
            return "배정 완료";
        } else if (orderState == 2) {
            return "주문 취소";
        } else if (orderState == 3) {
            return "배정 완료";
        } else {
            // 다른 상태에 대한 처리를 원한다면 추가적으로 처리할 수 있습니다.
            return "알 수 없음";
        }
    }
}
