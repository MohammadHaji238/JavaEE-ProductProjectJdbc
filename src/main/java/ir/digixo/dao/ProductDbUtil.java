package ir.digixo.dao;

import ir.digixo.entity.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDbUtil {

    private DataSource dataSource;


    public ProductDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getProducts() throws Exception{
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            String sql = "select * from products order by name";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                BigDecimal price = resultSet.getBigDecimal("price");

                Product tempProduct = new Product(id, name, description, price);
                products.add(tempProduct);
            }
            return products;
        }finally {
            close(connection, statement, resultSet);
        }

    }

    public Product createProduct(Product product)
    {
       // Connection connection = null;
       // PreparedStatement statement = null;
        String sql = "insert into products (name,description,price) values (?,?,?)";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            prepStmt.setString(1, product.getName());
            prepStmt.setString(2, product.getDescription());
            prepStmt.setBigDecimal(3, product.getPrice());

            prepStmt.executeUpdate();

            try (ResultSet genKeys = prepStmt.getGeneratedKeys()) {
                if (genKeys.next())
                    product.setId(genKeys.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }


    public Optional<Product> findById(long id) {

        Optional<Product> product = Optional.empty();
        String query = "select * from products where id = ?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(query);
        ) {
            prepStmt.setLong(1, id);
            try (ResultSet rset = prepStmt.executeQuery();) {
                Product temProduct = new Product();
                if (rset.next()) {
                    temProduct.setId(rset.getLong("id"));
                    temProduct.setName(rset.getString("name"));
                    temProduct.setDescription(rset.getString("description"));
                    temProduct.setPrice(rset.getBigDecimal("price"));


                }
                product = Optional.of(temProduct);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return product;
    }
    public Product update(Product product) {
        String query
                = "update products set name=?, "
                + " description=? ,price=? where id=?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(query);
        ) {
            prepStmt.setString(1, product.getName());
            prepStmt.setString(2, product.getDescription());
            prepStmt.setBigDecimal(3, product.getPrice());
            prepStmt.setLong(4, product.getId());
            prepStmt.executeUpdate();


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return product;
    }
    public int delete(Long productId) {
        String query="DELETE FROM products where id=?";
        int rowEffected=0;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(query);
        ) {
            prepStmt.setLong(1, productId);
            rowEffected=prepStmt.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return rowEffected;

    }

    private void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (connection != null)
                connection.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
