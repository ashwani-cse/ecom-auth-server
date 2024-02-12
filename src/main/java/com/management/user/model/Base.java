package com.management.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

/**
 * @author Ashwani Kumar
 * Created on 11/02/24.
 */
@ToString
@Getter
@Setter
@MappedSuperclass
public class Base {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @JsonIgnore
    @Column(name = "CREATE_TIMESTAMP")
    private Instant createTimeStamp;

    @JsonIgnore
    @Column(name = "UPDATE_TIMESTAMP")
    private Instant updateTimeStamp;

    @JsonIgnore
    @Column(name = "IS_DELETED")
    private boolean isDeleted;

}
