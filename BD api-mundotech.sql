-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema api-mundotech
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema api-mundotech
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `api-mundotech` DEFAULT CHARACTER SET utf8mb3 ;
USE `api-mundotech` ;

-- -----------------------------------------------------
-- Table `api-mundotech`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `api-mundotech`.`categoria` (
  `id_categoria` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_categoria`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `api-mundotech`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `api-mundotech`.`usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `contrasenia` VARCHAR(255) NOT NULL,
  `rol` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_usuario`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `api-mundotech`.`pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `api-mundotech`.`pedido` (
  `id_pedido` INT NOT NULL AUTO_INCREMENT,
  `detalle` VARCHAR(255) NOT NULL,
  `total` FLOAT NOT NULL,
  `usuario_id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_pedido`),
  INDEX `fk_pedido_usuario1_idx` (`usuario_id_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_pedido_usuario1`
    FOREIGN KEY (`usuario_id_usuario`)
    REFERENCES `api-mundotech`.`usuario` (`id_usuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `api-mundotech`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `api-mundotech`.`producto` (
  `id_producto` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `descripcion` VARCHAR(255) NOT NULL,
  `precio` FLOAT NOT NULL,
  `imagen` VARCHAR(255) NULL DEFAULT NULL,
  `stock` INT NULL DEFAULT NULL,
  `categoria_id_categoria` INT NOT NULL,
  PRIMARY KEY (`id_producto`),
  INDEX `fk_producto_categoria_idx` (`categoria_id_categoria` ASC) VISIBLE,
  CONSTRAINT `fk_producto_categoria`
    FOREIGN KEY (`categoria_id_categoria`)
    REFERENCES `api-mundotech`.`categoria` (`id_categoria`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `api-mundotech`.`pedidoproducto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `api-mundotech`.`pedidoproducto` (
  `id_pedido_producto` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NULL DEFAULT NULL,
  `pedido_id_pedido` INT NOT NULL,
  `producto_id_producto` INT NOT NULL,
  PRIMARY KEY (`id_pedido_producto`),
  INDEX `fk_pedidoProducto_pedido1_idx` (`pedido_id_pedido` ASC) VISIBLE,
  INDEX `fk_pedidoProducto_producto1_idx` (`producto_id_producto` ASC) VISIBLE,
  CONSTRAINT `fk_pedidoProducto_pedido1`
    FOREIGN KEY (`pedido_id_pedido`)
    REFERENCES `api-mundotech`.`pedido` (`id_pedido`),
  CONSTRAINT `fk_pedidoProducto_producto1`
    FOREIGN KEY (`producto_id_producto`)
    REFERENCES `api-mundotech`.`producto` (`id_producto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
