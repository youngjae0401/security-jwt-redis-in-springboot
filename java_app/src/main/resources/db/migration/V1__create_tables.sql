CREATE TABLE `users` (
    id BIGINT UNSIGNED auto_increment PRIMARY KEY NOT NULL,
    email varchar(100) NOT NULL COMMENT '이메일',
    name varchar(30) NOT NULL COMMENT '이름',
    password varchar(255) NOT NULL COMMENT '비밀번호',
    phone_number varchar(20) NOT NULL COMMENT '전화번호',
    `role` varchar(20) NOT NULL COMMENT '권한',
    created_at TIMESTAMP NOT NULL COMMENT '등록일',
    updated_at TIMESTAMP NOT NULL COMMENT '수정일',
    deleted_at TIMESTAMP NULL COMMENT '삭제일'
);

CREATE TABLE `products` (
    id BIGINT UNSIGNED auto_increment PRIMARY KEY NOT NULL,
    name varchar(100) NOT NULL COMMENT '상품명',
    price int NOT NULL COMMENT '상품금액',
    created_at TIMESTAMP NOT NULL COMMENT '등록일',
    updated_at TIMESTAMP NOT NULL COMMENT '수정일',
    deleted_at TIMESTAMP NULL COMMENT '삭제일'
);
