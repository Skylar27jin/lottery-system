package zetian.bucssa.lottery_system.domain;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Data
public class User {
    private String buId;
    private String name;
    private String email;
    private String phone;
    private Timestamp created_at;

    // Getters and Setters by lombok
}
