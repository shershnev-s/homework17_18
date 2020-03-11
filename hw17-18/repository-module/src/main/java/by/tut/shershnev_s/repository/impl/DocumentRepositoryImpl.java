package by.tut.shershnev_s.repository.impl;

import by.tut.shershnev_s.repository.DocumentRepository;
import by.tut.shershnev_s.repository.model.Document;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {

    @Override
    public Document add(Connection connection, Document document) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO document(unique_number, description) VALUES(?,?)"
                        , Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, document.getUniqueNumber());
            preparedStatement.setString(2, document.getDescription());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating document failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    document.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating document failed, no ID obtained.");
                }
            }
            return document;
        }
    }

    @Override
    public List<Document> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, unique_number, description FROM document; "
                )
        ) {
            List<Document> docs = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Document document = getDocs(rs);
                    docs.add(document);
                }
                return docs;
            }
        }
    }

    @Override
    public Document findById(Connection connection, Long id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, unique_number, description FROM document WHERE id=?; "
                )
        ) {
            statement.setLong(1,id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Document document = getDocs(rs);
                    return document;
                }
                return null;
            }
        }
    }

    @Override
    public void deleteByID(Connection connection, Long id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM document WHERE id =?"
                )
        ) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting document failed, no rows affected.");
            }
        }
    }

    private Document getDocs(ResultSet rs) throws SQLException {
       Document document = new Document();
       document.setId(rs.getLong("id"));
        document.setUniqueNumber(rs.getString("unique_number"));
        document.setDescription(rs.getString("description"));
        return document;
    }
}
