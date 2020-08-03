CREATE TABLE `user` (
	`user_id` INT(11) NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(128) NOT NULL,
	`password` VARCHAR(40) NOT NULL,
	`nickname` VARCHAR(40) NOT NULL,
	`auth` INT NOT NULL DEFAULT '0',
	PRIMARY KEY (`user_id`),
	UNIQUE INDEX `email` (`email`),
	UNIQUE INDEX `nickname` (`nickname`)
);

CREATE TABLE `food_category` (
	`food_category_id` INT NOT NULL AUTO_INCREMENT,
	`food_category_name` VARCHAR(128),
	PRIMARY KEY (`food_category_id`)
);

CREATE TABLE `favorite` (
	`favorite_id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`food_category_id` INT NOT NULL,
	PRIMARY KEY (`favorite_id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
	FOREIGN KEY (`food_category_id`) REFERENCES `food_category` (`food_category_id`)
);

CREATE TABLE `recipe` (
	`recipe_id` INT NOT NULL AUTO_INCREMENT,
	`recipe_name` VARCHAR(255) NOT NULL,
	`recipe_thumbnail_src` VARCHAR(255) NULL DEFAULT 'images/defaultThumbnail.jpg',
	`recipe_detail` VARCHAR(255),
	`user_id` INT NOT NULL,
	`reg_time` DATETIME,
	`cook_time` INT DEFAULT 0,
	`level` INT DEFAULT 0,
	`avg_rating` DOUBLE DEFAULT 0,
	`calorie` INT DEFAULT 0,
	`carbon` INT DEFAULT 0,
	`protein` INT DEFAULT 0,
	`fat` INT DEFAULT 0,
	`sugar` INT DEFAULT 0,
	`natrium` INT DEFAULT 0,
	PRIMARY KEY (`recipe_id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
	FOREIGN KEY (`food_category_id`) REFERENCES `food_category` (`food_category_id`)
);

CREATE TABLE `recipe_photo` (
	`recipe_photo_id` INT NOT NULL AUTO_INCREMENT,
	`recipe_id` INT NOT NULL,
	`photo_src` VARCHAR(255) NOT NULL DEFAULT 'images/defaultThumbnail.jpg',
	`photo_detail` VARCHAR(255),
	PRIMARY KEY (`recipe_photo_id`),
	FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`)
);

CREATE TABLE `ingredient` (
	`ingredient_id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(128) NOT NULL,
	`detail` VARCHAR(255),
	`calorie` INT DEFAULT 0,
	`carbon` INT DEFAULT 0,
	`protein` INT DEFAULT 0,
	`fat` INT DEFAULT 0,
	`sugar` INT DEFAULT 0,
	`natrium` INT DEFAULT 0,
	PRIMARY KEY (`ingredient_id`)
);

CREATE TABLE `recipe_ingredient` (
	`recipe_ingredient_id` INT NOT NULL AUTO_INCREMENT,
	`recipe_id` INT NOT NULL,
	`ingredient_id` INT NOT NULL,
	PRIMARY KEY (`recipe_ingredient_id`),
	FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`),
	FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`ingredient_id`)
);

CREATE TABLE `review` (
	`review_id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`recipe_id` INT NOT NULL,
	`rating` DOUBLE DEFAULT '0',
	`content` VARCHAR(255),
	`reg_time` DATETIME NOT NULL,
	PRIMARY KEY (`review_id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
	FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`)
);

CREATE TABLE `recipe_food_category` (
	`recipe_food_category_id` INT AUTO_INCREMENT,
	`recipe_id` INT NOT NULL,
	`food_category_id` INT NOT NULL,
	PRIMARY KEY (`recipe_food_category_id`),
	FOREIGN KEY(`recipe_id`) REFERENCES `recipe` (`recipe_id`),
	FOREIGN KEY(`food_category_id`) REFERENCES `food_category` (`food_category_id`)
);

CREATE TABLE `blog` (
	`blog_id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL,
	`recipe_id` INT(11) NULL DEFAULT NULL,
	`title` VARCHAR(255) NOT NULL,
	`content` TEXT(65535) NULL DEFAULT NULL,
	`reg_time` DATETIME NOT NULL,
	`tag1` VARCHAR(255) NULL DEFAULT NULL,
	`tag2` VARCHAR(255) NULL DEFAULT NULL,
	`tag3` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`blog_id`),
	INDEX `user_id` (`user_id`),
	INDEX `recipe_id` (`recipe_id`),
	FOREIGN KEY (`recipe_id`) REFERENCES `ssafy`.`recipe` (`recipe_id`) ON UPDATE RESTRICT ON DELETE CASCADE,
	FOREIGN KEY (`user_id`) REFERENCES `ssafy`.`user` (`user_id`) ON UPDATE RESTRICT ON DELETE CASCADE
);


