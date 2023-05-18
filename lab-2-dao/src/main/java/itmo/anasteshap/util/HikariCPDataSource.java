package itmo.anasteshap.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {
    private static final HikariConfig configuration = new HikariConfig(); // final ??
    private static final HikariDataSource ds; // final ??

    static {
        configuration.setJdbcUrl("jdbc:postgresql://localhost:5432/anastasiia_database");
        configuration.setUsername("anastasiia_user");
        configuration.setPassword("123");
        configuration.addDataSourceProperty("cachePrepStmts", "true");
        configuration.addDataSourceProperty("prepStmtCacheSize", "250");
        configuration.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(configuration);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private HikariCPDataSource(){}
}
