package by.tut.shershnev_s.repository;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionRepository {

    Connection getConnection() throws SQLException;

}
