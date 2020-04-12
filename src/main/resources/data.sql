INSERT IGNORE INTO roles (id,type) VALUES (1,"ROLE_BUYER"), (2,"ROLE_MERCHANT"), (3,"ROLE_ADMIN");
INSERT IGNORE INTO users (username, enabled, first_name, last_name, password, role_id) VALUES
("admin@miu.edu", TRUE, "Admin", "Super Privileged", "admin" ,3), /* id: 1 */
("merchant@miu.edu", TRUE, "Merchant", "Medium privileged", "faculty",2), /* id: 2 */
("user@miu.edu", TRUE, "User", "Low Privileged", "user", 1), /* id: 3 */
("eauser1@miu.edu", TRUE, "EA Student 1", "Low Privileged", "eauser1" ,1), /* id: 4 */
("eauser2@miu.edu", TRUE, "EA Student 2", "Low Privileged", "eauser2", 1), /* id: 5 */
("eauser3@miu.edu", TRUE, "Merchant", "Walmart", "eauser3", 2),
("eauser4@miu.edu", TRUE, "Merchant", "Target", "eauser4", 2);  /* id: 6 */
