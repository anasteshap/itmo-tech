package itmo.anasteshap.dao.jdbc;

import itmo.anasteshap.util.HikariCPDataSource;
import itmo.anasteshap.dao.interfaces.OwnerDao;
import itmo.anasteshap.entities.Owner;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class JdbcOwnerDao implements OwnerDao {
    @Override
    public Owner save(Owner owner) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement(
                    "insert into owner values (default, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, owner.getName());
                statement.setDate(2, Date.valueOf(owner.getBirthDate()));
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    owner.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owner;
    }

    @Override
    public void deleteById(long id) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement("delete from owner where id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByEntity(Owner owner) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement(
                    "delete from owner where id = ? and name = ? and birth_date = ?")) {
                statement.setLong(1, owner.getId());
                statement.setString(2, owner.getName());
                statement.setDate(3, Date.valueOf(owner.getBirthDate()));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("truncate table owner;")) {
                statement.execute(); // statement.executeUpdate(); ???
            }

            try (PreparedStatement statement = connection.prepareStatement("alter sequence owner_id_seq restart with 1;")) {
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Owner update(Owner owner) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement(
                    "update owner set name = ?, birth_date = ? where id = ?")) {
                statement.setString(1, owner.getName());
                statement.setDate(2, Date.valueOf(owner.getBirthDate()));
                statement.setLong(3, owner.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owner;
    }

    @Override
    public Owner getById(long id) {
        try (var connection = HikariCPDataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from owner where id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();

                ResultSet rs = statement.getResultSet();
                var owner = new Owner(
                        rs.getString(2),
                        rs.getDate(3).toLocalDate());
                owner.setId(rs.getLong(1));
                return owner;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Owner> getAll() {
        List<Owner> owners = new ArrayList<>();
        try (var connection = HikariCPDataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from owner")) {
                statement.executeUpdate();

                ResultSet rs = statement.getResultSet();
                while (rs.next()) {
                    var owner = new Owner(
                            rs.getString(2),
                            rs.getDate(3).toLocalDate());
                    owner.setId(rs.getLong(1));
                    owners.add(owner);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return owners;
    }
}
