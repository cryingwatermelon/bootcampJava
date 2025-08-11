package com.watermelon.beatckc.entity;

import com.watermelon.beatckc.validation.NoQQEmailPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.babyfish.jimmer.sql.*;

import java.time.LocalDateTime;

/**
 * Entity for table "basicInfo"
 */
@Entity
public interface Basicinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    )
    int id();

    @Key
    @NotNull(message = "昵称不能为空")
    @Size(min = 2, max = 8, message = "昵称为2-8位")
    String nickname();

    @Key
    @Email
    @NoQQEmailPattern()
    String email();

    LocalDateTime createdAt();
}

