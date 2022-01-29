package com.crm.apiTest.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.crm.apiTest.model.Customer;
import com.crm.apiTest.repository.customer.CustomerRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository repository;
	
	@Test
	@Sql(scripts = {"file:./src/test/resources/InsertInitialCustomerForTest.sql"})
	public void shouldBeAbleToGetAllCustomers() {
		Iterable<Customer> allCustomers = repository.findAll();
		Assertions.assertTrue(((Collection<?>) allCustomers).size() == 2);
	}
	
}

