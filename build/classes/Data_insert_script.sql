
INSERT INTO `provider`.`administrator` (`login`, `password`) VALUES ('admin', '04a3e7c17ee075952a7c349cb12b8fd3');
INSERT INTO `provider`.`administrator` (`login`, `password`) VALUES ('administrator', '04a3e7c17ee075952a7c349cb12b8fd3');

INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user1', 'user1', 'Ivan', 'ivan@gmail.com');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user2', 'user2', 'Petr', 'petr@gmail.com');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user3', 'user3', 'Olga', 'olga@gmail.com');

INSERT INTO `provider`.`tariff_type` (`type`) VALUES ('unlim');
INSERT INTO `provider`.`tariff_type` (`type`) VALUES ('traffic');
 
INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`, `picture`) VALUES ('Студент', '2.00', '5.00', '2', 'images/tariffs/student.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`, `picture`) VALUES ('Подарок', '3.00', '10.00', '2', 'images/tariffs/present.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`, `picture`) VALUES ('Ночной', '5.00', '25.00', '2', 'images/tariffs/night.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`, `picture`) VALUES ('Особый', '18.00', '25', '1', 'images/tariffs/special.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`, `picture`) VALUES ('Семейный', '24.00', '50', '1', 'images/tariffs/family.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`, `picture`) VALUES ('Мировой', '39.00', '100', '1', 'images/tariffs/world.png');


UPDATE `provider`.`user` SET `balance`='10' WHERE `id`='1';
UPDATE `provider`.`user` SET `balance`='-8' WHERE `id`='2';
UPDATE `provider`.`user` SET `balance`='0' WHERE `id`='3';
UPDATE `provider`.`user` SET `balance`='25' WHERE `id`='4';
UPDATE `provider`.`user` SET `balance`='-1' WHERE `id`='5';
UPDATE `provider`.`user` SET `balance`='3.7' WHERE `id`='6';
UPDATE `provider`.`user` SET `balance`='-2.4' WHERE `id`='9';



INSERT INTO `provider`.`user_to_tariff` (`user_id`, `tariff_id`, `begin`) VALUES ('6', '3', '2004-06-20 17:00:00');


// уже исправила в схеме 
ALTER TABLE provider.user_to_tariff MODIFY end datetime NULL DEFAULT NULL;

