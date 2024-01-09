package com.meta.metaway.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.service.IAdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	IAdminService adminService;

	@GetMapping("/orderlist/{page}")
	public String orderList(@PathVariable int page, HttpSession session, Model model) {
		System.out.println("주문관리페이지");
		session.setAttribute("page", page);
		try {
			List<AdminOrderDTO> orderList = adminService.findAllOrderList(page);
			model.addAttribute("orderList", orderList);
			int totOrders = adminService.selectTotalOrdersCount();
			model.addAttribute("totOrders", totOrders);
			int waitOrders = adminService.selectWaitingOrdersCount();
			model.addAttribute("waitOrders", waitOrders);
			int completeOrders = adminService.selectCompleteOrdersCount();
			model.addAttribute("completeOrders", completeOrders);
			int cancelledOrders = totOrders - (waitOrders + completeOrders);
			model.addAttribute("cancelledOrders", cancelledOrders);
			int totalPage = 0;
			if (totOrders > 0) {
				totalPage = (int) Math.ceil(totOrders / 10.0);
			}
			int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
			int nowPageBlock = (int) Math.ceil(page / 10.0);
			int startPage = (nowPageBlock - 1) * 10 + 1;
			int endPage = 0;
			if (totalPage > nowPageBlock * 10) {
				endPage = nowPageBlock * 10;
			} else {
				endPage = totalPage;
			}
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("nowPage", page);
			model.addAttribute("totalPageBlock", totalPageBlock);
			model.addAttribute("nowPageBlock", nowPageBlock);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
		} catch (Exception e) {
			e.printStackTrace();
//			model.addAttribute("error", "An error occurred.");
//            return "error";
		}
		return "admin/ordermanager";
	}

	@GetMapping("/searchorder/{page}")
	public String searchOrder(@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false) Integer orderState, @RequestParam(required = false) String orderDate,
			@PathVariable int page, HttpSession session, Model model) {
		System.out.println("검색결과 페이지");
		session.setAttribute("page", page);
		try {
			List<AdminOrderDTO> searchList = adminService.searchOrderListByKeyword(keyword, orderState, orderDate, page);
			model.addAttribute("orderList", searchList);
			int totOrders = adminService.selectTotalOrdersCount();
			model.addAttribute("totOrders", totOrders);
			int waitOrders = adminService.selectWaitingOrdersCount();
			model.addAttribute("waitOrders", waitOrders);
			int completeOrders = adminService.selectCompleteOrdersCount();
			model.addAttribute("completeOrders", completeOrders);
			int cancelledOrders = totOrders - (waitOrders + completeOrders);
			model.addAttribute("cancelledOrders", cancelledOrders);
			System.out.println("총 주문수 " + totOrders);
			int totalPage = 0;
			if (totOrders > 0) {
				totalPage = (int) Math.ceil(totOrders / 10.0);
			}
			int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
			int nowPageBlock = (int) Math.ceil(page / 10.0);
			int startPage = (nowPageBlock - 1) * 10 + 1;
			int endPage = 0;
			if (totalPage > nowPageBlock * 10) {
				endPage = nowPageBlock * 10;
			} else {
				endPage = totalPage;
			}
			model.addAttribute("keyword", keyword);
			model.addAttribute("orderState", orderState);
			model.addAttribute("orderDate", orderDate);
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("nowPage", page);
			model.addAttribute("totalPageBlock", totalPageBlock);
			model.addAttribute("nowPageBlock", nowPageBlock);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
		} catch (Exception e) {
			e.printStackTrace();
//			model.addAttribute("error", "An error occurred.");
//            return "error";
		}
		return "admin/ordermanager";
	}

	@PostMapping("/deleteorder/{orderId}")
	public String deleteOrders(@PathVariable long orderId, RedirectAttributes redirectAttr, Model model) {
		System.out.println("주문 취소 컨트롤러");
		adminService.updateCancleOrder(orderId);
		model.addAttribute(orderId);
	    redirectAttr.addFlashAttribute("message", orderId+" 번 주문이 취소되었습니다.");
	    return "redirect:/admin/orderlist/1";
	}
	
	@GetMapping("/assign/{orderId}")
	public String assignOrder(@PathVariable long orderId, Model model) {
		System.out.println("주문 상세 페이지");
		orderId = adminService.getOrderId(orderId);
		System.out.println("주문번호: " + orderId);
		
		AdminOrderDetailDTO orderDetails = adminService.selectOneOrderList(orderId);
		model.addAttribute("orderDetail", orderDetails);
		
		List<AdminStaffDTO> driverList = adminService.selectAllDriverList();
		model.addAttribute("driverList", driverList);
		
		List<AdminStaffDTO> codiList = adminService.selectAllCodiList();
		model.addAttribute("codiList", codiList);
		
		System.out.println(orderDetails.toString());
		return "admin/orderdetail";
	}
	

}
