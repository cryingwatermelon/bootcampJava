package com.watermelon.beatckc.entity;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.GenerationType;

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
    String nickname();

    @Key
    String email();

    LocalDateTime createdAt();
}

