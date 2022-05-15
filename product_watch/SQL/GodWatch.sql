/*
	USE MASTER
	GO
	DROP DATABASE GodShop
	GO
*/

CREATE DATABASE GodShop
GO

USE GodShop
GO

CREATE TABLE Roles(
	Id VARCHAR(15) PRIMARY KEY,
	Name NVARCHAR(100) NOT NULL
);
GO

CREATE TABLE Accounts(
	Username VARCHAR(50) PRIMARY KEY,
	Password VARCHAR(250) NOT NULL,
	IsDelete BIT DEFAULT 0,
	RoleId VARCHAR(15) NOT NULL,
	FOREIGN KEY (RoleId) REFERENCES Roles(Id)
);
GO

CREATE TABLE Users(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Fullname NVARCHAR(50) NOT NULL,
	Email NVARCHAR(150) NOT NULL,
	Gender INT DEFAULT 0, -- 0 nữ | 1 nam | 2 unisex
	Dob DATE,
	Phone VARCHAR(12) NOT NULL,
	Photo NVARCHAR(250) NULL,
	Address NVARCHAR(250) NOT NULL,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

CREATE TABLE RefAccounts(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	NewAccount VARCHAR(50),
	IsReward BIT DEFAULT 0, -- mặc định chưa nhận
	OldAccount VARCHAR(50),
	FOREIGN KEY (OldAccount) REFERENCES Accounts(Username)
);
GO

CREATE TABLE Vouchers(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name VARCHAR(20) NOT NULL,
	Discount INT DEFAULT 0,
	CreateDate DATETIME NOT NULL,
	EndDate DATETIME NOT NULL,
	CreateBy VARCHAR(50),
	FOREIGN KEY (CreateBy) REFERENCES Accounts(Username)
);
GO

CREATE TABLE Gcoins(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Gcoin INT DEFAULT 0, -- số lượng gcoin trong 1 tài khoản
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

-- Danh sách các voucher trong ví của tài khoản
CREATE TABLE VoucherLists(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IsUsed BIT DEFAULT 0,
	VoucherID INT,
	FOREIGN KEY (VoucherID) REFERENCES Vouchers(Id),
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

--===============================
--======== PRODUCTS
CREATE TABLE Brands(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(250) NOT NULL
);
GO

CREATE TABLE Categories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(250) NOT NULL
);
GO

CREATE TABLE Products(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(250) NOT NULL,	
	Quantity INT DEFAULT 0, -- số lượng
	Price FLOAT DEFAULT 0, -- Giá
	CreateDate DATE NOT NULL, -- ngày tạo sản phẩm
	Warranty INT DEFAULT 0, -- Bảo hành tính theo tháng
	MadeIn NVARCHAR(50) NULL,
	Detail NTEXT NULL, -- mô tả sản phẩm
	BrandId INT NOT NULL, -- thương hiệu
	CategoryId INT NULL, 
	FOREIGN KEY (CategoryId) REFERENCES Categories(Id),
	FOREIGN KEY (BrandId) REFERENCES Brands(Id)
);


CREATE TABLE ProductPhotos(
	Id VARCHAR(100) PRIMARY KEY,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

CREATE TABLE ProductDiscounts(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Discount INT DEFAULT 0,
	CreateDate DATETIME NOT NULL,
	EndDate DATETIME NOT NULL,
	ProductId INT,
	CreateBy VARCHAR(50) NOT NULL,
	FOREIGN KEY (CreateBy) REFERENCES Accounts(Username),
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

--== Chất liệu mặt kính
CREATE TABLE GlassMaterials(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL
);
GO
--== Chất liệu dây đồng hồ
CREATE TABLE BraceletMaterials(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL
);
GO
--== Hệ thống bên trong
CREATE TABLE MachineInsides(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL
);
GO


CREATE TABLE Watches(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Gender INT DEFAULT 0, -- -- 0 Nữ | 1 Nam | 2 Unisex | 3 Couple | 4 Kid
	GlassSizes INT DEFAULT 29, -- đường kính mặt kính
	ATM INT DEFAULT 0, -- Chống nước
	GlassColors NVARCHAR(10), --  màu mặt đồng hồ
	CaseColors NVARCHAR(10), -- màu vỏ đồng hồ
	ProductId INT NOT NULL,
	GlassmaterialId INT,
	BraceletmaterialId INT,
	MachineinsideId INT,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	FOREIGN KEY (GlassmaterialId) REFERENCES GlassMaterials(Id),
	FOREIGN KEY (BraceletmaterialId) REFERENCES BraceletMaterials(Id),
	FOREIGN KEY (MachineinsideId) REFERENCES MachineInsides(Id),
);
GO

--==================================
--== Phụ kiện
CREATE TABLE Accessories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Colors NVARCHAR(20),
	BraceletmaterialId INT,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	FOREIGN KEY (BraceletmaterialId) REFERENCES BraceletMaterials(Id),
);
GO

--=====================================
--===== ORDERS
CREATE TABLE OrderStatuses(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(25)
);
GO

CREATE TABLE OrderMethods(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(100)
);
GO

CREATE TABLE Orders(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	CreateDate DATE NOT NULL,
	Address NTEXT NOT NULL,
	Username VARCHAR(50) NOT NULL,
	OrderstatusId INT NOT NULL,
	OrdermethodId INT NOT NULL,
	FOREIGN KEY (OrderstatusId) REFERENCES OrderStatuses(Id),
	FOREIGN KEY (Username) REFERENCES Accounts(Username),
	FOREIGN KEY (OrdermethodId) REFERENCES OrderMethods(Id)
);
GO

CREATE TABLE OrderDetails(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	OrderId INT NOT NULL,
	ProductId INT NOT NULL,
	Price FLOAT NOT NULL,
	Quantity INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	FOREIGN KEY (OrderId) REFERENCES Orders(Id)
);
GO


--=====================================
-- COMMENT - REPLY
CREATE TABLE ProductLikes(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IsLiked BIT DEFAULT 0,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

CREATE TABLE ProductEvaluations(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Evaluation INT DEFAULT 0,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

CREATE TABLE ProductComments(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	TopicUsername VARCHAR(50),
	FOREIGN KEY (TopicUsername) REFERENCES Accounts(Username),
	CommentContent NTEXT,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id),

);
GO

CREATE TABLE CommentPhotos(
	Id VARCHAR(100) PRIMARY KEY,
	CommentId INT,
	FOREIGN KEY (CommentId) REFERENCES ProductComments(Id),
);
GO

CREATE TABLE ProductReplies(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	CommentId INT,
	ReplyContent NTEXT,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (CommentId) REFERENCES ProductComments(Id),
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

--==============================================================


--==============================================================

INSERT INTO Roles(Id, Name) VALUES
('Customer', N'Khách hàng'),
('Admin', N'Quản trị viên')
GO

INSERT INTO Accounts(Username, Password, IsDelete, RoleId) VALUES
('admin01', '123', 0, 'Admin'),
('cust01', '123', 0, 'Customer'),
('cust02', '123', 0, 'Customer'),
('cust03', '123', 0, 'Customer'),
('cust04', '123', 1, 'Customer'),
('cust05', '123', 0, 'Customer')
GO

INSERT INTO Users(Fullname, Email, Gender, Dob, Phone, Photo, Address, Username) VALUES
(N'Trần Trung Nghĩa', 'nghiattps14820@fpt.edu.vn', 1, '1997-04-13', '0366888470', 'nghia.jpg', 'Tân Bình', 'admin01'),
(N'Doãn Hoài Nam', 'namdh123@gmail.com', 1, '1996-01-02', '0989878787', 'nam.jpg', 'Quận 11', 'cust01'),
(N'Trần Nguyên Hải', 'haitn123@gmail.com', 1, '1992-01-02', '0387465739', 'hai.jpg', 'Quận Bình Thạnh', 'cust02'),
(N'Lê Quý Vương', 'vuonglq123@gmail.com', 1, '1995-01-02', '0988767512', 'vuong.jpg', 'Quận 2', 'cust03'),
(N'Hồ Trung Tính', 'tinhht321@gmail.com', 1, '2000-01-02', '0976352435', 'tinh.jpg', 'Quận 11', 'cust04'),
(N'Trần Thị Hoàn', 'hoantt@gmail.com', 0, '2001-03-31', '0398767652', 'hoan.jpg', 'Quận 12', 'cust05')
GO

INSERT INTO RefAccounts(NewAccount, OldAccount, IsReward) VALUES
('cust03', 'cust02', 0)
GO

INSERT INTO Brands(Name) VALUES
('Baby-G'),
('Bulova'),
('Calvin Klein'),
('Cadino'),
('Casio'),
('Citizen'),
('Daniel Wellington'),
('Doxa'),
('Fossil'),
('G-Shock'),
('Longines'),
('Pulsar'),
('Saga'),
('Seiko'),
('Rolex')
GO

INSERT INTO Categories(Name) VALUES
(N'Analog Watch'),
(N'Digital Watch'),
(N'Automatic Watch'),
(N'Chronograph Watch'),
(N'Diving Watch'),
(N'Dress Watch'),
(N'Quartz Watch'),
(N'Mechanical Watch'),
(N'Pilot Watch'),
(N'Field Watch'),
(N'Smart Watch'),
(N'Luxury Watch'),
(N'Phụ kiện')
GO

INSERT INTO GlassMaterials(Name) VALUES
(N'Kính Mica'),
(N'Kính Sapphire'),
(N'Kính Khoáng'),
(N'Kính Hardlex')
GO

INSERT INTO BraceletMaterials(Name) VALUES
(N'Dây da'),
(N'Dây kim loại'),
(N'Dây lưới'),
(N'Dây vải'),
(N'Dây da cá sấu'),
(N'Dây nhựa/cao su'),
(N'Dây cacbon'),
(N'Dây titanium')
GO

INSERT INTO MachineInsides(Name) VALUES
(N'Pin(Quartz)'),
(N'Cơ(Automatic)'),
(N'Năng lượng mặt trời(Eco Drive)')
GO

-- VUONG -- 
INSERT INTO Products VALUES
(N'Longines Heritage Classic',3,3500,'2018-07-09',60,N'Thuỵ Sỹ',N'Bao Gồm Vòng Đeo Tay Hạt Gạo Bằng Thép Không Gỉ Với Móc Cài Của Người Triển Khai Có Chữ Ký. Cũng Bao Gồm Hộp Bên Trong Và Bên Ngoài, Dây Da Nâu Có Chữ Ký Với Khóa Ghim Bằng Thép Có Chữ Ký, Hộp Đựng Thẻ Da, Sách, Dụng Cụ, Thẻ Đo Thời Gian, Thẻ Bảo Hành Và Chứng Nhận Phiên Bản Giới Hạn',11,1),
(N'Longines Legend Diver',5,2000,'2019-09-02',60,N'Thuỵ Sỹ',N'Là phiên bản tái hiện của một chiếc đồng hồ thợ lặn những năm 1960, The Longines Legend Diver chiếm một vị trí đặc biệt trong phân khúc Di sản của thương hiệu',11,2),
(N'Longines Conquest Heritage',2,2550,'2021-12-12',60,N'Thuỵ Sỹ',N'Longines Conquest Heritage mang đến mọi thứ mà người chơi đồng hồ chuyên nghiệp cần. Trong đó có thể kể đến một vài tính năng, tiện ích và thông tin kỹ thuật như sau: chronograph, đồng hồ 24h, lịch trăng,… Đặc biệt hơn, thời gian trữ cót đã được cải tiến lên đến 54 giờ',11,5),
(N'Longines HydroConquest',7,1250,'2022-02-02',60,N'Thuỵ Sỹ', N'Đồng hồ Longines HydroConquest có mặt số tròn với viền mỏng tinh tế, nền số màu trắng trang nhã cùng kim chỉ và chữ số chỉ giờ La Mã mỏng phủ màu đen nổi bật, dây đeo kim loại',11,7),
(N'Longines Spirit Zulu Time',2,3450,'2020-12-10',60,N'Thuỵ Sỹ',N'Longines là thương hiệu thời trang hàng đầu hiện nay của Thụy Sỹ, thuộc sở hữu Tập đoàn Swatch. Các sản phẩm đồng hồ đeo tay chủ yếu tập trung vào phân khúc giá cận cao cấp và cao cấp',11,12),
--
(N'Pulsar SR SG8884',10,3350,'2022-01-02',60,N'Nhật Bản',N'Mẫu SR SG8884.4102AT dây da nâu với phiên bản tạo hình vân lịch lãm trẻ trung với cọc số la mã được tạo hình mỏng trên nền mặt số trắng kích thước nam tính 42mm',12,3),
(N'PULSAR PS9251X1',5,120,'2022-05-05',36,N'Nhật Bản',N'Ba Ưu Điểm Khó Cưỡng Của Đồng Hồ Pulsar Chính Hãng Với tư cách là thương hiệu con của nhà sản xuất đồng hồ lớn nhất Nhật Bản Seiko, đồng hồ Pulsar nam chính hãng mang trong mình gần như toàn bộ những gì tốt đẹp nhất của Seiko nhưng chỉ với một mức',12,9),
(N'Nữ PULSAR PEGG76X1',20,150,'2022-06-06',36,N'Nhật Bản',N'Giới thiệu về thương hiệu PULSAR “Thương hiệu đồng hồ PulSar ra đời vào năm 1970, mãi đến năm 1972 thì những chiếc đồng hồ Pulsar mới có mặt trên thị trường. Lúc bấy giờ rất ít khách hàng chú ý đến thương hiệu này, nhưng với định hướng phát triển Pulsar...',12,8),
(N'Pulsar PT3067X1',8,75,'2022-03-06',12,N'Nhật Bản',N'Giới thiệu về thương hiệu PULSAR “Thương hiệu đồng hồ PulSar ra đời vào năm 1970, mãi đến năm 1972 thì những chiếc đồng hồ Pulsar mới có mặt trên thị trường. Lúc bấy giờ rất ít khách hàng chú ý đến thương hiệu này, nhưng với định hướng phát triển Pulsar đã làm',12,6),
(N'PULSAR PP6076X1',2,135,'2022-03-06',12,N'Nhật Bản',N'Giới thiệu về thương hiệu PULSAR “Thương hiệu đồng hồ PulSar ra đời vào năm 1970, mãi đến năm 1972 thì những chiếc đồng hồ Pulsar mới có mặt trên thị trường. Lúc bấy giờ rất ít khách hàng chú ý đến thương hiệu này, nhưng với định hướng phát triển Pulsar đã làm',12,4),
--
(N'DÂY CHUYỀN SAGA',10,125,'2022-01-02',60,N'Mỹ',N'Đính pha lê Swarovski, có chứng nhận của tập đoàn Swarovsk',13,13),
(N'SAGA 13323',10,275,'2022-01-02',60,N'Mỹ',N'Mẫu Saga 13323 SVBLSV-1 thiết kế 3 núm điều chỉnh tính năng Chronograph (đo thời gian) tạo nên kiểu dáng đồng hồ 6 kim trên nền mặt số xanh thời trang với tạo hình họa tiết Guilloche.',13,4),
(N'SAGA 53375 SVMWPK-2',10,550,'2022-01-06',60,N'Mỹ',N'Mẫu Saga 53375 SVMWPK-2 phiên bản 12 viên đá Swarovski được đính tương ứng 12 múi giờ hiện thị trên nền mặt số kích thước 34mm với tone màu trắng xà cừ tạo nên vẻ ngoài thời trang sang trọng',13,5),
(N'SAGA 53578 SVSVBK-2',10,250,'2022-08-02',36,N'Mỹ',N'Mẫu Saga 53578 SVSVBK-2 phiên bản 4 viên pha lê Swarovski đính trên mặt số, các chi tiết kim chỉ cọc vạch số được tạo nét mỏng trên nền mặt số size 35mm với thiết kế họa tiết trải tia nhẹ.',13,9),
(N'BÔNG TAI SAGA',10,110,'2022-01-01',60,N'Mỹ',N'Đính pha lê Swarovski, có chứng nhận của tập đoàn Swarovsk',13,13),
--
(N'SEIKO SSB351P1 – NAM',2,340,'2021-12-10',60,N'Nhật Bản',N'Mẫu Seiko SSB351P1 phiên bản nam tính với tính năng đo thời gian tạo nên kiểu dáng đồng hồ 6 kim trên nền mặt số lớn kích thước 43.9mm.',14,11),
(N'SEIKO NH8390-20H',9,340,'2021-12-10',60,N'Nhật Bản',N'Mẫu đồng hồ Dress toát lên vẻ trang nhã, cổ điển là lựa chọn lý tưởng cho những sự kiện ấm cúng. Với dây da thật cao cấp mềm mại, pha họa tiết nổi tinh tế tạo điểm nhấn và nét đặc trưng riêng của thời trang hiện đại.',14,6),
(N'SEIKO NH8390-20H',3,340,'2021-12-10',60,N'Nhật Bản',N'Vẻ ngoài thiết kế kiểu dáng dày dặn khả năng chịu nước lên đến 20 ATM cùng với trải nghiệm của bộ máy cơ là những điểm nổi bật tạo nên mẫu đồng hồ nam Seiko SRPA21K1 phong cách thể thao.',14,8),
(N'SEIKO SSA295K1',4,310,'2021-09-11',60,N'Nhật Bản',N'Đồng hồ nam Seiko SSA295K1 với kiểu thiết kế cổ điển dành cho nam, kim chỉ và vạch số to rõ nổi bật trên nền trắng trang trọng, kết hợp cùng với dây đeo bằng da nâu tạo vẻ hoài cổ lịch lãm.',14,7),
(N'Seiko SRN054P1',2,390,'2021-09-11',60,N'Nhật Bản',N'Mẫu Seiko SRN054P1 các chi tiết kim chỉ cùng cọc chấm tròn vạch số tạo hình dày dặn phủ dạ quang trên nền mặt số kích thước 42,5mm với thiết kế họa tiết.',14,1),
--
(N'Rolex Yacht-Master',2,25000,'2022-08-12',60,N'Thuỵ Sỹ',N'Đồng hồ Rolex Yacht-Master 116621 Mặt Số Đen (Like New)',15,12),
(N'Rolex Datejust 36',2,35000,'2022-09-11',60,N'Thuỵ Sỹ',N'Caliber Rolex 4130 - Trữ cót 72 giờ',15,12),
(N'Rolex Cosmograph Daytona ',1,99600,'2021-10-11',60,N'Thuỵ Sỹ',N'Mặt Số Chocolate Nạm Kim Cương (Like New 2021).',15,12),
(N'Rolex Oyster Perpetual Day',1,72500,'2021-11-11',60,N'Thuỵ Sỹ',N'Đồng hồ Rolex Oyster Perpetual Day-Date 36mm sở hữu đai kính rãnh đặc trưng riêng của Rolex. Ban đầu, đồng hồ được tạo ra để dễ dàng xoay vòng bezel và vỏ để đảm bảo khả năng chống nước tối đa nhất. ',15,12),
(N'Rolex Datejust 126234',4,80000,'2021-12-11',60,N'Thuỵ Sỹ',N'Đồng hồ  Rolex DateJust 126234 Blue Navy nằm trên cùng một kệ với mẫu đồng hồ mới được ra mắt tại Baselworld 2019 là Rolex Datejust 36mm 126234-0037. ',15,12)
GO

INSERT INTO Watches VALUES
(0,39,5,N'Bạc',N'Bạc',1,2,8,3),
(1,42,20,N'Xám',N'Xám',2,2,8,3),
(1,40,10,N'Xám',N'Bạc',3,2,1,1),
(1,39,20,N'Bạc',N'Đen',4,2,2,1),
(1,40,20,N'Bạc',N'Bạc',5,2,8,3),
(1,42,5,N'Bạc',N'Nâu',6,1,2,1),
(1,44,5,N'Xanh',N'Nâu',7,1,1,1),
(0,16,5,N'Trắng',N'Vàng',8,2,2,2),
(1,42,5,N'Đen',N'Bạc',9,4,1,2),
(0,34,10,N'Đồng',N'Đồng',10,3,2,3),
(0,60,10,N'Đồng',N'Đồng',11,3,3,1),
(0,18,5,N'Vàng',N'Vàng',12,1,2,1),
(0,34,10,N'Vàng',N'Hồng',13,1,1,1),
(0,34,10,N'Trắng',N'Bạc',14,1,1,1),
(0,60,10,N'Đồng',N'Đồng',15,3,3,1),
(1,42,20,N'Xanh',N'Đen',16,4,3,3),
(1,42,20,N'Đen',N'Bạc',17,2,1,3),
(1,42,20,N'Đen',N'Đen',18,4,2,3),
(1,44,20,N'Nâu',N'Bạc',19,2,1,1),
(1,42,20,N'Đen',N'Vàng',20,1,1,1),
(1,40,20,N'Đen',N'Hồng',21,4,8,3),
(0,38,20,N'Hồng',N'Hồng',22,4,8,3),
(1,41,20,N'Nâu',N'Hồng',23,4,8,3),
(0,36,20,N'Nâu',N'Hồng',24,4,8,3),
(1,42,20,N'Xanh',N'Bạc',25,4,8,3)

GO

INSERT INTO ProductPhotos VALUES
('longines-analog-p1-1.jpg',1),
('longines-analog-p1-2.jpg',1),
('longines-analog-p1-3',1),
('longines-analog-p1-4',1),
('longines-digital-p2-1.jpg',2),
('longines-digital-p2-2.jpg',2),
('longines-digital-p2-3.jpg',2),
('longines-digital-p2-4.jpg',2),
('longines-diving-p3-1.jpg',3),
('longines-diving-p3-2.jpg',3),
('longines-diving-p3-3.jpg',3),
('longines-diving-p3-4.jpg',3),
('longines-quartz-p4-1.jpg',4),
('longines-quartz-p4-2.jpg',4),
('longines-quartz-p4-3.jpg',4),
('longines-quartz-p4-4.jpg',4),
('longines-luxury-p5-1.jpg',5),
('longines-luxury-p5-2.jpg',5),
('longines-luxury-p5-3.jpg',5),
('longines-luxury-p5-4.jpg',5),
('pulsar-automatic-p1-1.jpg',6),
('pulsar-automatic-p1-2.jpg',6),
('pulsar-automatic-p1-3.jpg',6),
('pulsar-automatic-p1-4.jpg',6),
('pulsar-quartz-p1.jpg',7),
('pulsar-quartz-p2.jpg',7),
('pulsar-quartz-p3.jpg',7),
('pulsar-quartz-p4.jpg',7),
('pulsar-mechanical-p1.jpg',8),
('pulsar-mechanical-p2.jpg',8),
('pulsar-mechanical-p3.jpg',8),
('pulsar-mechanical-p4.jpg',8),
('pulsar-diving-p1.jpg',9),
('pulsar-diving-p2.jpg',9),
('pulsar-diving-p3.jpg',9),
('pulsar-diving-p4.jpg',9),
('pulsar-chronograph-p1.jpg',10),
('pulsar-chronograph-p2.jpg',10),
('pulsar-chronograph-p3.jpg',10),
('pulsar-chronograph-p4.jpg',10),
('saga-p1-1.jpg',11),
('saga-p1-2.jpg',11),
('saga-p1-3.jpg',11),
('saga-p1-4.jpg',11),
('saga-chronograph-p2-1.jpg',12),
('saga-chronograph-p2-2.jpg',12),
('saga-chronograph-p3-3.jpg',12),
('saga-chronograph-p4-4.jpg',12),
('saga-diving-p3-1.jpg',13),
('saga-diving-p3-2.jpg',13),
('saga-diving-p3-3.jpg',13),
('saga-diving-p3-4.jpg',13),
('saga-pilot-p4-1.jpg',14),
('saga-pilot-p4-2.jpg',14),
('saga-pilot-p4-3.jpg',14),
('saga-pilot-p4-4.jpg',14),
('saga-p5-1.jpg',15),
('saga-p5-2.jpg',15),
('saga-p5-3.jpg',15),
('saga-p5-4.jpg',15),
('seiko-smart-p1-1.jpg',16),
('seiko-smart-p1-2.jpg',16),
('seiko-smart-p1-3.jpg',16),
('seiko-smart-p1-4.jpg',16),
('seiko-dress-p2-1.jpg',17),
('seiko-dress-p2-2.jpg',17),
('seiko-dress-p2-3.jpg',17),
('seiko-dress-p2-4.jpg',17),
('seiko-mechanical-p3-1.jpg',18),
('seiko-mechanical-p3-2.jpg',18),
('seiko-mechanical-p3-3.jpg',18),
('seiko-mechanical-p3-4.jpg',18),
('seiko-quartz-p4-1.jpg',19),
('seiko-quartz-p4-2.jpg',19),
('seiko-quartz-p4-3.jpg',19),
('seiko-quartz-p4-4.jpg',19),
('seiko-analog-p5-1.jpg',20),
('seiko-analog-p5-2.jpg',20),
('seiko-analog-p5-3',20),
('seiko-analog-p5-4',20),
--rolex
('rolex-luxury-p1-1.jpg',21),
('rolex-luxury-p1-2.jpg',21),
('rolex-luxury-p1-3.jpg',21),
('rolex-luxury-p1-4.jpg',21),
('rolex-luxury-p2-1.jpg',22),
('rolex-luxury-p2-2.jpg',22),
('rolex-luxury-p2-3.jpg',22),
('rolex-luxury-p2-4.jpg',22),
('rolex-luxury-p3-1.jpg',23),
('rolex-luxury-p3-2.jpg',23),
('rolex-luxury-p3-3.jpg',23),
('rolex-luxury-p3-4.jpg',23),
('rolex-luxury-p4-1.jpg',24),
('rolex-luxury-p4-2.jpg',24),
('rolex-luxury-p4-3.jpg',24),
('rolex-luxury-p4-4.jpg',24),
('rolex-luxury-p5-1.jpg',25),
('rolex-luxury-p5-2.jpg',25),
('rolex-luxury-p5-3.jpg',25),
('rolex-luxury-p5-4.jpg',25)
GO

select *from Products
select *from Watches
select *from Categories
select *from Products where CategoryId ='14'
