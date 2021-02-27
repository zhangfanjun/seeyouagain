package com.jun.seeyouagain.repository.impl;

import com.jun.seeyouagain.common.model.Ingredient;
import com.jun.seeyouagain.repository.IngredientRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository("jdbc")
public class OldJdbcRepositoryServiceImpl implements IngredientRepositoryService {

    @Resource
    private DataSource dataSource;

    @Override
    public boolean save(Ingredient ingredient) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("insert into t_ingredient (id,name,type) values (?,?,?)");
            preparedStatement.setString(1, ingredient.getId());
            preparedStatement.setString(2, ingredient.getName());
            preparedStatement.setString(3, ingredient.getType().toString());
            boolean execute = preparedStatement.execute();
            if (!execute) {
                log.info("插入成功");
                return true;
            } else {
                log.info("插入失败");
                return false;
            }
        } catch (SQLException e) {
            log.error("插入异常:", e.getStackTrace());
            return false;
        } finally {
            //放在try后面的括号里可以实现自动关闭
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Ingredient findById(String id) {
        ResultSet resultSet = null;
        Ingredient ingredient = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select id ,name,type from t_ingredient where id = ?");

        ) {
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ingredient = new Ingredient(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Ingredient.Type.valueOf(resultSet.getString("type"))
                );
            }
            return ingredient;
        } catch (SQLException e) {
            log.error("查找失败:", e.getStackTrace());
            return ingredient;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.error("resultSet close false");
                }
            }
        }
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> list = null;
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement("select id ,name ,type from t_ingredient");
             final ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next()) {
                final Ingredient ingredient = new Ingredient(resultSet.getString("id"),
                        resultSet.getString("name"),
                        Ingredient.Type.valueOf(resultSet.getString("type")));
                list.add(ingredient);
            }
            return list;
        } catch (SQLException e) {
            log.error("查找失败:", e.getStackTrace());
            return list;
        }
    }
}
