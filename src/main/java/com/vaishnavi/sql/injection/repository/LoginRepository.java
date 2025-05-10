package com.vaishnavi.sql.injection.repository;

import com.vaishnavi.sql.injection.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class LoginRepository {

    @Autowired
    private ConnectionService connectionService;

    public boolean insecureLogin(String query) {
        // Unsafe query which uses string concatenation
        try (Connection conn = connectionService.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                // Login Successful if match is found
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean secureLogin(String query, String username, String password) {
        try (Connection conn = connectionService.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Login Successful if match is found
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}


