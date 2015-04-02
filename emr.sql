/*
MySQL Data Transfer
Source Host: localhost
Source Database: emr
Target Host: localhost
Target Database: emr
Date: 2012-07-08 11:03:12 AM
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for allergy
-- ----------------------------
DROP TABLE IF EXISTS `allergy`;
CREATE TABLE `allergy` (
  `id` int(11) NOT NULL auto_increment,
  `allergy` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for disease
-- ----------------------------
DROP TABLE IF EXISTS `disease`;
CREATE TABLE `disease` (
  `id` int(11) NOT NULL auto_increment,
  `type` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `id` int(11) NOT NULL auto_increment,
  `nsn` int(11) default NULL,
  `speciality` int(11) default NULL,
  `first_name` varchar(50) character set latin1 default NULL,
  `last_name` varchar(50) character set latin1 default NULL,
  `birth_date` date default NULL,
  `place_of_birth` varchar(75) character set latin1 default NULL,
  `father_name` varchar(50) character set latin1 default NULL,
  `mother_name` varchar(50) character set latin1 default NULL,
  `gender` varchar(20) character set latin1 default NULL,
  `phone_number` int(20) default NULL,
  `email` varchar(20) character set latin1 default NULL,
  `marital_status` varchar(20) character set latin1 default NULL,
  `nationality` varchar(20) character set latin1 default NULL,
  `address` varchar(50) character set latin1 default NULL,
  `username` varchar(50) character set latin1 default NULL,
  `password` varchar(50) character set latin1 default NULL,
  `type` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `specialityFK` (`speciality`),
  CONSTRAINT `specialityFK` FOREIGN KEY (`speciality`) REFERENCES `specialist` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for medical_treatment
-- ----------------------------
DROP TABLE IF EXISTS `medical_treatment`;
CREATE TABLE `medical_treatment` (
  `id` int(11) NOT NULL auto_increment,
  `medication` int(11) default NULL,
  `dose` varchar(20) default NULL,
  `frequency` varchar(20) default NULL,
  `treatement` int(11) default NULL,
  `start_date` date default NULL,
  `end_date` date default NULL,
  PRIMARY KEY  (`id`),
  KEY `medicinFK` (`medication`),
  KEY `treatementFK` (`treatement`),
  CONSTRAINT `medicinFK` FOREIGN KEY (`medication`) REFERENCES `medicine` (`id`),
  CONSTRAINT `treatementFK` FOREIGN KEY (`treatement`) REFERENCES `treatment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine` (
  `id` int(11) NOT NULL auto_increment,
  `scientific_name` varchar(50) default NULL,
  `commercial_name` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for nurse
-- ----------------------------
DROP TABLE IF EXISTS `nurse`;
CREATE TABLE `nurse` (
  `id` int(11) NOT NULL auto_increment,
  `first_name` varchar(50) default NULL,
  `last_name` varchar(50) default NULL,
  `username` varchar(50) default NULL,
  `password` varchar(50) default NULL,
  `phone` int(11) default NULL,
  `gender` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for patient_demographic
-- ----------------------------
DROP TABLE IF EXISTS `patient_demographic`;
CREATE TABLE `patient_demographic` (
  `id` int(11) NOT NULL auto_increment,
  `nsn` int(11) NOT NULL default '0',
  `blood_type` varchar(10) default NULL,
  `dna_form` varchar(20) default NULL,
  `first_name` varchar(50) default NULL,
  `last_name` varchar(50) default NULL,
  `birth_date` date default NULL,
  `place_of_birth` varchar(75) default NULL,
  `father_name` varchar(50) default NULL,
  `mother_name` varchar(50) default NULL,
  `gender` varchar(20) default NULL,
  `phone_number` int(20) default NULL,
  `email` varchar(20) default NULL,
  `marital_status` varchar(20) default NULL,
  `nationality` varchar(20) default NULL,
  `address` varchar(50) default NULL,
  `username` varchar(50) default NULL,
  `password` varchar(50) default NULL,
  PRIMARY KEY  (`id`,`nsn`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for physical_therapy
-- ----------------------------
DROP TABLE IF EXISTS `physical_therapy`;
CREATE TABLE `physical_therapy` (
  `id` int(11) NOT NULL auto_increment,
  `note` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for specialist
-- ----------------------------
DROP TABLE IF EXISTS `specialist`;
CREATE TABLE `specialist` (
  `id` int(11) NOT NULL auto_increment,
  `type` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for surgery
-- ----------------------------
DROP TABLE IF EXISTS `surgery`;
CREATE TABLE `surgery` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `note` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  `type` varchar(20) default NULL,
  `result` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for treatment
-- ----------------------------
DROP TABLE IF EXISTS `treatment`;
CREATE TABLE `treatment` (
  `id` int(11) NOT NULL auto_increment,
  `type` int(11) default NULL,
  `physical` int(11) default NULL,
  `surgery` int(11) default NULL,
  `medical` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `physicalFK` (`physical`),
  KEY `surgeryFK` (`surgery`),
  KEY `medicalFK` (`medical`),
  CONSTRAINT `medicalFK` FOREIGN KEY (`medical`) REFERENCES `medical_treatment` (`id`),
  CONSTRAINT `physicalFK` FOREIGN KEY (`physical`) REFERENCES `physical_therapy` (`id`),
  CONSTRAINT `surgeryFK` FOREIGN KEY (`surgery`) REFERENCES `surgery` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for visit
-- ----------------------------
DROP TABLE IF EXISTS `visit`;
CREATE TABLE `visit` (
  `id` int(11) NOT NULL auto_increment,
  `date` date default NULL,
  `diagnosis` varchar(250) default NULL,
  `patient` int(11) default NULL,
  `doctor` int(11) default NULL,
  `test` int(11) default NULL,
  `vital_signs` int(11) default NULL,
  `xray` int(11) default NULL,
  `disease` int(11) default NULL,
  `allergy` int(11) default NULL,
  `treatment` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `patientFK` (`patient`),
  KEY `testFK` (`test`),
  KEY `vitalFK` (`vital_signs`),
  KEY `xrayFK` (`xray`),
  KEY `diseaseFK` (`disease`),
  KEY `allergyFK` (`allergy`),
  KEY `treatmentFK` (`treatment`),
  KEY `doctor` (`doctor`),
  CONSTRAINT `allergyFK` FOREIGN KEY (`allergy`) REFERENCES `allergy` (`id`),
  CONSTRAINT `diseaseFK` FOREIGN KEY (`disease`) REFERENCES `disease` (`id`),
  CONSTRAINT `patientFK` FOREIGN KEY (`patient`) REFERENCES `patient_demographic` (`id`),
  CONSTRAINT `testFK` FOREIGN KEY (`test`) REFERENCES `test` (`id`),
  CONSTRAINT `treatmentFK` FOREIGN KEY (`treatment`) REFERENCES `treatment` (`id`),
  CONSTRAINT `visit_ibfk_1` FOREIGN KEY (`doctor`) REFERENCES `doctor` (`id`),
  CONSTRAINT `vitalFK` FOREIGN KEY (`vital_signs`) REFERENCES `vital_signs` (`id`),
  CONSTRAINT `xrayFK` FOREIGN KEY (`xray`) REFERENCES `xray` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for vital_signs
-- ----------------------------
DROP TABLE IF EXISTS `vital_signs`;
CREATE TABLE `vital_signs` (
  `id` int(11) NOT NULL auto_increment,
  `pressure` varchar(15) default NULL,
  `temperature` int(11) default NULL,
  `pulsation` varchar(15) default NULL,
  `conscious` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for xray
-- ----------------------------
DROP TABLE IF EXISTS `xray`;
CREATE TABLE `xray` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(30) default NULL,
  `type` varchar(30) default NULL,
  `result` varchar(100) default NULL,
  `photo` blob,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `allergy` VALUES ('1', 'allergy');
INSERT INTO `allergy` VALUES ('2', 'allergy');
INSERT INTO `allergy` VALUES ('3', 'allergy');
INSERT INTO `allergy` VALUES ('4', 'allergy');
INSERT INTO `allergy` VALUES ('5', 'allergy');
INSERT INTO `allergy` VALUES ('6', 'allergy');
INSERT INTO `allergy` VALUES ('7', 'allergy');
INSERT INTO `allergy` VALUES ('8', 'allergy');
INSERT INTO `allergy` VALUES ('9', 'vvv');
INSERT INTO `allergy` VALUES ('10', '??');
INSERT INTO `allergy` VALUES ('11', 'm');
INSERT INTO `allergy` VALUES ('12', 'allergy');
INSERT INTO `disease` VALUES ('1', 'Nerve, nerve root and plexus disorders');
INSERT INTO `disease` VALUES ('2', 'Infectious arthropathies');
INSERT INTO `disease` VALUES ('3', 'Systemic connective tissue disorders');
INSERT INTO `disease` VALUES ('4', 'Deforming dorsopathies');
INSERT INTO `disease` VALUES ('5', 'Inflammatory polyathropathies');
INSERT INTO `doctor` VALUES ('1', '2', '1', 'doctor', 'doctor', null, null, null, null, null, null, null, null, null, null, 'doc1', 'doc1', null);
INSERT INTO `doctor` VALUES ('2', null, '1', 'admin', 'admin', null, '', '', '', '', null, '', '', '', '', 'admin', 'admin', '1');
INSERT INTO `doctor` VALUES ('9', null, null, 'doctor1', 'doctor1', null, null, null, null, null, null, null, null, null, null, 'doc2', 'doc2', null);
INSERT INTO `medical_treatment` VALUES ('1', null, '12', '12', null, null, null);
INSERT INTO `medical_treatment` VALUES ('2', null, '12', '12', null, null, null);
INSERT INTO `medical_treatment` VALUES ('3', '1', '12', '12', null, null, null);
INSERT INTO `medical_treatment` VALUES ('4', '2', '12', '12', null, null, null);
INSERT INTO `medical_treatment` VALUES ('5', '3', '12', '12', null, null, null);
INSERT INTO `medical_treatment` VALUES ('6', '3', '12', 'dsfs', null, null, null);
INSERT INTO `medical_treatment` VALUES ('7', '1', '2', '2', null, '2010-10-09', '2012-10-09');
INSERT INTO `medical_treatment` VALUES ('8', '1', '12', '2', null, '2010-10-09', '2012-10-09');
INSERT INTO `medical_treatment` VALUES ('9', null, '', '', null, null, null);
INSERT INTO `medical_treatment` VALUES ('10', null, '', '', null, null, null);
INSERT INTO `medical_treatment` VALUES ('11', '1', '12', '', null, null, null);
INSERT INTO `medical_treatment` VALUES ('12', null, '', '', null, null, null);
INSERT INTO `medicine` VALUES ('1', 'Vitramine', 'vita');
INSERT INTO `medicine` VALUES ('2', 'Citamol', 'cita');
INSERT INTO `medicine` VALUES ('3', 'Panadol', 'pana');
INSERT INTO `nurse` VALUES ('1', 'Nurse', 'nurse', 'nur1', 'nur1', '93777777', 'female');
INSERT INTO `nurse` VALUES ('2', 'Nurse2', 'nurse2', 'nur2', 'nur2', '932785412', 'male');
INSERT INTO `patient_demographic` VALUES ('1', '123', 'A+', '', 'patient', 'patient', '1990-03-30', 'Damascus', 'Mohammad', '', 'male', '932222222', '', '', 'Syrian', 'Baramkeh', 'pat1', 'pat1');
INSERT INTO `patient_demographic` VALUES ('3', '321', 'O-', '', 'patient2', '', '1984-05-19', 'Latakia', '', '', 'female', '943333333', '', '', 'Syrian', '', 'pat2', 'pat2');
INSERT INTO `physical_therapy` VALUES ('1', 'Physical 1');
INSERT INTO `physical_therapy` VALUES ('2', 'Physical 2');
INSERT INTO `physical_therapy` VALUES ('3', 'Physical 3');
INSERT INTO `specialist` VALUES ('1', 'Heart');
INSERT INTO `specialist` VALUES ('2', 'Eyes');
INSERT INTO `specialist` VALUES ('3', 'Enternal');
INSERT INTO `specialist` VALUES ('4', 'Surgery');
INSERT INTO `surgery` VALUES ('1', 'Heart Sergery', 'heart');
INSERT INTO `surgery` VALUES ('2', 'Head Sergery', 'head');
INSERT INTO `surgery` VALUES ('3', 'Nerve Sergery', 'nerve');
INSERT INTO `treatment` VALUES ('1', null, '1', '1', '9');
INSERT INTO `treatment` VALUES ('2', null, '1', '1', '10');
INSERT INTO `treatment` VALUES ('3', null, '3', '3', '11');
INSERT INTO `treatment` VALUES ('4', null, '3', '3', '12');
INSERT INTO `visit` VALUES ('1', '2012-07-04', 'permenant', '1', '2', null, null, null, '1', null, null);
INSERT INTO `visit` VALUES ('2', '2012-07-04', '', '1', '2', null, null, null, '2', null, null);
INSERT INTO `visit` VALUES ('3', '2012-07-04', '', '1', '2', null, null, null, '3', null, null);
INSERT INTO `visit` VALUES ('4', '2012-07-04', '', '1', '2', null, null, null, null, null, '2');
INSERT INTO `visit` VALUES ('5', '2012-07-04', '', '1', '2', null, null, null, '4', null, '3');
INSERT INTO `visit` VALUES ('6', '2012-07-04', '', '1', '2', null, null, null, '5', null, '4');
