package com.epam.esm.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class TestInnerDatabaseConfig {

    private final static String CREATE_TABLE_TAG = "create table if not exists " +
            "tag(id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL UNIQUE, PRIMARY KEY (id));";
    private final static String CREATE_TABLE_GIFT_CERTIFICATE = "create table if not exists " +
            "gift_certificate(id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL UNIQUE," +
            " description VARCHAR(200), price INT NOT NULL, duration INT NOT NULL," +
            " create_date VARCHAR(50) NOT NULL, last_update_date VARCHAR(50), PRIMARY KEY (id));";
    private final static String CREATE_TABLE_GIFT_CERTIFICATE_TAG = "create table " +
            "if not exists gift_certificate_tag(certificate INT NOT NULL, tag INT NOT NULL);";

    private final static String INIT_TABLE_TAG = "insert into tag (name) values ('asa'),('bsb'),('csc');";
    private final static String INIT_TABLE_GIFT_CERTIFICATE = "insert into gift_certificate " +
            "(name, description, price, duration, create_date) values " +
            "('partName1', 'asdasa sada d asdasd', 10, 2, '2021-03-21T20:52:13.5213')," +
            "('a2', 'asdasa sada description first', 10, 2, '2021-03-24T00:00:13.5213')," +
            "('b3', 'asdasa sad description second', 10, 2, '2021-03-25T05:51:13.5213')," +
            "('a4', 'descriptions', 10, 2, '2021-03-29T06:52:13.5213');";
    private final static String INIT_TABLE_GIFT_CERTIFICATE_TAG = "insert into gift_certificate_tag " +
            "(certificate, tag) values (1, 1),(2, 2),(3, 3),(4, 1),(4, 2),(4, 3);";

    private final static String DROP_TABLE_TAG = "drop table tag;";
    private final static String DROP_TABLE_GIFT_CERTIFICATE = "drop table gift_certificate;";
    private final static String DROP_TABLE_GIFT_CERTIFICATE_TAG = "drop table gift_certificate_tag;";

    private JdbcTemplate jdbcTemplate;

    public TestInnerDatabaseConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void initializeDatabase() throws SQLException {
        jdbcTemplate.execute(createQueryInitializeDatabase());
    }

    private String createQueryInitializeDatabase() {
        return String.join(" ",
                CREATE_TABLE_TAG,
                CREATE_TABLE_GIFT_CERTIFICATE,
                CREATE_TABLE_GIFT_CERTIFICATE_TAG,
                INIT_TABLE_TAG,
                INIT_TABLE_GIFT_CERTIFICATE,
                INIT_TABLE_GIFT_CERTIFICATE_TAG);
    }

    void destroyDatabase() {
        jdbcTemplate.execute(createQueryDestroyDatabase());
    }

    private String createQueryDestroyDatabase() {
        return String.join(" ",
                DROP_TABLE_TAG,
                DROP_TABLE_GIFT_CERTIFICATE,
                DROP_TABLE_GIFT_CERTIFICATE_TAG);
    }

}