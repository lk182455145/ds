package com.leadingsoft.ds.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import com.dm.common.converter.Converter;
import com.leadingsoft.ds.dto.OrderDto;
import com.leadingsoft.ds.entities.Order;

@Component
public class OrderConverter implements Converter<Order, OrderDto> {

	@Override
	public Order copyProperties(Order model, OrderDto dto) {
		model.setColumn(dto.getColumn());
		model.setDirection(dto.getDirection());
		return model;
	}

	private Order toModel(OrderDto _order) {
		Order order = new Order();
		copyProperties(order, _order);
		return order;
	}

	public List<Order> toModel(Iterable<OrderDto> _orders) {
		if (IterableUtils.isEmpty(_orders)) {
			return Collections.emptyList();
		} else {
			List<Order> orders = new ArrayList<>();
			for (OrderDto _order : _orders) {
				orders.add(toModel(_order));
			}
			return orders;
		}
	}

	@Override
	public OrderDto toDto(Order model) {
		OrderDto order_ = new OrderDto();
		order_.setColumn(model.getColumn());
		order_.setDirection(model.getDirection());
		return order_;
	}

}
