
// для админов пароль - aA1aA1
INSERT INTO `provider`.`administrator` (`login`, `password`) VALUES ('admin', '3636adf508e00eb49436de619f2d85f8');
INSERT INTO `provider`.`administrator` (`login`, `password`) VALUES ('administrator', '3636adf508e00eb49436de619f2d85f8');

// для пользователей пароль - userUSER1 (2, 3, 4 и т.д.)
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`, `balance`) VALUES ('user1', 'a7cd73b6ff14aeea621a9ee85fcb5163', 'Ivan', 'ivan@gmail.com', '61.00');  
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`, `balance`) VALUES ('user2', '75e91531835c927c66fd79cdf6a63ab8', 'Petr', 'petr@gmail.com', '0.00');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user3', '6be23f5a387ceaace739957e8c23d797', 'Olga', 'olga@gmail.com');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`, `balance`) VALUES ('user4', '10510d0146cf8060d14c1a6e0b19c447', 'Masha', 'masha@gmail.com', '25.00');  
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`, `balance`) VALUES ('user5', '9c010ae804014fedaed08743dcd2ecac', 'Sasha', 'sasha@gmail.com', '22.00');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`, `balance`) VALUES ('user6', 'a37bac07ad2782f62d1f9467125b6cd1', 'Ryhor', 'ryhor@gmail.com', '65.00');  
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`, `balance`) VALUES ('user7', '163b6034975365c713eaa3cda82e541c', 'Oleg', 'oleg@gmail.com', '10.00');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user8', 'f55b4bf0814bb4d2502ddf2b1bbfd0da', 'Владимир', 'vladimir@gmail.com');  
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user9', '5f21b8484d0bb2257497da33cfdec328', 'МАРИНА', 'marina@tut.by');
INSERT INTO `provider`.`user` (`login`, `password`, `name`, `email`) VALUES ('user10', '71e158e348e9d9ac8218a3948f9cea83', 'Anna', 'anna@gmail.com');  

INSERT INTO `provider`.`tariff_type` (`type`) VALUES ('unlim');
INSERT INTO `provider`.`tariff_type` (`type`) VALUES ('traffic');
 
INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`, `picture`) VALUES ('Студент', '2.00', '5.00', '2', 'images/tariffs/student.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`, `picture`) VALUES ('Подарок', '3.00', '10.00', '2', 'images/tariffs/present.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `size`, `tariff_type_id`, `picture`) VALUES ('Ночной', '5.00', '25.00', '2', 'images/tariffs/night.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`, `picture`) VALUES ('Особый', '18.00', '25', '1', 'images/tariffs/special.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`, `picture`) VALUES ('Семейный', '24.00', '50', '1', 'images/tariffs/family.png');
INSERT INTO `provider`.`tariff` (`name`, `price`, `speed`, `tariff_type_id`, `picture`) VALUES ('Мировой', '39.00', '100', '1', 'images/tariffs/world.png');


INSERT INTO `provider`.`transaction` (`type`, `ammount`, `date`, `user_id`) VALUES ('refill', '100.00', '2017-06-20 00:00:00', '1');
INSERT INTO `provider`.`transaction` (`type`, `ammount`, `date`, `user_id`) VALUES ('refill', '5.00', '2017-06-18 00:00:00', '2');
INSERT INTO `provider`.`transaction` (`type`, `ammount`, `date`, `user_id`) VALUES ('withdraw', '39.00', '2017-06-20 00:00:00', '1');
INSERT INTO `provider`.`transaction` (`type`, `ammount`, `date`, `user_id`) VALUES ('withdraw', '5.00', '2017-06-18 00:00:00', '2');


INSERT INTO `provider`.`event` (`title`) VALUES ('Нет оплаты');





UPDATE `provider`.`transaction` SET `date`='2017-06-20 00:00:00' WHERE `id`='25' and`user_id`='1';



INSERT INTO `provider`.`user_to_tariff` (`user_id`, `tariff_id`, `begin`) VALUES ('6', '3', '2004-06-20 17:00:00');


// уже исправила в схеме 
ALTER TABLE provider.user_to_tariff MODIFY end datetime NULL DEFAULT NULL;

