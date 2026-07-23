package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL =
            "SELECT id, description, image_url, name, price, stock " +
            "FROM products ORDER BY id";

    private static final String SELECT_BY_ID =
            "SELECT id, description, image_url, name, price, stock " +
            "FROM products WHERE id = ?";

    private static final String UPDATE_STOCK =
            "UPDATE products SET stock = ? WHERE id = ?";

    private static final String DECREASE_STOCK =
            "UPDATE products " +
            "SET stock = stock - ? " +
            "WHERE id = ? AND stock >= ?";

    public List<Product> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRow);
    }

    public Optional<Product> findById(long productId) {
        return jdbcTemplate.query(
                SELECT_BY_ID,
                this::mapRow,
                productId
        ).stream().findFirst();
    }

    public boolean updateStock(long productId, int stock) {
        int result = jdbcTemplate.update(
                UPDATE_STOCK,
                stock,
                productId
        );
        return result == 1;
    }

    public boolean decreaseStock(long productId, int quantity) {
        int result = jdbcTemplate.update(
                DECREASE_STOCK,
                quantity,
                productId,
                quantity
        );
        return result == 1;
    }

    private Product mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        Product product = new Product();

        product.setId(rs.getLong("id"));
        product.setDescription(rs.getString("description"));
        product.setImageUrl(rs.getString("image_url"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStock(rs.getInt("stock"));

        return product;
    }
}
