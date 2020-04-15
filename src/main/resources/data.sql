INSERT IGNORE INTO product_category (category, tax_in_percentage) VALUES ("T-Shirt", 0.54), ("Electronics", 1.09), ("Bag",1.54), ("Dress",2.43), ("Accessories", 1.23);
INSERT IGNORE INTO roles (id,type) VALUES (1,"ROLE_BUYER"), (2,"ROLE_MERCHANT"), (3,"ROLE_ADMIN");
INSERT IGNORE INTO users (username, enabled, first_name, last_name, password, role_id) VALUES
("admin@miu.edu", TRUE, "Admin", "Super Privileged", "admin" ,3), /* id: 1 */
("merchant@miu.edu", TRUE, "merchant", "Medium privileged", "merchant",2), /* id: 2 */
("user@miu.edu", TRUE, "User", "Low Privileged", "user", 1), /* id: 3 */
("eauser1@miu.edu", TRUE, "EA Student 1", "Low Privileged", "eauser1" ,1), /* id: 4 */
("eauser2@miu.edu", TRUE, "EA Student 2", "Low Privileged", "eauser2", 1), /* id: 5 */
("eauser3@miu.edu", TRUE, "Merchant", "Walmart", "eauser3", 2),
("eauser4@miu.edu", TRUE, "Merchant", "Target", "eauser4", 2);  /* id: 6 */

REPLACE INTO merchants (user_id,biz_name,office_phone_1, can_sell) VALUES
(2,"WalMart","641 819 1136", TRUE),(7,"Golden Market","641 819 1136", FALSE),(6,"MUM Store","641 819 1136", FALSE);

INSERT IGNORE INTO product_image (id,image_url) VALUES
(1,"http://localhost:8080/productimages/1.jpg"),
(2,"http://localhost:8080/productimages/2.jpg"),
(3,"http://localhost:8080/productimages/3.jpg"),
(4,"http://localhost:8080/productimages/4.jpg"),
(5,"http://localhost:8080/productimages/5.jpg"),
(6,"http://localhost:8080/productimages/6.jpeg"),
(7,"http://localhost:8080/productimages/7.jpeg"),
(8,"http://localhost:8080/productimages/8.jpg"),
(9,"http://localhost:8080/productimages/9.jpg");

INSERT IGNORE INTO products (product_number,description,discount,price, qty_avail, summary, title, category_id, merchant_id,is_available) VALUES
("LG32-MX","",6, 256.98, 13, "The Denim Jacket | Uniform ", "The Denim Jacket | Uniform",1,2,1),
("GLJ32KE","Merino Wool Knit Men",4, 1996.98, 12, "MERINO WOOL KNIT Women", "MERINO WOOL KNIT Women",1,7,1),
("HP232ENVY","Scandia Woods Crew Neck Sweatshirt",2, 456.98, 123, " Crew Neck Sweatshirtp", " Crew Neck Sweatshirt",1,2,1),
("GLJ32KE","Flounce-Hem Fit-and-Flare Dress",4, 1996.98, 12, "Flounce-Hem Fit-and-Flare Dressd", "Flounce-Hem Fit-and-Flare Dress",1,2,1),
("HP232ENVY","Scandia Woods Crew Neck Sweatshirt",2, 456.98, 123, "HP Envy is a Great Laptop", "HP Envy GH456",2,6,1),
("LG32-MX","Plasma TV from LG is so beautifully crafted",6, 256.98, 13, "LG Plasma TV ", "LG Plasma TV 32 Inch Screen",2,6,1),
("GLJ32KE","Facade Jewelleries is fitted for all occcasions",4, 1996.98, 12, "Facade Gold Necklace 23 Karate Gold", "Facade Gold Necklace 23 Karate Gold",3,6,1),
("HP232ENVY","Merino Wool Knit Men",2, 456.98, 123, "HP Envy is a Great Laptop", "HP Envy GH456",2,6,1),
("HP232ENVY","Plasma TV from LG is so beautifully crafted",2, 496.98, 123, "HP Envy is a Great Laptop", "HP Envy GH456 20GB RAM",2,7,1);

INSERT IGNORE INTO products_images (products_id, images_id) VALUES (1,1), (2,2), (3,3),(4,4), (5,5), (6,6), (7,7),(8,8),(9,9);
