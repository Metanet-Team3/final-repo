package com.meta.metaway.user.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meta.metaway.order.model.Order;
import com.meta.metaway.order.model.OrderDetail;
import com.meta.metaway.product.dao.IProductRepository;
import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.user.dao.IBasketRepository;
import com.meta.metaway.user.dao.IUserRepository;
import com.meta.metaway.user.dto.ChangePasswordDTO;
import com.meta.metaway.user.dto.JoinDTO;
import com.meta.metaway.user.model.Basket;
import com.meta.metaway.user.model.User;

@Service
public class UserService implements IUserService {

	private final IUserRepository userRepository;
	private final IBasketRepository basketRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	IProductRepository productRepository;

	public UserService(IUserRepository userRepository, IBasketRepository basketRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.basketRepository = basketRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public boolean checkIfUserExists(String account) {
		return userRepository.existsByAccount(account);
	}

	@Override
	@Transactional
	public void joinProcess(JoinDTO joinDTO) {
		String account = joinDTO.getAccount();
		boolean isExist = checkIfUserExists(account);

		if (!isExist) {
			User data = new User();
			User role = new User();

			Long id = userRepository.selectUserMaxNo() + 1;
			String password = joinDTO.getPassword();
			String email = joinDTO.getEmail();
			String name = joinDTO.getName();
			String phone = joinDTO.getPhone();
			int age = joinDTO.getAge();
			String zipcode = joinDTO.getZipcode();
			String streetadr = joinDTO.getStreetadr();
			String detailadr = joinDTO.getDetailadr();

			data.setId(id);
			data.setAccount(account);
			data.setPassword(passwordEncoder.encode(password));
			data.setEmail(email);
			data.setName(name);
			data.setPhone(phone);
			data.setAge(age);
			data.setAddress(zipcode + streetadr + detailadr);
			data.setZipcode(zipcode);
			data.setStreetadr(streetadr);
			data.setDetailadr(detailadr);

			role.setId(id);
			if (account.contains("co")) {
				role.setAuthorities("ROLE_CODI");
			} else if (account.contains("dr")) {
				role.setAuthorities("ROLE_DRIVER");
			} else if (account.contains("ad")) {
				role.setAuthorities("ROLE_ADMIN");
			} else {
				role.setAuthorities("ROLE_USER");
			}

			userRepository.insertUser(data);
			userRepository.insertUserRole(role);
		} else {
			// 이미 존재하는 경우
		}
	}

	@Override
	@Transactional
	public User getUserByUsername(String username) {
		System.out.println("서비스 단에서 유저네임" + username);
		return userRepository.findByInfo(username);
	}

	@Override
	public User updateUser(String account, User user) {
		Long id = userRepository.getUserIdByAccount(account);

		if (id != null) {
			User existingUser = userRepository.findByInfo(account);
			if (existingUser != null) {
				String newPassword = user.getPassword();
				if (newPassword != null && !newPassword.isEmpty()) {
					String encryptedPassword = passwordEncoder.encode(newPassword);
					existingUser.setPassword(encryptedPassword);
				}

				existingUser.setName(user.getName());
				existingUser.setEmail(user.getEmail());
				existingUser.setPhone(user.getPhone());
				existingUser.setAge(user.getAge());
				existingUser.setAddress(user.getAddress());

				userRepository.updateUser(existingUser);

				return existingUser;
			} else {
				throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
			}
		} else {
			throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
		}
	}

	@Override
	public User updateUserPassword(String account, User user) {
		Long id = userRepository.getUserIdByAccount(account);
		System.out.println("업데이트 유저 아이디: " + id);
		System.out.println("업데이트 유저 패스워드: " + user.getPassword());

		User existingUser = userRepository.findByInfo(account);
		String newPassword = user.getPassword();
		System.out.println("새로운 비밀번호: " + newPassword);
		if (newPassword != null && !newPassword.isEmpty()) {
			String encryptedPassword = passwordEncoder.encode(newPassword);
			existingUser.setPassword(encryptedPassword);
			System.out.println("암호화 비밀번호: " + encryptedPassword);
			userRepository.updateUser(existingUser);

			return existingUser;
		} else {
			throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
		}
	}

	@Override
	public List<Product> getBasketItemsByUserId(Long userId) {
		List<Product> productList = basketRepository.getBasketItemsByUserId(userId);
		for (Product product : productList) {
			product.setFunctionList(productRepository.getProductKey(product.getProductId()));
			product.setImageFile(productImageString(product.getImageFile()));
		}
		return productList;
	}

	@Override
	public void addProductToBasket(Basket basket) throws Exception {
		basketRepository.addProductToBasket(basket);
	}

	@Override
	public void removeProductFromBasket(Basket basket) throws Exception {
		basketRepository.removeProductFromBasket(basket);
	}

	@Override
	public boolean checkPassword(Long id, String enteredPassword) {
		String storedPassword = userRepository.findPasswordById(id);

		return passwordEncoder.matches(enteredPassword, storedPassword);
	}

	@Override
	@Transactional
	public void deleteUserById(Long id) {

		userRepository.deleteUserById(id);
	}

	@Override
	public List<Order> getOrdersByUserId(Long userId) {
		return userRepository.getOrderByUserId(userId);
	}

	@Override
	public List<OrderDetail> getOrderDetailByUserId(Long userId) {
		return userRepository.getOrderDetailByUserId(userId);
	}

	private String productImageString(String filePath) {
		try {
			InputStream input = new FileInputStream(filePath);

			byte[] byteFile = input.readAllBytes();
			String encodedByte = Base64.getEncoder().encodeToString(byteFile);
			return encodedByte;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public List<Order> getUserMyOrder(long userId) {
		List<Order> orderList = userRepository.getOrderByUserId(userId);
		for (Order order : orderList) {
			order.setContractList(userRepository.getUserContractList(order.getOrderId()));
			for (Contract contract : order.getContractList()) {
				contract.setProduct(userRepository.getUserProduct(contract.getContractId()));
				contract.getProduct().setImageFile(productImageString(contract.getProduct().getImageFile()));
			}
			order.setOrderSize(order.getContractList().size() - 1);
		}
		return orderList;
	}

	@Override
	public Order getUserMyOrderDetail(Order order) {
			order = userRepository.getOrderByOrderId(order.getOrderId());
			order.setContractList(userRepository.getUserContractList(order.getOrderId()));
			for(Contract contract : order.getContractList()) {
				contract.setProduct(userRepository.getUserProduct(contract.getContractId()));
				contract.getProduct().setImageFile(productImageString(contract.getProduct().getImageFile()));
			}
		return order;
	}
	
	@Override
    public int isAccountAvailable(String account) {
		int result = userRepository.checkAccount(account);
        return result;
    }
}
