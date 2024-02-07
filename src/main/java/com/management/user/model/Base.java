package com.management.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private String createTimeStamp;

    @JsonIgnore
    @Column(name = "UPDATE_TIMESTAMP")
    private String updateTimeStamp;

    @JsonIgnore
    @Column(name = "IS_DELETED")
    private boolean isDeleted;

}
