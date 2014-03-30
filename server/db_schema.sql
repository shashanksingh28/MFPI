SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `microfinance` ;
CREATE SCHEMA IF NOT EXISTS `microfinance` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `microfinance` ;

-- -----------------------------------------------------
-- Table `microfinance`.`Group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `microfinance`.`Group` (
  `HashId` VARCHAR(256) NOT NULL,
  `Name` VARCHAR(128) NOT NULL,
  `PresidentId` VARCHAR(256) NOT NULL,
  `SecretaryId` VARCHAR(256) NULL,
  `TreasurerId` VARCHAR(256) NULL,
  `FieldOfficerId` VARCHAR(256) NULL,
  `CreatedDate` DATETIME NOT NULL,
  `CreatedBy` VARCHAR(256) NOT NULL,
  `Active` BINARY NOT NULL,
  `RecurringIndividualAmount` INT NULL,
  `MonthlyMeetingDate` DATE NOT NULL,
  `ClusterId` INT NULL,
  `AddressLine1` VARCHAR(45) NULL,
  `AddressLine2` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Country` VARCHAR(45) NULL,
  PRIMARY KEY (`HashId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `microfinance`.`Member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `microfinance`.`Member` (
  `UID` VARCHAR(256) NOT NULL,
  `GroupUID` VARCHAR(256) NOT NULL,
  `FirstName` VARCHAR(45) NOT NULL,
  `LastName` VARCHAR(45) NULL,
  `Sex` CHAR NOT NULL,
  `Age` INT NULL,
  `EmailId` VARCHAR(75) NULL,
  `Active` BINARY NOT NULL,
  `TotalSavings` DECIMAL(10,2) NOT NULL,
  `TotalDebt` DECIMAL(10,2) NOT NULL,
  `CreatedDate` DATETIME NOT NULL,
  `CreatedBy` VARCHAR(256) NOT NULL,
  `ContactNumber` VARCHAR(45) NULL,
  `AddressLine1` VARCHAR(45) NULL,
  `AddressLine2` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Country` VARCHAR(45) NULL,
  PRIMARY KEY (`UID`),
  INDEX `FK_Member_Group_idx` (`GroupUID` ASC),
  CONSTRAINT `FK_Member_Group`
    FOREIGN KEY (`GroupUID`)
    REFERENCES `microfinance`.`Group` (`HashId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `microfinance`.`GroupMemberLoanAccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `microfinance`.`GroupMemberLoanAccount` (
  `Id` VARCHAR(256) NOT NULL,
  `GroupId` VARCHAR(256) NOT NULL,
  `MemberId` VARCHAR(256) NOT NULL,
  `PrincipalAmount` BIGINT NOT NULL DEFAULT 0,
  `InterestRate` DECIMAL(3,2) NULL,
  `StartDate` DATE NOT NULL,
  `EndDate` DATE NOT NULL,
  `Outstanding` BIGINT NULL,
  `Reason` VARCHAR(500) NULL,
  `InstallmentAmount` BIGINT NULL,
  `NoOfInstallments` INT NULL,
  `CreatedDate` DATETIME NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (`Id`),
  INDEX `FK_GMLA_Group_idx` (`GroupId` ASC),
  INDEX `FK_GMLA_Member_idx` (`MemberId` ASC),
  CONSTRAINT `FK_GMLA_Group`
    FOREIGN KEY (`GroupId`)
    REFERENCES `microfinance`.`Group` (`HashId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_GMLA_Member`
    FOREIGN KEY (`MemberId`)
    REFERENCES `microfinance`.`Member` (`UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `microfinance`.`GroupMemberLoanTransaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `microfinance`.`GroupMemberLoanTransaction` (
  `Id` BIGINT NOT NULL AUTO_INCREMENT,
  `GroupId` VARCHAR(256) NOT NULL,
  `GroupMemberLoanId` VARCHAR(256) NULL,
  `Repayment` BIGINT NULL,
  `DateTime` DATETIME NOT NULL,
  `SignedBy` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `FK_GroupId_idx` (`GroupId` ASC),
  INDEX `FK_GroupMemberLoanId_idx` (`GroupMemberLoanId` ASC),
  CONSTRAINT `FK_GroupId`
    FOREIGN KEY (`GroupId`)
    REFERENCES `microfinance`.`Group` (`HashId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_GroupMemberLoanId`
    FOREIGN KEY (`GroupMemberLoanId`)
    REFERENCES `microfinance`.`GroupMemberLoanAccount` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `microfinance`.`GroupMemberSavingAccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `microfinance`.`GroupMemberSavingAccount` (
  `Id` VARCHAR(256) NOT NULL,
  `GroupId` VARCHAR(256) NOT NULL,
  `MemberId` VARCHAR(45) NULL,
  `TotalSaving` BIGINT NULL DEFAULT 0,
  `InterestAccumulated` BIGINT NULL,
  `CreatedDate` DATETIME NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (`Id`),
  INDEX `FK_GroupMemberSaving_Group_idx` (`GroupId` ASC),
  INDEX `FK_GroupMemberSaving_Member_idx` (`MemberId` ASC),
  CONSTRAINT `FK_GroupMemberSaving_Group`
    FOREIGN KEY (`GroupId`)
    REFERENCES `microfinance`.`Group` (`HashId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_GroupMemberSaving_Member`
    FOREIGN KEY (`MemberId`)
    REFERENCES `microfinance`.`Member` (`UID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `microfinance`.`GroupSavingTransaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `microfinance`.`GroupSavingTransaction` (
  `Id` VARCHAR(256) NOT NULL,
  `GroupId` VARCHAR(256) NOT NULL,
  `GroupMemberSavingId` VARCHAR(256) NOT NULL,
  `Amount` BIGINT NOT NULL,
  `DateTime` DATETIME NOT NULL,
  `SignedBy` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `FK_GroupMemberSavingId_idx` (`GroupMemberSavingId` ASC),
  INDEX `FK_GroupId_idx` (`GroupId` ASC),
  CONSTRAINT `FK_GroupId`
    FOREIGN KEY (`GroupId`)
    REFERENCES `microfinance`.`Group` (`HashId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_GroupMemberSavingId`
    FOREIGN KEY (`GroupMemberSavingId`)
    REFERENCES `microfinance`.`GroupMemberSavingAccount` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
