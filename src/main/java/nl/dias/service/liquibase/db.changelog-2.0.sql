--liquibase formatted sql

--changeset Patrick Heidotting:1

ALTER TABLE `POLIS` MODIFY `SOORT` VARCHAR(2);

--changeset Patrick Heidotting:2

ALTER TABLE `POLIS_AUD` MODIFY `SOORT` VARCHAR(2);

ALTER TABLE `OPMERKING` ADD COLUMN TIJD datetime NOT NULL;
ALTER TABLE `OPMERKING_AUD` ADD COLUMN TIJD datetime NOT NULL;

--changeset Patrick Heidotting:3

ALTER TABLE `POLIS` ADD COLUMN POLISNUMMER VARCHAR(25) NOT NULL AFTER `INGANGSDATUM`;
ALTER TABLE `POLIS_AUD` ADD COLUMN POLISNUMMER VARCHAR(25) AFTER `INGANGSDATUM`;

--changeset Patrick Heidotting:4

ALTER TABLE `VERZEKERINGSMAATSCHAPPIJ` ADD COLUMN TONEN tinyint(1) NOT NULL DEFAULT '1' AFTER `NAAM`;
ALTER TABLE `VERZEKERINGSMAATSCHAPPIJ_AUD` ADD COLUMN TONEN tinyint(1) AFTER `NAAM`;

INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Achmea');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Aegon');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Agis');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Allianz Nederland');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('AllSecur');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Amersfoortse (de)');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('ASR Verzekeringen');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Avero Achmea');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Crisper');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Delta Lloyd');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Ditzo');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Generali');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Goudse (de)');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Kilometerverzekering (de)');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Klaverblad');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Kruidvat');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('London Verzekeringen');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Nationale Nederlanden');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('OHRA');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Polis Direct');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('SNS Bank');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Turien & Co');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Unive');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Verzekeruzelf.nl');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Zelf.nl');

--changeset Patrick Heidotting:5

ALTER TABLE `POLIS_AUD` ADD COLUMN `KENTEKEN` varchar(10) DEFAULT NULL AFTER `OMSCHRIJVING`;
ALTER TABLE `POLIS_AUD` ADD COLUMN `SOORTAUTOVERZEKERING` varchar(10) DEFAULT NULL AFTER `KENTEKEN`;
ALTER TABLE `POLIS` ADD COLUMN OLDTIMER tinyint(1) AFTER `SOORTAUTOVERZEKERING`;
ALTER TABLE `POLIS_AUD` ADD COLUMN OLDTIMER tinyint(1) AFTER `SOORTAUTOVERZEKERING`;

--changeset Patrick Heidotting:6

INSERT INTO `GEBRUIKER` (`SOORT`, `ID`, `EMAILADRES`, `SALT`, `WACHTWOORD`, `ACHTERNAAM`, `VOORNAAM`) VALUES ('B', 1, 'patrick@lakedigital.nl', '374b2ed3c4f8c0ea4832dc036ef98134c141f3abfad3ad969774a780e5a756ced90f6b4249b3bf93a89b52820afa7a15dfe86bb76a99215ebaba4d80bd7dae33', '65c36a56c8a45e686424fa4c8342e546093b61d1acb4479a4a4cc763ad48f2b1ddcdf78b4f1e591027ecc13c346f93c64655f1c946da5487312605791d0fa5a1', 'Heidotting', 'Patrick');
INSERT INTO `GEBRUIKER_AUD` (`SOORT`, `ID`, `REV`, `REVTYPE`, `ACHTERNAAM`, `VOORNAAM`) VALUES ('B', 1, 1, 0, 'Heidotting', 'Patrick');
INSERT INTO `REVINFO` (`timestamp`, `userid`) VALUES (0, NULL);
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('1', '1', '0', 'Achmea', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('2', '1', '0', 'Aegon', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('3', '1', '0', 'Agis', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('4', '1', '0', 'Allianz Nederland', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('5', '1', '0', 'AllSecur', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('6', '1', '0', 'Amersfoortse (de)', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('7', '1', '0', 'ASR Verzekeringen', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('8', '1', '0', 'Avero Achmea', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('9', '1', '0', 'Crisper', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('10', '1', '0', 'Delta Lloyd', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('11', '1', '0', 'Ditzo', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('12', '1', '0', 'Generali', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('13', '1', '0', 'Goudse (de)', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('14', '1', '0', 'Kilometerverzekering (de)', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('15', '1', '0', 'Klaverblad', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('16', '1', '0', 'Kruidvat', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('17', '1', '0', 'London Verzekeringen', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('18', '1', '0', 'Nationale Nederlanden', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('19', '1', '0', 'OHRA', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('20', '1', '0', 'Polis Direct', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('21', '1', '0', 'SNS Bank', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('22', '1', '0', 'Turien & Co', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('23', '1', '0', 'Unive', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('24', '1', '0', 'Verzekeruzelf.nl', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('25', '1', '0', 'Zelf.nl', '1');

--changeset Patrick Heidotting:7

ALTER TABLE `POLIS` ADD COLUMN `PREMIE` double DEFAULT NULL AFTER `POLISNUMMER`;
ALTER TABLE `POLIS_AUD` ADD COLUMN `PREMIE` double DEFAULT NULL AFTER `POLISNUMMER`;
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Unigarant');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Voogd en Voogd');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('VKG');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('DAS');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Europeesche');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Erasmus');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Monuta');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Onderlinge');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ` (`NAAM`) VALUES ('Reaal');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('26', '1', '0', 'Unigarant', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('27', '1', '0', 'Voogd en Voogd', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('28', '1', '0', 'VKG', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('29', '1', '0', 'DAS', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('30', '1', '0', 'Europeesche', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('31', '1', '0', 'Europeesche', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('32', '1', '0', 'Monuta', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('33', '1', '0', 'Onderlinge', '1');
INSERT INTO `VERZEKERINGSMAATSCHAPPIJ_AUD` (`ID`, `REV`, `REVTYPE`, `NAAM`, `TONEN`) VALUES ('34', '1', '0', 'Reaal', '1');

--changeset Patrick Heidotting:8

ALTER TABLE `POLIS` DROP COLUMN `OMSCHRIJVING`;
ALTER TABLE `POLIS_AUD` DROP COLUMN `OMSCHRIJVING`;

--changeset Patrick Heidotting:9

ALTER TABLE `POLIS` ADD COLUMN`BEDRIJF` bigint(20) DEFAULT NULL AFTER `OLDTIMER`;
ALTER TABLE `POLIS_AUD` ADD COLUMN`BEDRIJF` bigint(20) DEFAULT NULL AFTER `OLDTIMER`;
ALTER TABLE `POLIS` ADD KEY `FK48C681741B2D649` (`BEDRIJF`);
CREATE TABLE `BEDRIJF` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `HUISNUMMER` bigint(20) DEFAULT NULL,
  `PLAATS` varchar(255) DEFAULT NULL,
  `POSTCODE` varchar(6) DEFAULT NULL,
  `STRAAT` varchar(255) DEFAULT NULL,
  `TOEVOEGING` varchar(255) DEFAULT NULL,
  `KVK` varchar(8) DEFAULT NULL,
  `NAAM` varchar(255) DEFAULT NULL,
  `RELATIE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK1CFE4994DF4C9431` (`RELATIE`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

CREATE TABLE `BEDRIJF_AUD` (
  `ID` bigint(20) NOT NULL,
  `REV` int(11) NOT NULL,
  `REVTYPE` tinyint(4) DEFAULT NULL,
  `HUISNUMMER` bigint(20) DEFAULT NULL,
  `PLAATS` varchar(255) DEFAULT NULL,
  `POSTCODE` varchar(6) DEFAULT NULL,
  `STRAAT` varchar(255) DEFAULT NULL,
  `TOEVOEGING` varchar(255) DEFAULT NULL,
  `KVK` varchar(8) DEFAULT NULL,
  `NAAM` varchar(255) DEFAULT NULL,
  `RELATIE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`,`REV`),
  KEY `FK7B038D65D04A6114` (`REV`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--changeset Patrick Heidotting:10

ALTER TABLE `POLIS` ADD COLUMN `BETAALFREQUENTIE` varchar(1) DEFAULT NULL AFTER `ID`;
ALTER TABLE `POLIS_AUD` ADD COLUMN `BETAALFREQUENTIE` varchar(1) DEFAULT NULL AFTER `ID`;
ALTER TABLE `POLIS` ADD COLUMN `PROLONGATIEDATUM` date DEFAULT NULL AFTER `PREMIE`;
ALTER TABLE `POLIS_AUD` ADD COLUMN `PROLONGATIEDATUM` date DEFAULT NULL AFTER `PREMIE`;
ALTER TABLE `POLIS` ADD COLUMN `WIJZIGINGSDATUM` date DEFAULT NULL AFTER `PREMIE`;
ALTER TABLE `POLIS_AUD` ADD COLUMN `WIJZIGINGSDATUM` date DEFAULT NULL AFTER `PREMIE`;

--changeset Patrick Heidotting:11

INSERT INTO `KANTOOR` (`ID`, `HUISNUMMER`, `PLAATS`, `POSTCODE`, `STRAAT`, `TOEVOEGING`, `BTW_NUMMER`, `DATUMOPRICHTING`, `EMAILADRES`, `HUISNUMMER_FACTUUR`, `PLAATS_FACTUUR`, `POSTCODE_FACTUUR`, `STRAAT_FACTUUR`, `TOEVOEGING_FACTUUR`, `KVK`, `NAAM`, `RECHTSVORM`, `SOORTKANTOOR`) VALUES (NULL, '8', 'Emmen', '7827NH', 'Sierduif', NULL, '', '2014-05-09', 'info@dejongefinancieelconsult.nl', NULL, NULL, NULL, NULL, NULL, '04077584', 'De Jonge Financieel Consult', 'VOF', 'VZ');

--changeset Patrick Heidotting:12

DROP TABLE `BEDRIJF_AUD`;
DROP TABLE `BIJLAGE_AUD`;
DROP TABLE `GEBRUIKER_AUD`;
DROP TABLE `KANTOOR_AUD`;
DROP TABLE `ONDERLINGERELATIES_AUD`;
DROP TABLE `OPMERKING_AUD`;
DROP TABLE `POLIS_AUD`;
DROP TABLE `REKENINGNUMMER_AUD`;
DROP TABLE `SESSIE_AUD`;
DROP TABLE `TELEFOONNUMMER_AUD`;
DROP TABLE `VERZEKERINGSMAATSCHAPPIJ_AUD`;

--changeset Patrick Heidotting:13

ALTER TABLE `POLIS` DROP `KENTEKEN`;
ALTER TABLE `POLIS` DROP `SOORTAUTOVERZEKERING`;
ALTER TABLE `POLIS` DROP `OLDTIMER`;

--changeset Patrick Heidotting:14

ALTER TABLE `BIJLAGE` ADD COLUMN `SOORTBIJLAGE` varchar(50) DEFAULT NULL AFTER `BESTANDSNAAM`;

--changeset Patrick Heidotting:15

ALTER TABLE `BIJLAGE` ADD COLUMN `S3` varchar(255) DEFAULT NULL AFTER `BESTANDSNAAM`;

--changeset Patrick Heidotting:16

ALTER TABLE `BIJLAGE` DROP `BESTANDSNAAM`;

--changeset Patrick Heidotting:17

INSERT INTO `GEBRUIKER` (`SOORT`, `EMAILADRES`, `SALT`, `WACHTWOORD`, `ACHTERNAAM`, `TUSSENVOEGSEL`, `VOORNAAM`, `HUISNUMMER`, `PLAATS`, `POSTCODE`, `STRAAT`, `TOEVOEGING`, `BSN`, `BURGERLIJKESTAAT`, `GEBOORTEDATUM`, `GESLACHT`, `OVERLIJDENSDATUM`, `KANTOOR`) VALUES
('M', 'bene@dejongefinancieelconsult.nl', 'dc76872ef299759ac38e60d80184c4318c1f2350808e7a44c8a15ee8975008f408e3bf5d58857dc7ef3f692e9c64787d7adae2b41668397898f47803b778f0b7', '6f683ceb2bf2e91107cc2c7c74e8c5bb012f8bf6a9212750db29a8ec128b7004c67ae066e01b7a781cc0c02df722524edf51461e0a2a199cbea5020dd63cf627', 'Jonge', 'de', 'Bene', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
('M', 'gerben@dejongefinancieelconsult.nl', 'a9fb0bffd32af106a452b6c46333e534568dcef4f2bd2c423c0f2429494e10b4711261770102834f52c2f39bf340a0a13af1e0ce980a32bb0428b900048f4563', 'd8b4855835206891dc9a2138f9a5c60c181b713bcabd708933c1deef0880110ade7c67ff3f5e6b6ad2d650cea99f4147160eabd5096e4c4f771720ab2f2a5e90', 'Zwiers', NULL, 'Gerben', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1),
('M', 'karin@dejongefinancieelconsult.nl', 'a6150b15241f6055d9b90e3266a817baaa4cc87716f3b6020db70f56914e1cff70b4b5a68895fc97076888551eb7dd939092e62b82d29fa2e00dfff35a560b77', 'c499eeef1311f0c03c3d6b5ce83e31d93e8511cbb4166bbe43f556531de80ff11ff6a40001f4de234f828ab01a1524907978fde34dd06733745e38f1aa87ea64', 'Vooys', NULL, 'Karin', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);

--changeset Patrick Heidotting:18
CREATE TABLE IF NOT EXISTS `SCHADE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATUMAFGEHANDELD` date DEFAULT NULL,
  `DATUMTIJDMELDING` datetime NOT NULL,
  `DATUMTIJD` datetime NOT NULL,
  `EIGENRISICO` double DEFAULT NULL,
  `LOCATIE` varchar(50) DEFAULT NULL,
  `OMSCHRIJVING` longtext,
  `SCHADENUMMERMAATSCHAPPIJ` varchar(25) NOT NULL,
  `SCHADENUMMERTUSSENPERSOON` varchar(25) DEFAULT NULL,
  `POLIS` bigint(20) NOT NULL,
  `SOORT` bigint(20) NOT NULL,
  `STATUS` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK9174198AE10F3258` (`POLIS`),
  KEY `FK9174198ADC1B1675` (`SOORT`),
  KEY `FK9174198A459BBDCD` (`STATUS`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `SOORTSCHADE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `INGEBRUIK` tinyint(1) DEFAULT NULL,
  `OMSCHRIJVING` varchar(250) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `STATUSSCHADE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `INGEBRUIK` tinyint(1) DEFAULT NULL,
  `STATUS` varchar(250) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Aanrijding met wild');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Aanrijding schuldschade');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Aanrijding verhaalschade');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Aansprijkelijkheidschade');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Diefstalscahde');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Inbraad zonder braakschade');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Inbraad met braakschade');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Rechtsbijstanddekking soc/werk');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Ruitbreuk');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Stormschade');
INSERT INTO `SOORTSCHADE` (`ID`, `INGEBRUIK`, `OMSCHRIJVING`) VALUES (NULL, '1', 'Vandalismeschade');

INSERT INTO `STATUSSCHADE` (`ID`, `INGEBRUIK`, `STATUS`) VALUES (NULL, '1', 'In behandeling maatschappij');
INSERT INTO `STATUSSCHADE` (`ID`, `INGEBRUIK`, `STATUS`) VALUES (NULL, '1', 'Afgehandeld maatschappij');
INSERT INTO `STATUSSCHADE` (`ID`, `INGEBRUIK`, `STATUS`) VALUES (NULL, '1', 'In behandeling tussenpersoon');
INSERT INTO `STATUSSCHADE` (`ID`, `INGEBRUIK`, `STATUS`) VALUES (NULL, '1', 'Afgehandeld tussenpersoon');

--changeset Patrick Heidotting:19
DROP TABLE `OPMERKING`;
CREATE TABLE IF NOT EXISTS `OPMERKING` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPMERKING` text,
  `TIJD` datetime DEFAULT NULL,
  `KANTOOR` bigint(20) DEFAULT NULL,
  `POLIS` bigint(20) DEFAULT NULL,
  `RELATIE` bigint(20) DEFAULT NULL,
  `SCHADE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK9B213750ED5811CD` (`KANTOOR`),
  KEY `FK9B213750E10F3258` (`POLIS`),
  KEY `FK9B213750DF4C9431` (`RELATIE`),
  KEY `FK9B213750A491F7AB` (`SCHADE`),
  KEY `FK9B213750F9DD9013` (`SCHADE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--changeset Patrick Heidotting:20
UPDATE  `SOORTSCHADE` SET  `OMSCHRIJVING` =  'Inbraak zonder braakschade' WHERE  `SOORTSCHADE`.`ID` =6;
UPDATE  `SOORTSCHADE` SET  `OMSCHRIJVING` =  'Inbraak met braakschade' WHERE  `SOORTSCHADE`.`ID` =7;
UPDATE  `SOORTSCHADE` SET  `OMSCHRIJVING` =  'Aansprakelijkheidschade' WHERE  `SOORTSCHADE`.`ID` =4;

--changeset Patrick Heidotting:21
ALTER TABLE `SCHADE` ADD COLUMN `SOORTSCHADEONGEDEFINIEERD` varchar(100) DEFAULT NULL AFTER `SOORT`;

--changeset Patrick Heidotting:22
ALTER TABLE  `SCHADE` CHANGE  `SOORT`  `SOORT` bigint(20) NULL DEFAULT NULL;

--changeset Patrick Heidotting:23
UPDATE  `SOORTSCHADE` SET  `OMSCHRIJVING` =  'Diefstalschade' WHERE  `SOORTSCHADE`.`ID` =5;

--changeset Patrick Heidotting:24
DROP TABLE `OPMERKING`;
CREATE TABLE IF NOT EXISTS `OPMERKING` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OPMERKING` text,
  `TIJD` datetime DEFAULT NULL,
  `KANTOOR` bigint(20) DEFAULT NULL,
  `MEDEWERKER` bigint(20) NOT NULL,
  `POLIS` bigint(20) DEFAULT NULL,
  `RELATIE` bigint(20) DEFAULT NULL,
  `SCHADE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK9B213750ED5811CD` (`KANTOOR`),
  KEY `FK9B21375038AD3919` (`MEDEWERKER`),
  KEY `FK9B213750E10F3258` (`POLIS`),
  KEY `FK9B213750DF4C9431` (`RELATIE`),
  KEY `FK9B213750A491F7AB` (`SCHADE`),
  KEY `FK9B213750F9DD9013` (`SCHADE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--changeset Patrick Heidotting:25
ALTER TABLE `BIJLAGE` ADD COLUMN `SCHADE` bigint(20) DEFAULT NULL AFTER `POLIS`;

--changeset Patrick Heidotting:26
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE `HYPOTHEEK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RENTE` double DEFAULT NULL,
  `DUUR` bigint(20) DEFAULT NULL,
  `DUURRENTEVASTEPERIODE` bigint(20) DEFAULT NULL,
  `EINDDATUM` date DEFAULT NULL,
  `EINDDATUMRENTEVASTEPERIODE` date DEFAULT NULL,
  `HYPOTHEEKBEDRAG` double DEFAULT NULL,
  `INGANGSDATUM` date DEFAULT NULL,
  `INGANGSDATUMRENTEVASTEPERIODE` date DEFAULT NULL,
  `KOOPSOM` double DEFAULT NULL,
  `MARKTWAARDE` double DEFAULT NULL,
  `OMSCHRIJVING` longtext NOT NULL,
  `ONDERPAND` double DEFAULT NULL,
  `TAXATIEDATUM` date DEFAULT NULL,
  `VRIJEVERKOOPWAARDE` double DEFAULT NULL,
  `WAARDENAVERBOUWING` double DEFAULT NULL,
  `WAARDEVOORVERBOUWING` double DEFAULT NULL,
  `WOZWAARDE` double DEFAULT NULL,
  `SOORT` bigint(20) DEFAULT NULL,
  `RELATIE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKE40AABE7EB463986` (`SOORT`),
  KEY `FKE40AABE7DF4C9431` (`RELATIE`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

CREATE TABLE `SOORTHYPOTHEEK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `INGEBRUIK` tinyint(1) DEFAULT NULL,
  `OMSCHRIJVING` varchar(250) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--changeset Patrick Heidotting:27
INSERT INTO `SOORTHYPOTHEEK` (`INGEBRUIK`, `OMSCHRIJVING`) VALUES ('1', 'Annuiteiten');
INSERT INTO `SOORTHYPOTHEEK` (`INGEBRUIK`, `OMSCHRIJVING`) VALUES ('1', 'Lineair');
INSERT INTO `SOORTHYPOTHEEK` (`INGEBRUIK`, `OMSCHRIJVING`) VALUES ('1', 'Aflossingsvrij');
INSERT INTO `SOORTHYPOTHEEK` (`INGEBRUIK`, `OMSCHRIJVING`) VALUES ('1', 'Bankspaar');
INSERT INTO `SOORTHYPOTHEEK` (`INGEBRUIK`, `OMSCHRIJVING`) VALUES ('1', 'Spaar');
INSERT INTO `SOORTHYPOTHEEK` (`INGEBRUIK`, `OMSCHRIJVING`) VALUES ('1', 'Hybride');
INSERT INTO `SOORTHYPOTHEEK` (`INGEBRUIK`, `OMSCHRIJVING`) VALUES ('1', 'Levens');

--changeset Patrick Heidotting:28
ALTER TABLE  `HYPOTHEEK` CHANGE  `RENTE`  `RENTE` INT( 11 ) NULL DEFAULT NULL;

--changeset Patrick Heidotting:29
ALTER TABLE  `HYPOTHEEK` CHANGE  `SOORT`  `SOORT` BIGINT( 20 ) NOT NULL;
ALTER TABLE  `HYPOTHEEK` CHANGE  `OMSCHRIJVING`  `OMSCHRIJVING` LONGTEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL;

--changeset Patrick Heidotting:30
ALTER TABLE `OPMERKING` ADD COLUMN HYPOTHEEK bigint(20) DEFAULT NULL;

--changeset Patrick Heidotting:31
ALTER TABLE `BIJLAGE` ADD COLUMN HYPOTHEEK bigint(20) DEFAULT NULL;

--changeset Patrick Heidotting:32
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


CREATE TABLE IF NOT EXISTS `TAAK` (
  `SOORT` varchar(1) NOT NULL,
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATUMTIJDAFRONDEN` datetime DEFAULT NULL,
  `DATUMTIJDCREATIE` datetime DEFAULT NULL,
  `DATUMTIJDOPPAKKEN` datetime DEFAULT NULL,
  `EINDDATUM` date DEFAULT NULL,
  `OMSCHRIJVING` longtext,
  `STATUS` varchar(1) DEFAULT NULL,
  `AANGEMAAKTDOOR` bigint(20) DEFAULT NULL,
  `GERELATEERDAAN` bigint(20) DEFAULT NULL,
  `GERELATEERDETAAK` bigint(20) DEFAULT NULL,
  `OPGEPAKTDOOR` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK272B5744A37744` (`AANGEMAAKTDOOR`),
  KEY `FK272B57A4272B97` (`GERELATEERDAAN`),
  KEY `FK272B577A6AF112` (`GERELATEERDETAAK`),
  KEY `FK272B57F0519493` (`OPGEPAKTDOOR`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `TAAKRELATIES` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `GERELATEERDOBJECT` bigint(20) DEFAULT NULL,
  `SOORT` varchar(1) DEFAULT NULL,
  `TAAK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKD8A7CD228242DF6D` (`TAAK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--changeset Patrick Heidotting:33
ALTER TABLE `POLIS` ADD COLUMN EINDDATUM date DEFAULT NULL after `INGANGSDATUM`;

--changeset Patrick Heidotting:34
ALTER TABLE  `HYPOTHEEK` CHANGE  `ONDERPAND`  `ONDERPAND` VARCHAR( 255 ) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL;
ALTER TABLE `HYPOTHEEK` ADD COLUMN LENINGNUMMER VARCHAR(50) DEFAULT NULL;
ALTER TABLE `HYPOTHEEK` ADD COLUMN BANK bigint(20) DEFAULT NULL;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
CREATE TABLE IF NOT EXISTS `BANK` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAAM` varchar(50) DEFAULT NULL,
  `TONEN` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

INSERT INTO `BANK` (`ID`, `NAAM`, `TONEN`) VALUES
(1, 'SNS', 1),
(2, 'ABN', 1);

--changeset Patrick Heidotting:35
CREATE TABLE `HYPOTHEEKPAKKET` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `RELATIE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK4618C627DF4C9431` (`RELATIE`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;
ALTER TABLE `HYPOTHEEK` ADD COLUMN PAKKET bigint(20) DEFAULT NULL;

--changeset Patrick Heidotting:36
ALTER TABLE `HYPOTHEEK` ADD COLUMN `BOXI` double DEFAULT NULL;
ALTER TABLE `HYPOTHEEK` ADD COLUMN `BOXIII` double DEFAULT NULL;


