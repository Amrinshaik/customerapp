package com.capgemini.customerapp.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.customerapp.controller.CustomerController;
import com.capgemini.customerapp.entity.Customer;
import com.capgemini.customerapp.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CustomerControllerTest {
	MockMvc mockMvc;

	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void testAuthenticateCustomer() throws Exception {
		String content = "{\r\n" + "  \"customerId\": 101,\r\n" + "  \"customerPassword\": \"amr\"\r\n" + "}";
		
		Customer customer = new Customer(101, "Amrin", "amr", "amrin@gmail.com", "ou colony");
		
		when(customerService.authentication(Mockito.isA(Customer.class))).thenReturn(customer);
		mockMvc.perform(post("/auth").content(content).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(jsonPath("$.customerEmail").exists())
				.andExpect(jsonPath("$.customerEmail").value("amrin@gmail.com"));
		verify(customerService).authentication(Mockito.isA(Customer.class));
	}

	@Test
	public void testUpdateCustomer() throws Exception {
		String content = "{\r\n" + 
				"  \"customerId\": 101,\r\n" + 
				"  \"customerName\": \"Amrin\",\r\n" + 
				"  \"customerPassword\": \"amr\",\r\n" + 
				"  \"customerEmail\": \"amrin@gmail.com\",\r\n" + 
				"  \"customerAddress\": \"ou colony\"\r\n" + 
				"}";
		Customer customer = new Customer(101, "Amrin", "amr", "amrin@gmail.com", "ou colony");
		when(customerService.editCustomer(Mockito.isA(Customer.class))).thenReturn(customer);
		mockMvc.perform(put("/customer").content(content).contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(jsonPath("$.customerEmail").exists())
				.andExpect(jsonPath("$.customerName").value("amrin"));
	}

	@Test
	public void testGetCustomer() throws Exception {
		String content = "{\r\n" + 
				"  \"customerId\": 101,\r\n" + 
				"  \"customerName\": \"Amrin\",\r\n" + 
				"  \"customerPassword\": \"amr\",\r\n" + 
				"  \"customerEmail\": \"amrin@gmail.com\",\r\n" + 
				"  \"customerAddress\": \"ou colony\"\r\n" + 
				"}";
		Customer customer = new Customer(101, "Amrin", "amr", "amrin@gmail.com", "ou colony");
		when(customerService.getCustomerById(101)).thenReturn(customer);
		mockMvc.perform(get("/customer/101").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(jsonPath("$.customerEmail").exists())
				.andExpect(jsonPath("$.customerName").value("amrin"));
	}

	@Test
	public void testDelete() throws Exception {
		Customer customer = new Customer(101, "Amrin", "amr", "amrin@gmail.com", "ou colony");
		when(customerService.getCustomerById(101)).thenReturn(customer);
		mockMvc.perform(delete("/customer/101").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void testaddCustomer() throws Exception {
		when(customerService.addCustomer(Mockito.isA(Customer.class)))
				.thenReturn(new Customer(101, "Amrin", "amr", "amrin@gmail.com", "ou colony"));

		String content =  "{\r\n" + 
				"  \"customerId\": 101,\r\n" + 
				"  \"customerName\": \"Amrin\",\r\n" + 
				"  \"customerPassword\": \"amr\",\r\n" + 
				"  \"customerEmail\": \"amrin@gmail.com\",\r\n" + 
				"  \"customerAddress\": \"ou colony\"\r\n" + 
				"}";

		mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).content(content)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId").exists())
				.andExpect(jsonPath("$.customerName").exists())
				.andExpect(jsonPath("$.customerPassword").exists())
				.andExpect(jsonPath("$.customerEmail").exists())
				.andExpect(jsonPath("$.customerAddress").exists())
				.andExpect(jsonPath("$.customerId").value(101))
				.andExpect(jsonPath("$.customerName").value("amrin"))
				.andExpect(jsonPath("$.customerPassword").value("amr"))
				.andExpect(jsonPath("$.customerEmail").value("amrin@gmail.com"))
				.andExpect(jsonPath("$.customerAddress").value("ou colony"))
				.andDo(print());

	}

}