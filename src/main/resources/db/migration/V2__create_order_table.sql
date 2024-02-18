CREATE TABLE orders (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  orderDate datetime NOT NULL,
  status varchar(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE order_items (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description varchar(255) DEFAULT NULL,
  quantity int(11) NOT NULL,
  order_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders(id)
);