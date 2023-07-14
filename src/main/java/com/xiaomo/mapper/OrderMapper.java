package com.xiaomo.mapper;

import com.xiaomo.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
//@Mapper
public interface OrderMapper {

    List<Order> getOrderById(int orderId);
}
