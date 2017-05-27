-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema provider
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema provider
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `provider` DEFAULT CHARACTER SET utf8 ;
USE `provider` ;

-- -----------------------------------------------------
-- Table `provider`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`user` ;

CREATE TABLE IF NOT EXISTS `provider`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический первичный ключ.',
  `login` VARCHAR(45) NOT NULL COMMENT 'Логин пользователя.',
  `password` VARCHAR(45) NOT NULL COMMENT 'Пароль пользователя.',
  `name` VARCHAR(100) NOT NULL COMMENT 'Имя пользователя.',
  `email` VARCHAR(45) NOT NULL COMMENT 'E-mail пользователя.',
  `balance` DECIMAL(9,2) NULL COMMENT 'Остаток на счете пользователя.',
  `traffic_used` DECIMAL(9,2) NULL COMMENT 'Израсходованный трафик пользователя.',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Зарегистрированные пользователи.';


-- -----------------------------------------------------
-- Table `provider`.`administrator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`administrator` ;

CREATE TABLE IF NOT EXISTS `provider`.`administrator` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический первичный ключ.',
  `login` VARCHAR(45) NOT NULL COMMENT 'Логин для администратора.',
  `password` VARCHAR(45) NOT NULL COMMENT 'Пароль администратора.',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Администраторы. Вынесены в отдельную таблицу для безопасности, чтобы пользователь из таблицы \'user\' никак не мог поменять себе роль.';


-- -----------------------------------------------------
-- Table `provider`.`tariff_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`tariff_type` ;

CREATE TABLE IF NOT EXISTS `provider`.`tariff_type` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический первичный ключ.',
  `type` ENUM('unlim', 'traffic') NOT NULL COMMENT 'Тип тарифа (unlim или traffic).',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Виды тарифов.';


-- -----------------------------------------------------
-- Table `provider`.`tariff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`tariff` ;

CREATE TABLE IF NOT EXISTS `provider`.`tariff` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический первичный ключ.',
  `name` VARCHAR(45) NOT NULL COMMENT 'Название тарифа.',
  `price` DECIMAL(9,2) NOT NULL COMMENT 'Цена тарифного плана.',
  `size` DECIMAL(5,2) NULL COMMENT 'Объем пакета (для тарифов traffic указывается значение, для тарифов unlim - значение не указывается).',
  `speed` INT NULL COMMENT 'Максимальная скорость доступа (для unlim тарифов указывается значение, для traffic - не указывается).',
  `tariff_type_id` INT NOT NULL COMMENT 'Внешний ключ к таблице \'tariff_type\'.',
  PRIMARY KEY (`id`),
  INDEX `fk_tariff_tariff_type1_idx` (`tariff_type_id` ASC),
  CONSTRAINT `fk_tariff_tariff_type1`
    FOREIGN KEY (`tariff_type_id`)
    REFERENCES `provider`.`tariff_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Существующие тарифы.';


-- -----------------------------------------------------
-- Table `provider`.`user_to_tariff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`user_to_tariff` ;

CREATE TABLE IF NOT EXISTS `provider`.`user_to_tariff` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический первичный ключ.',
  `user_id` INT NOT NULL COMMENT 'Внешний ключ к таблице \'user\'.',
  `tariff_id` INT NOT NULL COMMENT 'Внешний ключ к таблице \'tariff\'.',
  `begin` DATETIME NOT NULL COMMENT 'Дата начала действия тарифного плана',
  `end` DATETIME NOT NULL COMMENT 'Дата окончания тарифного плана',
  PRIMARY KEY (`id`),
  INDEX `fk_tariff_has_user_user1_idx` (`user_id` ASC),
  INDEX `fk_tariff_has_user_tariff1_idx` (`tariff_id` ASC),
  CONSTRAINT `fk_tariff_has_user_tariff1`
    FOREIGN KEY (`tariff_id`)
    REFERENCES `provider`.`tariff` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tariff_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `provider`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Связь пользователя и купленных им тарифных планов.';


-- -----------------------------------------------------
-- Table `provider`.`transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`transaction` ;

CREATE TABLE IF NOT EXISTS `provider`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический первичный ключ.',
  `type` ENUM('refill', 'withdraw') NOT NULL COMMENT 'Тип транзакции (refill - зачисление, withdraw - списание).',
  `ammount` DECIMAL(9,2) NOT NULL COMMENT 'Сумма транзакции.',
  `date` DATETIME NOT NULL COMMENT 'Дата транзакции.',
  `user_id` INT NOT NULL COMMENT 'Внешний ключ к таблице \'user\'.',
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_transaction_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_transaction_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `provider`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Транзакции (пополнение/списание со счета).';


-- -----------------------------------------------------
-- Table `provider`.`ban`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`ban` ;

CREATE TABLE IF NOT EXISTS `provider`.`ban` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Первичный синтетический ключ.',
  `user_id` INT NOT NULL COMMENT 'Внешний ключ к таблице \'user\'.',
  `administrator_id` INT NOT NULL COMMENT 'Внешний ключ к таблице \'administrator\'.',
  `start_date` DATETIME NOT NULL COMMENT 'Дата начала бана.',
  `end_date` DATETIME NULL COMMENT 'Дата окончания бана.',
  `reason` VARCHAR(100) NULL COMMENT 'Комментарий к бану.',
  PRIMARY KEY (`id`),
  INDEX `fk_user_has_administrator_administrator1_idx` (`administrator_id` ASC),
  INDEX `fk_user_has_administrator_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_administrator_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `provider`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_administrator_administrator1`
    FOREIGN KEY (`administrator_id`)
    REFERENCES `provider`.`administrator` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Забаненные пользователи.';


-- -----------------------------------------------------
-- Table `provider`.`event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`event` ;

CREATE TABLE IF NOT EXISTS `provider`.`event` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический первичный ключ.',
  `title` VARCHAR(45) NOT NULL COMMENT 'Название события для логирования.',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'События для логирования.';


-- -----------------------------------------------------
-- Table `provider`.`log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`log` ;

CREATE TABLE IF NOT EXISTS `provider`.`log` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'Синтетический первичный ключ.',
  `date` DATETIME NOT NULL COMMENT 'Дата и время записи в лог.',
  `event_id` INT NOT NULL COMMENT 'Внешний ключ к таблице \'event\'.',
  `user_id` INT NOT NULL COMMENT 'Внешний ключ к таблице \'user\'.',
  PRIMARY KEY (`id`),
  INDEX `fk_log_event1_idx` (`event_id` ASC),
  INDEX `fk_log_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_log_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `provider`.`event` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_log_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `provider`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Лог';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
