package com.jun.seeyouagain.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.seeyouagain.common.model.Order;
import com.jun.seeyouagain.repository.OrderRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class OrderJdbcTemplateServiceImpl implements OrderRepositoryService {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderJdbcTemplateServiceImpl(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");

        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        return null;
    }
}
