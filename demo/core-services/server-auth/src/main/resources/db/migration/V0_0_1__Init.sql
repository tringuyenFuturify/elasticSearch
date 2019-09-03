CREATE TABLE `role`
(
	`id`						INT AUTO_INCREMENT PRIMARY KEY,
	`role`						VARCHAR(256)
);

CREATE TABLE `users`
(
	`id`						INT AUTO_INCREMENT PRIMARY KEY,
	`email`						VARCHAR(256),
	`password`					VARCHAR(256),
	`name`						VARCHAR(256),
	`active`					BIT,
	`role_id`					INT,
	FOREIGN KEY (`role_id`) REFERENCES `role`(`id`)
);