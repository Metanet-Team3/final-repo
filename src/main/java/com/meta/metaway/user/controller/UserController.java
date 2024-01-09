//package com.meta.metaway.user.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.meta.metaway.jwt.JWTUtil;
//import com.meta.metaway.user.model.Basket;
//import com.meta.metaway.user.model.User;
//import com.meta.metaway.user.service.IUserService;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//	@Autowired
//	IUserService userService;
//    
//	@Autowired
//    private JWTUtil jwtUtil;
//	

//  
//    
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token) {
//        String username = jwtUtil.getUsername(token);
//        
//        User existingUser = userService.getUserByUsername(username);
//        if (existingUser != null) {
//            userService.deleteUserByAccount(username);
//            return ResponseEntity.ok("사용자 삭제 성공");
//        } else {
//            return ResponseEntity.badRequest().body("삭제 실패");
//        }
//    }
//    
//    // 아직 미완성
//    @PostMapping("/check-password")
//    public ResponseEntity<String> checkPassword(@RequestHeader("Authorization") String token, @RequestBody String password) {
//        String username = jwtUtil.getUsername(token);
//
//        try {
//            boolean passwordMatches = userService.checkPasswordByAccount(username, password);
//            if (passwordMatches) {
//                return ResponseEntity.ok("비밀번호 일치");
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 불일치");
//            }
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body("오류: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
//
//    	
//        return ResponseEntity.ok("로그아웃 성공");
//    }
//    
//    @PostMapping("/basket/{contractId}")
//    public ResponseEntity<String> addProductToBasket(
//            @RequestHeader("Authorization") String token,
//            @PathVariable Long contractId,
//            @RequestBody Basket basket) {
//
//        try {
//            String account = jwtUtil.getUsername(token);
//            userService.addProductToBasket(account, contractId, basket);
//            return ResponseEntity.ok("제품이 장바구니에 추가되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("장바구니에 제품을 추가하는데 실패했습니다: " + e.getMessage());
//        }
//    }
//    
//    
//
//
//}
