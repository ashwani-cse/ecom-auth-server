CREATE TABLE `role`
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    create_timestamp TIMESTAMP NULL,
    update_timestamp TIMESTAMP NULL,
    is_deleted       BIT(1) NULL,
    name             VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE token
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    create_timestamp TIMESTAMP NULL,
    update_timestamp TIMESTAMP NULL,
    is_deleted       BIT(1) NULL,
    value            VARCHAR(255) NULL,
    user_id          BIGINT NULL,
    expire_at        datetime NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

CREATE TABLE user_detail
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    create_timestamp  TIMESTAMP NULL,
    update_timestamp  TIMESTAMP NULL,
    is_deleted        BIT(1) NULL,
    first_name        VARCHAR(255) NULL,
    last_name         VARCHAR(255) NULL,
    email             VARCHAR(255) NULL,
    password          VARCHAR(255) NULL,
    phone_number      VARCHAR(255) NULL,
    is_email_verified BIT(1) NULL,
    is_phone_verified BIT(1) NULL,
    CONSTRAINT pk_user_detail PRIMARY KEY (id)
);

CREATE TABLE user_role_mapping
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL
);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user_detail (id);

ALTER TABLE user_role_mapping
    ADD CONSTRAINT fk_user_role_mapping_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_role_mapping
    ADD CONSTRAINT fk_user_role_mapping_on_user_detail FOREIGN KEY (user_id) REFERENCES user_detail (id);