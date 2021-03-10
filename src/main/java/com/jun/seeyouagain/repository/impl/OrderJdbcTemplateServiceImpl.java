package com.jun.seeyouagain.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.seeyouagain.common.model.Order;
import com.jun.seeyouagain.common.model.Taco;
import com.jun.seeyouagain.common.util.SpringBeanUtil;
import com.jun.seeyouagain.repository.OrderRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderJdbcTemplateServiceImpl implements OrderRepositoryService {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Resource(name = "mysqlTemplate")
    private JdbcTemplate jdbc;

    public void getInsert() {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        getInsert();
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrder(taco, orderId);
        }

        return order;
    }

    //executeAndReturnKey返回主键id
    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());
        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    //不返回主键
    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
