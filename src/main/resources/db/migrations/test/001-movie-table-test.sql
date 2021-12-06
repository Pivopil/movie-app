CREATE TABLE movie
(
    id               int            NOT NULL AUTO_INCREMENT,
    title            varchar(256)   NOT NULL,
    year year NOT NULL,
    is_best_picture  tinyint        NOT NULL,
    box_office_value decimal(15, 0) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY id_UNIQUE (id)
);