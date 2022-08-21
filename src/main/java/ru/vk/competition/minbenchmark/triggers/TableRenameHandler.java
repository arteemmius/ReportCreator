package ru.vk.competition.minbenchmark.triggers;

import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Deprecated
public class TableRenameHandler implements Trigger {
    String tableName;

    @Override
    public void init(Connection conn, String schemaName,
                     String triggerName, String tableName, boolean before, int type)
            throws SQLException {
        this.tableName = tableName;
    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow)
            throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO " + tableName + " (id, name, age) VALUES (1, \"name\", 5);")
        ) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void close() throws SQLException {}

    @Override
    public void remove() throws SQLException {}
}
