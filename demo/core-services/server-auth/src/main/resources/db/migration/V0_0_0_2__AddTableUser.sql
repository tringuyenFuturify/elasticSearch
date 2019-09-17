CREATE TABLE `permission` (
  `id` varchar(256) NOT NULL,
  `name` longtext NOT NULL,
  PRIMARY KEY (`id`)
); 

CREATE TABLE `role`
(
	`id`	INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	`role`	VARCHAR(256) NOT NULL
);

CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` varchar(127) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `IX_RolePermission_PermissionId` (`permission_id`),
  CONSTRAINT `FK_RolePermission_Permission_PermissionId` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_RolePermission_Role_RoleId` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
); 

CREATE TABLE `user`
(
	`id`		INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	`email`		VARCHAR(256) NOT NULL,
	`password`	VARCHAR(256) NOT NULL,
	`name`		VARCHAR(256) NOT NULL,
	`active`	BIT NOT NULL,
	`modified_at` datetime(6) DEFAULT NULL,
    `modified_by` char(36) DEFAULT  NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `created_by` char(36) DEFAULT NULL
);

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `IX_UserRole_RoleId` (`role_id`),
  CONSTRAINT `FK_UserRole_User_Userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_UserRole_Role_RoleId` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
);

-- Populate initial data ---
INSERT INTO `permission`(`id`,`name`)
	VALUES ('MANAGE_PROJECT', 'Manage Project'); 
INSERT INTO `permission`(`id`, `name`)
	VALUES ('MANAGE_RESOURCE', 'Manage Resource'); 

INSERT INTO `role`(`role`)
	VALUES ('admin'); 
	
INSERT INTO `user`(`email`, `password`, `name`, `active`, `created_at`, `created_by`, `modified_at`, `modified_by`)
	VALUES ('admin@example.com', '123456', 'Administrator', true, '2019-09-11 00:00:00', null, '2019-09-11 00:00:00', null); 
	
INSERT INTO `role_permission` (`role_id`, `permission_id`)	
	SELECT r.id, p.id FROM `role` r, `permission` p;
	
INSERT INTO `user_role` (`user_id`, `role_id`)	
	SELECT u.id, r.id FROM `user` u, `role` r;	
