CREATE TABLE orders (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  orderDate datetime NOT NULL,
  status varchar(255) NOT NULL,
  PRIMARY KEY (id)
);