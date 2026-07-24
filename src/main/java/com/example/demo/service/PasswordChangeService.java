package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.PasswordChangeDAO;

@Service
public class PasswordChangeService {

    private final PasswordChangeDAO passwordChangeDAO;

    public PasswordChangeService(
            PasswordChangeDAO passwordChangeDAO) {

        this.passwordChangeDAO =
                passwordChangeDAO;
    }

    /*
     * パスワード変更
     */
    public boolean changePassword(
            String memberId,
            String currentPassword,
            String newPassword) {

        if (memberId == null
                || memberId.isBlank()) {
            return false;
        }

        if (currentPassword == null
                || currentPassword.isBlank()) {
            return false;
        }

        if (newPassword == null
                || newPassword.isBlank()) {
            return false;
        }

        boolean passwordOk =
                passwordChangeDAO.checkPassword(
                        memberId,
                        currentPassword);

        if (!passwordOk) {
            return false;
        }

        return passwordChangeDAO.updatePassword(
                memberId,
                newPassword);
    }
}