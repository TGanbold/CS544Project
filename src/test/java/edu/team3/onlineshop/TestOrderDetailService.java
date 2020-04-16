package edu.team3.onlineshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import edu.team3.onlineshop.dao.OrderDetailRepository;
import edu.team3.onlineshop.domain.OrderDetail;
import edu.team3.onlineshop.domain.Product;
import edu.team3.onlineshop.domain.ProductCategory;
import edu.team3.onlineshop.service.OrderDetailService;
import edu.team3.onlineshop.service.impl.OrderDetailServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestOrderDetailService {

	@Mock
	private OrderDetailRepository orderDetailRepository;

	@InjectMocks
	private OrderDetailService orderDetailService = new OrderDetailServiceImpl();

	private List<Product> products = Arrays.asList(
			new Product(1L, "ABC", "ABC Summary", "ABC Description", 20, 1200, 10, true, null, null,
					new ProductCategory("ABC Category")),
			new Product(2L, "DEF", "DEF Summary", "DEF Description", 5, 200, 0, false, null, null,
					new ProductCategory("DEF Category")),
			new Product(3L, "GHI", "GHI Summary", "GHI Description", 10, 1500, 5, true, null, null,
					new ProductCategory("GHI Category")));

	private List<OrderDetail> orderDetails = Arrays.asList(
			new OrderDetail(1L, "ABC", products.get(0), 20, 10, LocalDate.of(2019, 2, 20), 1200, 25),
			new OrderDetail(2L, "DEF", products.get(1), 200, 50, LocalDate.of(2019, 3, 5), 450, 10),
			new OrderDetail(3L, "GHI", products.get(2), 17, 5, LocalDate.of(2019, 4, 19), 200, 20));

	@BeforeEach
	void setMockOutput() {
		when(orderDetailRepository.findAll()).thenReturn(orderDetails);

	}

	@Test
	void testGetOrderDetails() {
		assertEquals(orderDetails, orderDetailService.getAll());
		verify(orderDetailRepository).findAll();
	}

	@Test
	void testGetOrderDetail() {
		when(orderDetailRepository.findById(orderDetails.get(1).getId())).thenReturn(Optional.of(orderDetails.get(1)));
		assertEquals(orderDetails.get(1), orderDetailService.get(orderDetails.get(1).getId()).orElse(null));
		verify(orderDetailRepository).findById(orderDetails.get(1).getId());
	}

	@Test
	void testGetOrderDetailById() {
		when(orderDetailRepository.findById(orderDetails.get(0).getId())).thenReturn(Optional.of(orderDetails.get(0)));
		assertEquals(orderDetails.get(0), orderDetailService.get(orderDetails.get(0).getId()).orElse(null));
	}

}
