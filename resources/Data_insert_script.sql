
INSERT INTO `provider`.`administrator` (`login`, `password`) VALUES ('admin', 'admin');
INSERT INTO `provider`.`administrator` (`login`, `password`) VALUES ('administrator', 'administrator');

INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user1', 'user1', 'Ivan', 'ivan@gmail.com');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user2', 'user2', 'Petr', 'petr@gmail.com');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user3', 'user3', 'Olga', 'olga@gmail.com');

INSERT INTO `provider`.`tariff_type` (`type`) VALUES ('unlim');
INSERT INTO `provider`.`tariff_type` (`type`) VALUES ('traffic');

INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`) VALUES ('�������', '2.00', '5.00', '2');
INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`) VALUES ('�������', '3.00', '10.00', '2');
INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`) VALUES ('������', '5.00', '25.00', '2');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`) VALUES ('������', '18.00', '25', '1');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`) VALUES ('��������', '24.00', '50', '1');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`) VALUES ('�������', '39.00', '100', '1');

