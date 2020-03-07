create table COMMENT
(
    ID           BIGINT auto_increment,
    PARENT_ID    BIGINT,
    TYPE         INT,
    COMMENTATOR  INT,
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    LIKE_COUNT   BIGINT,
    constraint COMMENT_PK
        primary key (ID)
);
