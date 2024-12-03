package com.example.customerms;

import com.example.customerms.model.Customer;
import com.example.customerms.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerMsApplicationTests {
	@Mock
	private com.example.customerms.repository.CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerServiceImpl;

	private Customer[] customersArray;

	@BeforeEach
	public void setUp() {
		customersArray = new Customer[]{
				new Customer(1L,"Marcos", "Martinez", "78580311", "marcos@gmail.com"),
				new Customer(2L,"Roberto", "Figueroa", "04536666", "roberto@gmail.com"),
				new Customer(3L,"Steve", "Palomino", "45789999", "steve@gmail.com")
		};
	}

	@Test
	public void testGetCustomerById() {
		Customer customer = customersArray[0];
		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		Optional<Customer> result = customerServiceImpl.getCustomerById(1L);

		assertTrue(result.isPresent(), "El cliente no debería ser nulo");
		assertEquals("Marcos", result.get().getNombre(), "El nombre del cliente no coincide");

		verify(customerRepository, times(1)).findById(1L);
	}

	@Test
	public void testGetAllCustomers() {
		when(customerRepository.findAll()).thenReturn(Arrays.asList(customersArray));

		List<Customer> result = customerServiceImpl.getAllCustomers();

		assertNotNull(result, "La lista de clientes no debería ser nula");
		assertEquals(3, result.size(), "El tamaño de la lista no coincide");
		assertEquals("Marcos", result.get(0).getNombre(), "El primer cliente no coincide");

		verify(customerRepository, times(1)).findAll();
	}

	@Test
	public void testSaveCustomer() {
		Customer newCustomer = new Customer(3L,"Ana", "Lopez", "12345678", "ana@gmail.com");
		when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

		Customer savedCustomer = customerServiceImpl.saveCustomer(newCustomer);

		assertNotNull(savedCustomer, "El cliente guardado no debería ser nulo");
		assertEquals("Ana", savedCustomer.getNombre(), "El nombre del cliente no coincide");

		verify(customerRepository, times(1)).save(newCustomer);
	}

	@Test
	public void testDeleteCustomer() {
		when(customerRepository.existsById(1L)).thenReturn(true);

		boolean deleted = customerServiceImpl.deleteCustomer(1L);

		assertTrue(deleted, "El cliente debería ser eliminado");

		verify(customerRepository, times(1)).existsById(1L);
		verify(customerRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testDeleteCustomerNotFound() {
		when(customerRepository.existsById(99L)).thenReturn(false);

		boolean deleted = customerServiceImpl.deleteCustomer(99L);

		assertFalse(deleted, "El cliente no debería ser eliminado porque no existe");

		verify(customerRepository, times(1)).existsById(99L);
		verify(customerRepository, never()).deleteById(anyLong());
	}

	@Test
	public void testUpdateCustomer() {
		Customer existingCustomer = customersArray[0];
		Customer updatedCustomer = new Customer(1L,"Marcos", "Gomez", "78580311", "marcos_gomez@gmail.com");
		when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
		when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

		Optional<Customer> result = customerServiceImpl.updateCustomer(1L, updatedCustomer);
		assertTrue(result.isPresent(), "El cliente actualizado no debería ser nulo");
		assertEquals("Gomez", result.get().getApellido(), "El apellido del cliente no coincide");

		verify(customerRepository, times(1)).findById(1L);
		verify(customerRepository, times(1)).save(existingCustomer);
	}
}
