package com.jun.seeyouagain.repository.impl;

import com.jun.seeyouagain.common.model.Ingredient;
import com.jun.seeyouagain.repository.IngredientRepositoryService;
import com.sun.el.parser.AstFalse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository("jdbcTemplateMysql")
public class JdbcTemplateMysqlServiceImpl implements IngredientRepositoryService {

    @Resource(name = "mysqlTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Ingredient in) {
        final int update = jdbcTemplate.update("insert into t_ingredient (id ,name ,type) values (?,?,?)", in.getId(), in.getName(), in.getType().toString());
        return update > 0;
    }

    @Override
    public Ingredient findById(String id) {
        //sql语句，映射关系，占位符入参，这里不用query是因为返回的是一个对象，而不是list
        return jdbcTemplate.queryForObject("select id,name,type from t_ingredient where id=?", this::mapRowToIngredient, id);
    }

    private Ingredient mapRowToIngredient(ResultSet resultSet, int i) throws SQLException {
        return new Ingredient(resultSet.getString("id"), resultSet.getString("name"), Ingredient.Type.valueOf(resultSet.getString("type")));
    }

    @Override
    public List<Ingredient> findAll() {
        return jdbcTemplate.query("select id,name,type from t_ingredient", this::mapRowToIngredient);
    }
}
