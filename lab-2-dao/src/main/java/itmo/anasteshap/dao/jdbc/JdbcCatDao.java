package itmo.anasteshap.dao.jdbc;

import itmo.anasteshap.util.HikariCPDataSource;
import itmo.anasteshap.dao.interfaces.CatDao;
import itmo.anasteshap.entities.Cat;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class JdbcCatDao implements CatDao {
    @Override
    public Cat save(Cat cat) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement(
                    "insert into cat values (default, ?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, cat.getName());
                statement.setDate(2, Date.valueOf(cat.getBirthDate()));
                statement.setString(3, cat.getBreed());
                statement.setString(4, cat.getColor());
                statement.setLong(5, cat.getOwner().getId());
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    cat.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cat;
    }

    @Override
    public void deleteById(long id) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement("delete from cat where id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByEntity(Cat cat) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement(
                    "delete from cat where id = ? and name = ? and birth_date = ? and breed = ? and color = ? and owner_id = ?")) {
                statement.setLong(1, cat.getId());
                statement.setString(2, cat.getName());
                statement.setDate(3, Date.valueOf(cat.getBirthDate()));
                statement.setString(4, cat.getBreed());
                statement.setString(5, cat.getColor());
                statement.setLong(6, cat.getOwner().getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("truncate table cat;")) {
                statement.execute(); // statement.executeUpdate(); ???
            }

            try (PreparedStatement statement = connection.prepareStatement("alter sequence cat_id_seq restart with 1;")) {
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cat update(Cat cat) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement(
                    "update cat set name = ?, birth_date = ?, breed = ?, color = ?, owner_id = ? where id = ?")) {
                statement.setString(1, cat.getName());
                statement.setDate(2, Date.valueOf(cat.getBirthDate()));
                statement.setString(3, cat.getBreed());
                statement.setString(4, cat.getColor());
                statement.setLong(5, cat.getOwner().getId());
                statement.setLong(6, cat.getId());

                statement.executeUpdate();
            }
            return cat;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cat getById(long id) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from cat where id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
                ResultSet rs = statement.getResultSet();

                var cat = new Cat(
                        rs.getString(2),
                        rs.getDate(3).toLocalDate(),
                        rs.getString(4),
                        rs.getString(5)); // ??
                cat.setId(rs.getLong(1));
                cat.setOwner(new JdbcOwnerDao().getById(rs.getLong(6)));
                return cat;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cat> getAll() {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from cat")) {
                statement.executeUpdate();

                ResultSet rs = statement.getResultSet();
                return makeCatsList(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cat> getAllByOwnerId(long id) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from cat where owner_id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();

                ResultSet rs = statement.getResultSet();
                return makeCatsList(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Cat> makeCatsList(ResultSet rs) throws SQLException {
        List<Cat> cats = new ArrayList<>();
        while (rs.next()) {
            var cat = new Cat(
                    rs.getString(2),
                    rs.getDate(3).toLocalDate(),
                    rs.getString(4),
                    rs.getString(5));
            cat.setId(rs.getLong(1));
            cat.setOwner(new JdbcOwnerDao().getById(rs.getLong(6)));
            cats.add(cat);
        }
        return cats;
    }
}