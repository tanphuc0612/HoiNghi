drop database UngDung;
CREATE DATABASE IF NOT EXISTS UngDung;
USE UngDung;

CREATE TABLE IF NOT EXISTS HoiNghi (
	MaHoiNghi int not null AUTO_INCREMENT,
	Ten VARCHAR(255) NOT NULL,
    MoTaNgan VARCHAR(255),
    MoTaChiTiet VARCHAR(255),
    Anh int,
    NguoiThamDu int,
    PRIMARY KEY (MaHoiNghi)
);

CREATE TABLE IF NOT EXISTS DiaDiem (
	MaDiaDiem int NOT NULL AUTO_INCREMENT,
	Ten VARCHAR(255) NOT NULL,
	DiaChi VARCHAR(255) NOT NULL UNIQUE,
    SucChua int NOT NULL,
	PRIMARY KEY (MaDiaDiem)
);

CREATE TABLE IF NOT EXISTS ToChuc (
	MaHoiNghi int not null,
    ThoiGianToChuc date not null,
    MaDiaDiem int not null,
    PRIMARY KEY (MaDiaDiem,ThoiGianToChuc),
    FOREIGN KEY (MaDiaDiem)
        REFERENCES DiaDiem (MaDiaDiem)
        ON UPDATE RESTRICT ON DELETE CASCADE,
	FOREIGN KEY (MaHoiNghi)
        REFERENCES HoiNghi (MaHoiNghi)
        ON UPDATE RESTRICT ON DELETE CASCADE
);	

CREATE TABLE IF NOT EXISTS Ad (
	UserName VARCHAR(255) NOT NULL,
	TenAd VARCHAR(255) NOT NULL,
	Pass VARCHAR(255) NOT NULL,
    email CHAR(255) NOT NULL,
	PRIMARY KEY (UserName)
);

CREATE TABLE IF NOT EXISTS NguoiDung (
	UserName VARCHAR(255) NOT NULL,
	TenUser VARCHAR(255) NOT NULL,
	Pass VARCHAR(255) NOT NULL,
    email CHAR(255) NOT NULL,
	PRIMARY KEY (UserName)
);

CREATE TABLE IF NOT EXISTS LichSuDangKy (
	UserName VARCHAR(255) NOT NULL,
	MaHoiNghi int not null,
    NgayDangKy date,
	PRIMARY KEY (UserName,MaHoiNghi)
);

DELIMITER $$
CREATE TRIGGER trg_insert_ad
BEFORE INSERT
ON Ad FOR EACH ROW
BEGIN
	SET New.Pass = MD5(NEW.Pass+NEW.UserName);
END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER trg_insert_nguoiDung
BEFORE INSERT
ON NguoiDung FOR EACH ROW
BEGIN
	SET New.Pass = MD5(NEW.Pass+NEW.UserName);
END $$
DELIMITER ;

INSERT INTO HoiNghi Values(1,'hội nghị 1',null,null,1,0);
INSERT INTO HoiNghi Values(2,'hội nghị 2',null,null,2,0);
INSERT INTO HoiNghi Values(3,'hội nghị 3',null,null,3,0);
INSERT INTO HoiNghi Values(4,'hội nghị 4',null,null,4,0);
INSERT INTO HoiNghi Values(5,'hội nghị 5',null,null,5,0);

INSERT INTO DiaDiem Values(1,'Trung tâm 1','123 phạm văn đồng',100);
INSERT INTO DiaDiem Values(2,'Trung tâm 2','456 chu văn an',200);
INSERT INTO DiaDiem Values(3,'Trung tâm 3','789 huỳnh văn bánh',150);

INSERT INTO ToChuc Values(1,DATE("2020-07-07"),1);
INSERT INTO ToChuc Values(2,DATE("2020-07-08"),2);

INSERT INTO Ad Values('admin','Nguyễn Văn AD','1','123@gmail.com');

INSERT INTO NguoiDung Values('user1','Nguyễn Văn Bánh','1','pokemon@gmail.com');

SELECT HOINGHI.MAHOINGHI, HOINGHI.TEN, TOCHUC.THOIGIANTOCHUC, HOINGHI.MOTANGAN
FROM HOINGHI LEFT JOIN TOCHUC ON HOINGHI.MAHOINGHI = TOCHUC.MAHOINGHI;