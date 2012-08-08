/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oreilly.springdata.rest.order.web;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oreilly.springdata.rest.core.Customer;
import com.oreilly.springdata.rest.order.Order;
import com.oreilly.springdata.rest.order.OrderRepository;

/**
 * Custom SpringMVC controller that exposes the customer's orders via {@code /customer/ id}/orders}.
 * 
 * @author Oliver Gierke
 */
@Controller
@RequestMapping("/customer/{customer}")
public class CustomerOrdersController {

	private final OrderRepository repository;

	/**
	 * Creates a new {@link CustomerOrdersController} using the given {@link OrderRepository}.
	 * 
	 * @param repository must not be {@literal null}.
	 */
	public CustomerOrdersController(OrderRepository repository) {

		Assert.notNull(repository);
		this.repository = repository;
	}

	/**
	 * Exposes the orders triggered by a {@link Customer}.
	 * 
	 * @param customer the {@link Customer} we'd like to expose the orders for
	 * @return
	 */
	@RequestMapping("/orders")
	public HttpEntity<Resources<Resource<Order>>> getCustomerOrders(@PathVariable Customer customer) {

		List<Order> orders = repository.findByCustomer(customer);
		Resources<Resource<Order>> fromEntities = Resources.fromEntities(orders);

		return new ResponseEntity<Resources<Resource<Order>>>(fromEntities, HttpStatus.OK);
	}
}
