-- Run in MySQL Workbench on a database where you have permission.
-- Safe for repeated practice runs; seeded IDs/tags are ignored if already present.
CREATE DATABASE IF NOT EXISTS farm_exam CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE farm_exam;

CREATE TABLE IF NOT EXISTS farmers (
  farmer_id INT PRIMARY KEY AUTO_INCREMENT,
  full_name VARCHAR(100) NOT NULL,
  village VARCHAR(80) NOT NULL,
  phone VARCHAR(25)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS livestock (
  animal_id INT PRIMARY KEY AUTO_INCREMENT,
  tag_number VARCHAR(30) NOT NULL UNIQUE,
  species VARCHAR(20) NOT NULL,
  breed VARCHAR(80) NOT NULL,
  sex VARCHAR(10) NOT NULL,
  date_of_birth DATE NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'Active',
  farmer_id INT NOT NULL,
  CONSTRAINT fk_livestock_farmer FOREIGN KEY (farmer_id)
    REFERENCES farmers(farmer_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT chk_species CHECK (species IN ('Cattle','Goat','Sheep','Chicken','Donkey')),
  CONSTRAINT chk_sex CHECK (sex IN ('Female','Male')),
  CONSTRAINT chk_status CHECK (status IN ('Active','Sold','Deceased')),
  INDEX idx_livestock_farmer (farmer_id)
) ENGINE=InnoDB;

INSERT IGNORE INTO farmers (farmer_id,full_name,village,phone)
VALUES (1,'Kabo Dube','Mochudi','71234567'),
       (2,'Naledi Molefe','Maun','72345678'),
       (3,'Thato Kgosi','Serowe','73456789');

INSERT IGNORE INTO livestock
 (animal_id,tag_number,species,breed,sex,date_of_birth,status,farmer_id)
VALUES
 (1,'BW-C001','Cattle','Brahman','Female','2023-03-10','Active',1),
 (2,'BW-C002','Cattle','Tswana','Male','2024-01-17','Active',1),
 (3,'BW-G001','Goat','Boer','Female','2025-02-02','Active',2),
 (4,'BW-S001','Sheep','Dorper','Female','2024-09-08','Active',1),
 (5,'BW-D001','Donkey','Local','Male',NULL,'Active',3);

SELECT f.full_name, f.village, a.tag_number, a.species, a.status
FROM farmers f JOIN livestock a ON f.farmer_id=a.farmer_id
ORDER BY f.full_name, a.tag_number;