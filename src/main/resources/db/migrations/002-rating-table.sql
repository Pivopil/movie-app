CREATE TABLE IF NOT EXISTS `rating`
(
    `user_id`  varchar(50) NOT NULL,
    `movie_id` int         NOT NULL,
    `score`    smallint    NOT NULL,
    PRIMARY KEY (`user_id`, `movie_id`),
    KEY        `movie_id_pf_idx` (`movie_id`),
    CONSTRAINT `movie_id_pf` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;