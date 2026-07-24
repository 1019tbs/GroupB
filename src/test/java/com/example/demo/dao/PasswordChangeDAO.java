package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PasswordChangeDAOTest {

    private final PasswordChangeDAO dao =
            new PasswordChangeDAO();

    @Test
    void 現在のパスワードが正しい() {

        boolean result =
                dao.checkPassword(
                        "user001",
                        "pass001");

        assertTrue(result);
    }
}