package by.tut.shershnev_s.repository;

import by.tut.shershnev_s.repository.model.Document;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Repository
public interface DocumentRepository {

    Document add(Connection connection, Document document) throws SQLException;

    List<Document> findAll(Connection connection) throws SQLException;

}
