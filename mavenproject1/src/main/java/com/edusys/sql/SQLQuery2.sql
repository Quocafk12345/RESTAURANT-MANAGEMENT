CREATE DATABASE RestaurantManagement2
USE RestaurantManagement2

--Thống kê doanh thu theo ngày
CREATE PROCEDURE ThongKeDoanhThuTheoNgay
AS
BEGIN
    SELECT ThoiGianLap, SUM(TongTien) AS DoanhThuNgay
    FROM HoaDon
    GROUP BY ThoiGianLap
    ORDER BY ThoiGianLap;
END;

--Thống kê doanh thu theo bàn ăn
CREATE PROCEDURE ThongKeDoanhThuTheoBanAn
AS
BEGIN
    SELECT MaSoBan, SUM(TongTien) AS DoanhThuTheoBan
    FROM HoaDon
    GROUP BY MaSoBan
    ORDER BY DoanhThuTheoBan DESC;
END;

--Thống kê doanh thu theo tháng
CREATE PROCEDURE ThongKeDoanhThuTheoBanAn
AS
BEGIN
    SELECT MaSoBan, SUM(TongTien) AS DoanhThuTheoBan
    FROM HoaDon
    GROUP BY MaSoBan
    ORDER BY DoanhThuTheoBan DESC;
END;

CREATE TABLE LoaiThucDon
(
    MaLoai INT PRIMARY KEY NOT NULL,
    Nhom NVARCHAR(20),
    TenLoai NVARCHAR(50)
);

CREATE TABLE Mon
(
    MaMon INT PRIMARY KEY IDENTITY NOT NULL,
    TenMon NVARCHAR(50),
    DonGia FLOAT,
    NgayADGia DATE,
    DonViTinh NVARCHAR(40),
    MaLoai INT,
	TenLoai NVARCHAR(50),
    Hinh NVARCHAR(255),
    FOREIGN KEY (MaLoai) REFERENCES LoaiThucDon(MaLoai)
);


CREATE TABLE BanAn
(
    MaSoBan NVARCHAR(20) PRIMARY KEY NOT NULL,
	TrangThaiThanhToan NVARCHAR (20) 
);


CREATE TABLE PhanCong 
(
	MaPhanCong INT IDENTITY PRIMARY KEY,
	CaLam INT,
	NgayPC Date
)

CREATE TABLE NhanVien
(
    MaNV NVARCHAR(20) PRIMARY KEY NOT NULL,
    TenNV NVARCHAR(50),
    NgaySinh DATE,
    SDT NVARCHAR(20),
    TenDN NVARCHAR(50),
    CaLam INT,
    Email NVARCHAR(50),
    GioiTinh BIT,
    MatKhau NVARCHAR(MAX),
    VaiTro BIT,
	MaSoBan NVARCHAR(20),
	MaOTP NVARCHAR(50),
	FOREIGN KEY(MaSoBan) REFERENCES BanAn(MaSoBan),
	MaPhanCong INT FOREIGN KEY REFERENCES PhanCong(MaPhanCong)
);
CREATE TABLE HoaDon
(
    SoHD INT primary key NOT NULL,
	TenKhachHang NVARCHAR(MAX),
	SDTKhachHang NVARCHAR(20),
    ThoiGianLap DATE,
	MaSoBan NVARCHAR(20),
    MaNV NVARCHAR(20),
	TongTien FLOAT,
	TienMat FLOAT,
	TienDu FLOAT,
	TrangThaiThanhToan NVARCHAR(20),
	FOREIGN KEY (MaSoBan) REFERENCES BanAn(MaSoBan),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

CREATE TABLE ChiTietHoaDon
(
	MaCTHD INT IDENTITY PRIMARY KEY NOT NULL,
    MaSoBan NVARCHAR(20),
    TenLoai NVARCHAR(50),
    TenMon NVARCHAR(50),
    DonGia FLOAT,
    SoLuong INT,
	SoHD INT,
	ThoiGianLap DATE,
	FOREIGN KEY (SoHD) REFERENCES HoaDon(SoHD),
    FOREIGN KEY (MaSoBan) REFERENCES BanAn(MaSoBan)
);
CREATE TABLE DatMon
(
	MaOrder INT IDENTITY PRIMARY KEY NOT NULL,
    MaSoBan NVARCHAR(20),
    TenLoai NVARCHAR(50),
    TenMon NVARCHAR(50),
    DonGia FLOAT,
    SoLuong INT,
    ThoiGianLap DATE,
	SoHD INT FOREIGN KEY (SoHD) REFERENCES HoaDon(SoHD),
    FOREIGN KEY (MaSoBan) REFERENCES BanAn(MaSoBan)
);



--@INSERT INTO LoaiThucDon
-- Thêm 4 nhóm món thực đơn
INSERT INTO LoaiThucDon (MaLoai, Nhom, TenLoai)
VALUES (1, N'Món ăn chính', N'Món ăn chính'),
       (2, N'Món khai vị', N'Món khai vị'),
       (3, N'Đồ uống', N'Đồ uống'),
       (4, N'Món tráng miệng', N'Món tráng miệng');
--@INSERT INTO THUCDON
-- Thêm 10 món ăn 
-- Thêm 10 món ăn chính
INSERT INTO Mon(TenMon, DonGia, NgayADGia, DonViTinh, TenLoai, MaLoai, Hinh)
VALUES
    (N'Cá chẽm hấp gia vị Triều Châu', 150000, '2023-11-13', N'đĩa', N'Món ăn chính', 1,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\CaChemHapGiaViTrieuChau.jpg'),
    (N'Lẩu gà vị hoa điêu', 120000, '2023-11-13', N'đĩa', N'Món ăn chính', 1, N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\LauGaViHoaDieu.jpg'),
    (N'Gà tiềm bào ngư và trúc sinh', 180000, '2023-11-13', N'đĩa', N'Món ăn chính', 1,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\GaTiemBaoNgu.jpg'),
    (N'Bò Úc xốt rượu vang Pháp', 200000, '2023-11-13', N'đĩa', N'Món ăn chính', 1,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\BoUcXotRuouVang.jpg'),
(N'Gỏi củ hủ dừa tôm thịt và rau mùi', 90000, '2023-11-13', N'đĩa', N'Món ăn chính', 1,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\GoiCuHuDuaTom.jpg'),
    (N'Súp hải sản bích ngọc', 120000, '2023-11-13', N'đĩa', N'Món ăn chính', 1,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\SupHaiSan.jpg'),
    (N'Sườn non nướng kiểu Bắc Kinh', 150000, '2023-11-13', N'đĩa', N'Món ăn chính', 1,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\SuonNonNuongBK.jpg'),
    (N'Xôi gấc', 50000, '2023-11-13', N'đĩa', N'Món ăn chính', 1, N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\XoiGat.png'),
    (N'Vịt hầm ngũ quả', 250000, '2023-11-13', N'đĩa', N'Món ăn chính', 1,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\VitHam.jpg'),
    (N'Trứng muối', 30000, '2023-11-13', N'đĩa', N'Món ăn chính', 1,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\MonChinh\TrungMuoi.jpg');

-- Thêm 5 món khai vị
INSERT INTO Mon(TenMon, DonGia, NgayADGia, DonViTinh, TenLoai, MaLoai, Hinh)
VALUES 
    (N'Bánh nướng', 50000, '2023-11-13', N'đĩa', N'Món khai vị', 2,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\KhaiVi\BanhNuong.jpg'),
    (N'Xúc xích tươi', 60000, '2023-11-13', N'đĩa', N'Món khai vị', 2,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\KhaiVi\XucXich.jpg'),
    (N'Chả giò trái cây', 70000, '2023-11-13', N'đĩa', N'Món khai vị', 2,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\KhaiVi\ChaGio.jpg'),
    (N'BRUSCHETTA', 80000, '2023-11-13', N'đĩa', N'Món khai vị', 2,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\KhaiVi/BRUSCHETTA.jpg'),
    (N'Saganaki', 90000, '2023-11-13', N'đĩa', N'Món khai vị', 2,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\KhaiVi\Saganaki.jpg');

-- Thêm 6 đồ uống
INSERT INTO Mon(TenMon, DonGia, NgayADGia, DonViTinh, TenLoai, MaLoai, Hinh)
VALUES 
    (N'Nước suối', 10000, '2023-11-13', N'chai', N'Đồ uống', 3,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\DoUong\NuocSuoi.jpg'),
    (N'Nước ngọt Pepsi', 12000, '2023-11-13', N'chai', N'Đồ uống', 3,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\DoUong\Pepsi.jpg'),
(N'Nước ngọt Coca', 12000, '2023-11-13', N'chai', N'Đồ uống', 3,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\DoUong\Coca.jpg'),
    (N'Bia Huda', 25000, '2023-11-13', N'chai', N'Đồ uống', 3, N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\DoUong\Huda.jpg'),
    (N'Bia Saigon', 30000, '2023-11-13', N'chai', N'Đồ uống', 3,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\DoUong\SaiGon.jpg'),
    (N'Bia Heineken', 35000, '2023-11-13', N'chai', N'Đồ uống', 3,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\DoUong\Heineken.jpg');

-- Thêm 4 món tráng miệng
INSERT INTO Mon(TenMon, DonGia, NgayADGia, DonViTinh, TenLoai, MaLoai, Hinh)
VALUES 
    (N'Bánh flan', 25000, '2023-11-13', N'đĩa', N'Món tráng miệng', 4,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\TrangMien\BanhFlan.jpg'),
    (N'Bánh kem', 30000, '2023-11-13', N'đĩa', N'Món tráng miệng', 4,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\TrangMien\BanhKem.jpg'),
    (N'Chè đậu đỏ', 30000, '2023-11-13', N'đĩa', N'Món tráng miệng', 4,  N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\TrangMien\CheDauDo.jpg'),
	    (N'Chè đậu hũ', 30000, '2023-11-13', N'đĩa', N'Món tráng miệng', 4, N'C:\Users\MS QUOC\Documents\NetBeansProjects\mavenproject1\src\main\resources\com\edusys\icon\images\TrangMien\BanhKem.jpg');
		

-- INSERT INTO BanAn
-- Thêm 10 bàn ăn

INSERT INTO BanAn (MaSoBan)
VALUES ('B101'),
       ('B102'),
       ('B103'),
       ('B104'),
       ('B105'),
       ('B106'),
       ('B107'),
       ('B108'),
       ('B109'),
       ('B110'),
	   ('B201'),
       ('B202'),
       ('B203'),
       ('B204'),
       ('B205'),
       ('B206'),
       ('B207'),
       ('B208'),
       ('B209'),
       ('B210');;

-- INSERT INTO HOADON
-- Thêm 10 hóa đơn
-- Thêm dữ liệu ví dụ vào bảng HoaDon với cột SoHD tự tăng
INSERT INTO HoaDon (SoHD)
VALUES
    (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20)
	select * from DATMON
-- Thêm dữ liệu ví dụ vào bảng ORDERR
INSERT INTO DatMon (MaSoBan, TenLoai, TenMon, DonGia, SoLuong,ThoiGianLap,SoHD)
VALUES
    ('B102', N'Món khai vị', N'Xúc xích tươi', 50000, 1,'2023-11-13',1),
    ('B103', N'Đồ uống', N'Nước suối', 10000, 3,'2023-11-13',1),
    ('B104', N'Món tráng miệng', N'Bánh flan', 25000, 1,'2023-11-13',1)
INSERT [dbo].[NhanVien] ([MaNV],[TenNV],[NgaySinh],[SDT],[TenDN],[CaLam], [Email],[GioiTinh], [MatKhau], [VaiTro],[MaSoBan]) VALUES (N'sa1', N'Nguyễn Thái Quốc', '2003-08-16', '093494222', 'QuocNT', 4, 'quocafk12345@gmail.com', 0, N'songlong', N'1','B101')
INSERT [dbo].[NhanVien] ([MaNV],[TenNV],[NgaySinh],[SDT],[TenDN],[CaLam], [Email],[GioiTinh], [MatKhau], [VaiTro],[MaSoBan]) VALUES (N'LongNDH', N'Nguyễn Ðình Hoàng Long', '2003-03-16', '093494222', 'LongND', 1, 'longnd@fpt.edu.vn', 0, N'songlong',N'1' ,'B102')
INSERT [dbo].[NhanVien] ([MaNV],[TenNV],[NgaySinh],[SDT],[TenDN],[CaLam], [Email],[GioiTinh], [MatKhau], [VaiTro],[MaSoBan]) VALUES (N'NghiemN', N'Nguyễn nghiêm', '2002-02-16', '093491222', 'NghiemN', 2, 'NghiemN@fpt.edu.vn', 0, N'songlong', N'1','B103')
INSERT [dbo].[NhanVien] ([MaNV],[TenNV],[NgaySinh],[SDT],[TenDN],[CaLam], [Email],[GioiTinh], [MatKhau], [VaiTro],[MaSoBan]) VALUES (N'NoPT', N'Phạm Thị Nở', '2002-04-12', '093494221', 'NoPT', 3, 'nopt@fpt.edu.vn', 1, N'songlong', N'0','B104')
INSERT [dbo].[NhanVien] ([MaNV],[TenNV],[NgaySinh],[SDT],[TenDN],[CaLam], [Email],[GioiTinh], [MatKhau], [VaiTro],[MaSoBan]) VALUES (N'sa', N'Nguyễn Thái Quốc', '2003-08-16', '093494222', 'QuocNT', 4, 'quocntps31889@fpt.edu.vn', 0, N'songlong', N'1','B105')
INSERT [dbo].[NhanVien] ([MaNV],[TenNV],[NgaySinh],[SDT],[TenDN],[CaLam], [Email],[GioiTinh], [MatKhau], [VaiTro],[MaSoBan]) VALUES (N'PheoNC', N'Nguyễn Chí Phèo', '2001-03-16', '09342225', 'PheoNC', 5, 'pheonc@fpt.edu.vn', 0, N'songlong', N'0','B106')
INSERT [dbo].[NhanVien] ([MaNV],[TenNV],[NgaySinh],[SDT],[TenDN],[CaLam], [Email],[GioiTinh], [MatKhau], [VaiTro],[MaSoBan]) VALUES (N'TeoNDH', N'Nguyễn Ðình Hoàng Tèo', '2002-03-16', '093494222', 'TeoND', 1, 'teond@fpt.edu.vn', 0, N'songlong', N'0','B107')

