package org.audit.repository;

import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Класс Repository для аудита
 */
public class AuditRepository {

    public static final String INSERT_INTO_AUDITS = "INSERT INTO demo.audits(user_id, description, time) VALUES (?, ?, ?)";

    private final Connection connection;

    @SneakyThrows
    public AuditRepository(DataSource source) {
        this.connection = source.getConnection();
    }

    public void save(Long idUser, String description) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_AUDITS)){
            preparedStatement.setLong(1, idUser);
            preparedStatement.setString(2, description);
            LocalDateTime time = LocalDateTime.now();
            preparedStatement.setTimestamp(3, Timestamp.valueOf(time));
            preparedStatement.executeUpdate();
        }
    }
}
