/*
	USE MASTER
	GO
	DROP DATABASE GodShop
	GO
*/
---------update lần cuối--7:50 15/05/2022----------------
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
go


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
('Jacob & Co'),
('Casio'),
('Cityzen'),
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
(N'Kính Acrylic'),
(N'Kính Sapphire'),
(N'Kính Mineral'),
(N'Kính Hardlex')
GO

INSERT INTO BraceletMaterials(Name) VALUES
(N'Dây da'),
(N'Dây kim loại'),
(N'Dây lưới'),
(N'Dây vải'),
(N'Dây da cá sấu'),
(N'Dây nhựa/ cao su'),
(N'Dây cacbon'),
(N'Dây titanium')
GO

INSERT INTO MachineInsides(Name) VALUES
(N'Pin(Quartz)'),
(N'Cơ(Automatic)'),
(N'Năng lượng mặt trời(Eco Drive)')
GO
INSERT INTO Products(Name,Quantity,Price,CreateDate,Warranty,MadeIn,Detail,BrandId,CategoryId) VALUES

(N'BA-130-7A1DR',1,2,'2022-05-15',60,N'Mỹ',N'Từ BABY-G, dòng đồng hồ thường ngày dành cho nữ giới năng động, chúng tôi đã cho ra mắt các mẫu mới để tô điểm cho thời trang đường phố.
Mẫu cơ bản là BA-130, phiên bản nhỏ gọn của thiết kế G-SHOCK được ưa chuộng. Sắc trắng và hồng của mẫu sản phẩm này kết hợp với màu kim loại của các bộ phận mặt đồng hồ đem đến diện mạo tươi sáng. Tông màu đơn sắc tinh tế của mẫu đồng hồ màu đen để lại ấn tượng về vẻ ngoài sành điệu.
Thiết kế vỏ nam tính kết hợp với các màu sắc kim loại nữ tính tạo nên thiết kế đồng hồ vô cùng phù hợp với thời trang đường phố.',1,1),
(N'BA-120TG-4ADR',3,5,'2022-05-15',60,N'Mỹ',N'Thuộc dòng BA-120LP-7A2DR – dòng baby-g kim số được mệnh danh là G-shock GA-120 phiên bản nữ; BA-120LP-7A2 với ,ặt số ở vị trí 6 giờ và 9 giờ của mẫu đồng hồ kim-số này được bố trí dễ đọc.
Điều này hơn nữa tạo một thiết kế dày dặn rất phù hợp với trang phục xuống phố cũng như thời trang thường ngày.',1,2),
(N'BGD-560CF-7DR',5,10,'2022-05-15',60,N'Mỹ',N'Lấy cảm hứng từ phong cách bãi biển phía Tây Hoa Kỳ.',1,3),
(N'BGA-151EF-1BDR',7,15,'2022-05-15',60,N'Mỹ',N'Ba mặt số cung cấp thông tin về ngày tháng, Giờ thế giới, đồng hồ bấm giờ và nhiều hơn nữa.Từ dòng BGA-150 mặt to phổ biến đã xuất hiện các mẫu mới với số Ả Rập tuyệt vời cho bốn vạch giờ. Mẫu màu hồng nhạt và trắng kết hợp vạch giờ số Ả Rập màu vàng hồng mang đến họa tiết màu sắc nữ tính và thanh lịch. Ba mặt số cung cấp thông tin về ngày tháng, Giờ thế giới, đồng hồ bấm giờ và nhiều hơn nữa.
Thiết kế nữ tính cực kỳ phù hợp với thời trang nữ với các tính năng và chức năng tiện dụng và dễ đọc.',1,12),
(N'BGA-190-3BDR',7,2,'2022-05-15',60,N'Mỹ',N'Với tính năng Dual Dial World Time (Giờ thế giới hai vòng quay), mẫu này rất lý tưởng cho những phụ nữ thích đi du lịch thế giới ngày nay. Bạn có thể xem nhanh thời gian ở vị trí hiện tại của bạn (kim chính) và một vị trí khác (mặt số ở vị trí 6 giờ), hoán đổi mặt số chính và mặt số giờ bằng cách đơn giản là nhấn vào nút phía dưới bên phải.
Màu của mẫu này đã được lựa chọn đặc biệt để phù hợp với các khu nghỉ dưỡng trên bãi biển trên toàn cầu và họa tiết du lịch được nhấn mạnh hơn bằng kim phút hình máy bay trong mặt số và thiết kế mặt đồng hồ hình bản đồ thế giới.',1,4),

(N'Bulova 96L257',9,200,'2022-05-16',60,N'Mỹ',N'Bulova 96L257 - Đồng Hồ Nữ - Pin / Quartz - Kính Khoáng - Size Mặt 33mm - Bảo Hành 5 Năm - Chính Hãng 100%',2,7),
(N'Bulova 98A187',1,250,'2022-05-16',60,N'Mỹ',N'Bulova 98A187 - Đồng Hồ Nam - Cơ - Automatic - Kính Khoáng - Size Mặt 41mm - Bảo Hành 5 Năm - Chính Hãng 100%',2,3),
(N'BULOVA 98A137',3,200,'2022-05-16',60,N'Mỹ',N'BULOVA 98A137 - Đồng Hồ Nam - Pin / Quartz - Kính Khoáng - Bảo Hành 5 Năm - Chính Hãng 100%',2,7),
(N'Bulova 97B165',5,200,'2022-05-16',60,N'Mỹ',N'Bulova 97B165 - Đồng Hồ Nam - Pin / Quartz - Kính Khoáng - Size Mặt 37mm - Bảo Hành 5 Năm - Chính Hãng 100%',2,7),
(N'Bulova 96C132',5,250,'2022-05-16',60,N'Mỹ',N'Bulova 96C132 - Đồng Hồ Nam - Cơ - Automatic - Kính Khoáng - Size Mặt 42mm - Bảo Hành 5 Năm - Chính Hãng 100%',2,3),

(N'CALVIN KLEIN K3M51T5N',9,100,'2022-05-17',60,N'Thụy Sỹ',N'CALVIN KLEIN K3M51T5N - Đồng Hồ Nam - Pin / Quartz - Kính Khoáng - Size Mặt 40mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,7),
(N'CALVIN KLEIN STRAP 22MM',7,60,'2022-05-17',24,N'Thụy Sỹ',N'Dây đeo CALVIN KLEIN làm từ da cao cấp phù hợp với các loại đồng hồ cùng hãng',3,13),
(N'Calvin Klein K2G236X6',5,100,'2022-05-17',60,N'Thụy Sỹ',N'Đồng Hồ Nữ - Pin / Quartz - Kính Khoáng - Size Mặt 31mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,6),
(N'Calvin Klein K4D221CY',3,100,'2022-05-17',60,N'Thụy Sỹ',N'Calvin Klein K4D221CY - Đồng Hồ Nữ - Pin / Quartz - Kính Khoáng - Size Mặt 32mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,8),
(N'Calvin Klein K6R23526',1,100,'2022-05-17',60,N'Thụy Sỹ',N'Calvin Klein K6R23526 - Đồng Hồ Nữ - Pin / Quartz - Kính Khoáng - Size Mặt 32mm - Bảo Hành 5 Năm - Chính Hãng 100%',3,9),

(N'Đồng Hồ Jacob & Co Astronomia Solar Baguette - Jewellery - Planets - Zodiac AS910.40.BD.BD.A6',1,649702,'2022-05-18',60,N'Mỹ',N'Mang sự sáng tạo đánh tan những chuẩn mực thiết kế thông thường 
vẫn luôn là cách mà thương hiệu đồng hồ Jacob & Co ghi dấu ấn mạnh mẽ trong lòng người hâm mộ trên thế giới có một không hai, không thể nhầm lẫn.',4,12),
(N'Đồng Hồ Jacob & Co Bugatti Chiron Sapphire Crystal BU800.30.BE.UA.ABRUA',1,349702,'2022-05-18',60,N'Mỹ',N'Jacob & Co Bugatti Chiron Tourbillon BU800.30.BE.UA.ABRUA này thì
chúng ta có thêm bộ vỏ nạm đá Tsavorite màu xanh lá cực kỳ nổi bật.',4,12),
(N'Đồng Hồ Jacob & Co Brilliant Flying Tourbillon Arlequino Pastel BT543.30.HX.UC.B',1,649702,'2022-05-18',60,N'Mỹ',N'Là một chiếc đồng hồ thuộc bộ sưu tập High Jewelry Masterpiece, Jacob & Co Brilliant Flying
Tourbillon Arlequino nổi bật với hàng trăm viên đá quý lấp lánh được nạm trên cả mặt số và viền bezel đồng hồ tạo nên một sự cuốn hút ngay từ cái nhìn đầu tiên.',4,12),
(N'JACOB & CO Astronomia Solar',1,300,'2022-05-18',60,N'Mỹ',N'JACOB & CO Astronomia Solar  với cổ máy Tourbillon lấy cảm hứng từ dãi thiên Hà quay quanh hệ mặt trời .',4,12),
(N'JACOB & CO EPIC SF24 FLYING TOURBILLON',1,500,'2022-05-18',60,N'Mỹ',N'Jacob & Co Epic SF24 Flying Tourbillon là một trong những thiết kế đáng kinh ngạc của Jacob & Co khi mang tới một cỗ máy đầy thể thao cùng thiết kế mặt số skeleton đầy táo bạo cùng những đường nét thủ công không thể tuyệt vời hơn.',4,10),


(N'Đồng hồ CASIO 44.4 × 43.2 mm Unisex W-218HC-4AVDF',5,25,'2022-05-19',60,N'Nhật Bản',N'Đồng hồ được trang bị khung viền và dây đeo nhựa có trọng lượng nhẹ, chịu lực tốt, hạn chế hư hỏng và tạo cảm giác mềm mại khi đeo.',5,9),
(N'Đồng hồ CASIO 52 mm Nam AEQ-120W-9AVDF',3,20,'2022-05-19',60,N'Nhật Bản',N'Mẫu đồng hồ này đến từ thương hiệu đồng hồ Casio nổi tiếng Nhật Bản. Thương hiệu với nhiều mẫu mã đa dạng phù hợp nhiều đối tượng sử dụng.',5,8),
(N'Đồng hồ CASIO 29 mm Nữ LA680WGA-9DF',5,35,'2022-05-19',60,N'Nhật Bản',N'Mang thương hiệu đồng hồ Casio chất lượng và lâu đời của Nhật Bản, quen thuộc với người Việt Nam. Bộ sản phẩm gồm: Hộp, Thẻ bảo hành, Hướng dẫn sử dụng',5,5),
(N'Đồng hồ CASIO 34 mm Nữ LRW-200H-4B2VDF',5,30,'2022-05-19',60,N'Nhật Bản',N'Mang thương hiệu đồng hồ Casio chất lượng và lâu đời của Nhật Bản, quen thuộc với người Việt Nam. Bộ sản phẩm gồm: Hộp, Thẻ bảo hành, Hướng dẫn sử dụng',5,2),
(N'Đồng hồ CASIO 31 mm Nữ LTP-1308D-2AVDF ',5,25,'2022-05-19',60,N'Nhật Bản',N'Mang thương hiệu đồng hồ Casio chất lượng và lâu đời của Nhật Bản, quen thuộc với người Việt Nam. Bộ sản phẩm gồm: Hộp, Thẻ bảo hành, Hướng dẫn sử dụng',5,4),




-----------------Nam------------------


(N'Đồng Hồ Citizen NH8352-53P',2,220,'2022-05-14',60,N'Nhật Bản',N'Đồng Hồ Citizen NH8352-53P Cho Nam là chiếc đồng hồ cao cấp đến từ thương hiệu Citizen nổi tiếng. Sở hữu gam màu nổi bật ngay từ khi có mặt trên thị trường Citizen NH8352-53P được nhiều tín đồ thời trang săn đón.',6,1), --== nam
(N'CITIZEN BM683809X CHANDLER MILITARY',4,170,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ CITIZEN Chandler Military, được đánh bóng và thoải mái, là sự bổ sung hoàn hảo cho bộ sưu tập đồng hồ của bạn. Nổi bật ở đây là vỏ bằng thép không gỉ, dây đeo màu nâu sẫm với mặt số và ngày tháng màu xanh lá cây. Với công nghệ Eco-Drive của chúng tôi - được cung cấp bởi ánh sáng, bất kỳ ánh sáng nào.',6,10),  --==nam
(N'Đồng Hồ Nam Citizen NH8365-19F',6,200,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ Citizen NH8365-19F cho Nam có thiết kế đơn giản nhưng nam tính, với mặt màu đen sử dụng số La Mã và dây được làm từ chất liệu da cao cấp, mang đến nét mạnh mẽ cho người đeo. Đường kính mặt 41mm với độ dày 11mm phù hợp với mọi cổ tay nam giới.',6,3), --== nam
(N'Đồng Hồ Citizen AN8167-53X',8,160,'2022-05-14',60,N'Nhật Bản',N'Đồng Hồ Citizen AN8167-53X Nam Chronograph Màu Đen Mặt Nâu đỏ là chiếc đồng hồ cao cấp đến từ thương hiệu Citizen nổi tiếng. Sở hữu thiết kế hiện đại cùng gam màu nổi bật Citizen AN8167-53X cho người dùng vẻ sang trọng, lịch lãm.',6,4), --==nam
(N'Đồng Hồ Citizen ER0212-50P',10,135,'2022-05-14',60,N'Nhật Bản',N'Đồng Hồ Citizen ER0212-50P Cho Nữ là chiếc đồng hồ cao cấp đến từ thương hiệu Citizen nổi tiếng. Khi sở hữu siêu phẩm này bạn sẽ thấy như cả thế giới ở trên cổ tay của mình. Ngay từ khi có mặt trên thị trường Citizen ER0212-50P đã được rất nhiều tín đồ thời trang yêu thích. ',6,6), --==nữ

(N'Đồng hồ Daniel Wellington 0102DW',2,145,'2022-05-14',60,N'Thụy Điển',N'Đồng hồ Daniel Wellington 0102DW có vỏ kim loại màu vàng sang trọng bao quanh nền số màu trắng trang nhã, kim chỉ và vạch số mỏng nổi bật trên nền số màu trắng trang nhã, logo DW được đặt ngay vị trí 12h nổi bật.',7,1), --== nam
(N'Đồng Hồ Daniel Wellington 0952DW',2,145,'2022-05-14',60,N'Thụy Điển',N'Đồng Hồ Daniel Wellington 0952DW được thiết kế tinh xảo đến từng chi tiết cùng dây vải Nato cao cấp với màu sắc ngọt ngào, nữ tính giúp phụ nữ thêm phần nổi bật',7,6), --==nữ
(N'Đồng Hồ Daniel Wellington Men’s 0206DW',2,150,'2022-05-14',60,N'Thụy Điển',N'Đồng hồ Daniel Wellington Men’s 0206DW với dây da màu đen sang trọng, lịch sự và nam tính giúp bạn luôn tự tin mỗi khi xuất hiện. Chiếc đồng hồ Daniel Wellington gọn nhẹ dễ dàng kết hợp với các loại trang phục khác nhau.',7,7), --== nam
(N'Đồng Hồ Daniel Wellington DW00100130',2,155,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ Daniel Wellington DW00100130 là chiếc đồng hồ đẹp xuất sắc từ thương hiệu Daniel Wellington sở hữu thiết kế tối giản với mặt số đơn giản ít chi tiết, dây da có phần cổ điển, lịch lãm.',7,3), --== nam
(N'Đồng Hồ Daniel Wellington 0902DW',2,120,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ Daniel Wellington 0902DW với dây da cao cấp khỏe khoắn, trẻ trung và hiện đại giúp phái nữ luôn nổi bật với sự xuất hiện của mình ',7,6), --== nữ

(N'DOXA D154TWH',2,6500,'2022-05-14',60,N'Thụy Sĩ',N'Mẫu Doxa D154TWH phiên bản giới hạn 1000 chiếc trên toàn thế giới, sự kết hợp cách điệu giữa các vạch số cùng chữ số la mã được mạ vàng sang trọng, điểm nhấn với nền mặt số Skeleton lộ máy.',8,12), --== nam
(N'DOXA D221RSV',4,1800,'2022-05-14',60,N'Thụy Sĩ',N'Ẩn bên dưới mặt kính Sapphire với nền mặt số được gia công tinh xảo đính các viên kim cương tạo nên phụ kiện thời trang sang trọng dành cho phái đẹp với phiên bản Doxa D221RSV.',8,3), --== nữ
(N'DOXA D203SBU',6,750,'2022-05-14',60,N'Thụy Sĩ',N'Mẫu Doxa D203SBU kiểu dáng mỏng mang lại vẻ ngoài trẻ trung tinh tế với nền mặt số được tạo hình hoa văn nổi phối cùng tông màu xanh nổi bật thời trang trước nền mặt kính Sapphire.',8,7), --== nam
(N'DOXA D215SWH',8,2350,'2022-05-14',60,N'Thụy Sĩ',N'Bên dưới mặt kính Sapphire nổi bật với thiết kế máy cơ lộ tim mang lại vẻ đẹp độc đáo trên nền mặt số xà cứ size 35mm phong cách thời trang cho phái đẹp với mẫu Doxa D215SWH.',8,12), --== nữ
(N'DOXA ĐÔI(D183TSV – D184TSV)',10,6500,'2022-05-14',60,N'Thụy Sĩ',N'Mẫu Doxa đôi với chi tiết núm vặn cùng kim chỉ mạ vàng nổi bật, nền mặt số trắng với thiết kế họa tiết trải tia tạo nên vẻ trẻ trung.',8,12), --==đôi

(N'FOSSIL ES4571',2,185,'2022-05-14',60,N'Mỹ',N'Mẫu Fossil ES4571 chi tiết kế kim chỉ cùng cọc vạch số tạo nét thanh mảnh nữ tính phối tone màu vàng hồng trẻ trung sang trọng cho phái đẹp.',9,1), --== nữ
(N'FOSSIL FS5325',4,160,'2022-05-14',60,N'Mỹ',N'Mẫu Fossil FS5325 với nền mặt số được phối tông màu xanh mang lại làng gió mới tạo nên vẻ thời trang cho phái nam, phối cùng bộ dây đeo chất liệu bằng da nâu trơn tăng thêm vẻ trẻ trung.',9,7), --== nam
(N'FOSSIL ES3284',6,185,'2022-05-14',60,N'Mỹ',N'Mẫu đồng hồ nữ thời trang sang trọng Fossil ES3284 được thiết kế tinh tế với các hạt pha lê được đính xung quanh đồng hồ còn có mạ vàng.',9,6), --== nữ
(N'FOSSIL ME3167',8,225,'2022-05-14',60,N'Mỹ',N'Fossil ME3167 là một trong những bộ máy hiếm hoi trong bộ sưu tập Fossil khi được trang bị máy cơ – một trong những bộ máy có nhiều tầng nghĩa, rất có ý nghĩa với ngành công nghiệp đồng hồ và đòi hỏi người dùng phải có một chút kiến thức trong quá trình sử dụng.',9,3), --== nam
(N'FOSSIL ME3159',10,350,'2022-05-14',60,N'Mỹ',N'Trẻ trung đầy lịch lãm với mẫu dây da được phối tông màu nâu với mẫu Fossil ME3159 mang trên mình phong cách độc đáo với nền mặt số kính phô diễn ra bộ máy cơ mạnh mẽ.',9,3), --== nam

(N'G-SHOCK GA-700-1ADR',2,165,'2022-05-14',60,N'Nhật Bản',N'Đồng hồ G-Shock GA-700-1ADR với thiết kế vỏ máy bằng nhựa kết hợp cùng dây đeo cao su khả năng chống nước cao, theo phong cách thể thao kết hợp mặt số điện tử với những tính năng tiện dụng.',10,7), --== nam
(N'G-SHOCK GA-2000-1A2DR',2,225,'2022-05-14',60,N'Nhật Bản',N'Mẫu G-Shock GA-2000-1A2DR phần vỏ viền ngoài tạo hình nền cọc số mang lại vẻ thể thao năng động cùng các ô số điện tử hiện thị chức năng lịch và đo thời gian.',10,7), --== nam
(N'BABY-G BSA-B100MC-8ADR',2,222,'2022-05-14',60,N'Nhật Bản',N'Mẫu Baby-G BSA-B100MC-8ADR nổi bật tính năng Bluetooth khả năng kết nối điện thoại, thiết kế dây vỏ nhựa chịu va đập cùng khả năng chịu nước lên đến 10atm.',10,7), --== nữ
(N'BABY-G BGA-150FL-7ADR',2,165,'2022-05-14',60,N'Nhật Bản',N'Mẫu Baby-G BGA-150FL-7ADR dây vỏ nhựa phối tone màu trắng kết hợp cùng nền mặt số tạo hình họa tiết đóa hoa tạo nên vẻ ngoài thời trang.',10,7), --== nam
(N'BABY-G BA-110CP-4ADR',2,235,'2022-05-14',60,N'Nhật Bản',N'Mẫu Baby-G BA-110CP-4ADR với phiên bản tổng thể vỏ máy cùng dây đeo phối tone màu hồng nhạt chủ đạo nổi bật vẻ thời trang cá tính cho các bạn nữ, nền mặt số điện tử hiện thị đa chức năng tăng thêm vẻ ngoài đầy năng động.',10,7),
--------------VƯƠNG----------------------

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

INSERT INTO Watches(Gender,GlassSizes,ATM,GlassColors,CaseColors,ProductId,GlassmaterialId,BraceletmaterialId,MachineinsideId) VALUES
-------TÍNH-------
(0,29,1,N'Trắng',N'Trắng',1,1,4,1),
(0,29,1,N'Hồng',N'Hồng',2,1,4,1),
(0,29,1,N'Trắng',N'Trắng',3,1,4,1),
(0,29,1,N'Đen',N'Đen',4,1,4,2),
(0,29,1,N'Lục',N'Lục',5,3,4,3),

(0,29,1,N'Trắng',N'Bạc',6,1,4,1),
(1,29,1,N'Trắng',N'Đen',7,3,4,2),
(1,29,1,N'Trắng',N'Vàng',8,3,6,1),
(1,29,1,N'Đen',N'Nâu Vàng',9,1,6,1),
(1,29,1,N'Đen',N'Nâu Vàng',10,3,2,2),

(1,29,1,N'Đen',N'Lam',11,1,2,1),
--12 là phụ kiện--
(0,29,1,N'Trắng',N'Hồng Cam',13,1,4,1),
(0,29,1,N'Đen',N'Đen',14,3,4,1),
(0,29,1,N'Trắng',N'Vàng',15,3,4,2),

(0,44,1,N'Vàng',N'Hồng',16,1,4,1),
(0,44,1,N'Bạc',N'Lục',17,1,4,1),
(0,44,1,N'Bạc',N'7 Màu',18,1,4,1),
(0,44,1,N'Trắng',N'Vàng',19,1,4,1),
(0,44,1,N'Trắng',N'Vàng Đen',20,1,4,1),

(2,29,1,N'Trắng',N'Hồng',21,2,1,2),
(2,29,1,N'Trắng',N'Đen',22,2,1,2),
(2,29,1,N'Trắng',N'Vàng',23,2,1,2),
(2,29,1,N'Trắng',N'Hồng',24,2,1,2),
(2,29,1,N'Trắng',N'Bạc',25,2,1,2),


-----------NAM---------------
(1,40,5,N'Vàng',N'Vàng',26,1,2,2),
(1,42,5,N'Bạc',N'Bạc',27,1,1,2),
(1,41,5,N'Đen',N'Đen',28,1,1,2),
(1,44,5,N'Nâu Đỏ',N'Đen',29,1,2,3),
(0,30,5,N'Trắng',N'Vàng',30,2,2,1),

(2,40,3,N'Trắng',N'Vàng',31,1,4,1),
(0,34,3,N'Trắng',N'Vàng',32,1,1,1),
(2,40,3,N'Trắng',N'Bạc',33,1,1,1),
(1,40,5,N'Đen',N'Bạc',34,1,1,2),
(0,26,3,N'Trắng',N'Vàng',35,1,1,1),

(1,40,3,N'Trắng',N'Vàng',36,2,1,2),
(0,31,5,N'Trắng',N'Bạc',37,2,2,2),
(1,39,5,N'Xanh',N'Bạc',38,2,1,1),
(0,35,5,N'Trắng',N'Bạc',39,2,2,2),
(2,45,3,N'Bạc',N'Bạc',40,2,2,2),

(0,36,3,N'Vàng Hồng',N'Vàng Hồng',41,1,2,1),
(1,44,5,N'Xanh',N'Bạc',42,1,1,1),
(0,29,5,N'Trằng',N'Vàng Hồng',43,1,2,1),
(1,44,5,N'Trắng',N'Bạc',44,1,2,2),
(1,42,5,N'Xanh',N'Bạc',45,1,1,2),

(1,57,20,N'Đen',N'Đen',46,1,6,3),
(1,51,20,N'Đen',N'Đen',47,1,6,3),
(0,49,10,N'Trắng',N'Xám',48,1,6,1),
(0,47,10,N'Trắng',N'Trắng',49,1,6,1),
(2,46,10,N'Trắng',N'Trắng Hồng',50,1,6,1),
--------------VƯƠNG------------------
(0,39,5,N'Bạc',N'Bạc',51,2,8,3),
(1,42,20,N'Xám',N'Xám',52,2,8,3),
(1,40,10,N'Xám',N'Bạc',53,2,1,1),
(1,39,20,N'Bạc',N'Đen',54,2,2,1),
(1,40,20,N'Bạc',N'Bạc',55,2,8,3),
(1,42,5,N'Bạc',N'Nâu',56,1,2,1),
(1,44,5,N'Xanh',N'Nâu',57,1,1,1),
(0,16,5,N'Trắng',N'Vàng',58,2,2,2),
(1,42,5,N'Đen',N'Bạc',59,4,1,2),
(0,34,10,N'Đồng',N'Đồng',60,3,2,3),
(0,60,10,N'Đồng',N'Đồng',61,3,3,1),
(0,18,5,N'Vàng',N'Vàng',62,1,2,1),
(0,34,10,N'Vàng',N'Hồng',63,1,1,1),
(0,34,10,N'Trắng',N'Bạc',64,1,1,1),
(0,60,10,N'Đồng',N'Đồng',65,3,3,1),
(1,42,20,N'Xanh',N'Đen',66,4,3,3),
(1,42,20,N'Đen',N'Bạc',67,2,1,3),
(1,42,20,N'Đen',N'Đen',68,4,2,3),
(1,44,20,N'Nâu',N'Bạc',69,2,1,1),
(1,42,20,N'Đen',N'Vàng',70,1,1,1),
(1,40,20,N'Đen',N'Hồng',71,4,8,3),
(0,38,20,N'Hồng',N'Hồng',72,4,8,3),
(1,41,20,N'Nâu',N'Hồng',73,4,8,3),
(0,36,20,N'Nâu',N'Hồng',74,4,8,3),
(1,42,20,N'Xanh',N'Bạc',75,4,8,3)
GO

INSERT INTO ProductPhotos VALUES
--------TÍNH--------
('babyg-analog-p1-1.jpg',1),
('babyg-analog-p1-2.jpg',1),
('babyg-analog-p1-3.jpg',1),
('babyg-analog-p1-4.jpg',1),

('babyg-digital-p2-1.jpg',2),
('babyg-digital-p2-2.jpg',2),
('babyg-digital-p2-3.jpg',2),
('babyg-digital-p2-4.jpg',2),

('babyg-automatic-p3-1.jpg',3),
('babyg-automatic-p3-2.jpg',3),
('babyg-automatic-p3-3.jpg',3),
('babyg-automatic-p3-4.jpg',3),


('babyg-luxury-p4-1.jpg',4),
('babyg-luxury-p4-2.jpg',4),
('babyg-luxury-p4-3.jpg',4),
('babyg-luxury-p4-4.jpg',4),


('babyg-chronograph-p5-1.jpg',5),
('babyg-chronograph-p5-2.jpg',5),
('babyg-chronograph-p5-3.jpg',5),
('babyg-chronograph-p5-4.jpg',5),

('bulova-quartz-p6-1.jpg',6),
('bulova-quartz-p6-2.jpg',6),
('bulova-quartz-p6-3.jpg',6),
('bulova-quartz-p6-4.jpg',6),


('bulova-automatic-p7-1.jpg',7),
('bulova-automatic-p7-2.jpg',7),
('bulova-automatic-p7-3.jpg',7),
('bulova-automatic-p7-4.jpg',7),


('bulova-quartz-p8-1.jpg',8),
('bulova-quartz-p8-2.jpg',8),
('bulova-quartz-p8-3.jpg',8),
('bulova-quartz-p8-4.jpg',8),


('bulova-quartz-p9-1.jpg',9),
('bulova-quartz-p9-2.jpg',9),
('bulova-quartz-p9-3.jpg',9),
('bulova-quartz-p9-4.jpg',9),

('bulova-automatic-p10-1.jpg',10),
('bulova-automatic-p10-2.jpg',10),
('bulova-automatic-p10-3.jpg',10),
('bulova-automatic-p10-4.jpg',10),


('calvinklein-quartz-p11-1.jpg',11),
('calvinklein-quartz-p11-2.jpg',11),
('calvinklein-quartz-p11-3.jpg',11),
('calvinklein-quartz-p11-4.jpg',11),


('calvinklein-phukien-p12-1-.jpg',12),

('calvinklein-dress-p13-1.jpg',13),
('calvinklein-dress-p13-2.jpg',13),
('calvinklein-dress-p13-3.jpg',13),
('calvinklein-dress-p13-4.jpg',13),

('calvinklein-mechanical-p14-1.jpg',14),
('calvinklein-mechanical-p14-2.jpg',14),
('calvinklein-mechanical-p14-3.jpg',14),
('calvinklein-mechanical-p14-4.jpg',14),


('calvinklein-pilot-p15-1.jpg',15),
('calvinklein-pilot-p15-2.jpg',15),
('calvinklein-pilot-p15-3.jpg',15),
('calvinklein-pilot-p15-4.jpg',15),


('jacobnco-luxury-p16-1.jpg',16),
('jacobnco-luxury-p16-2.jpg',16),
('jacobnco-luxury-p16-3.jpg',16),
('jacobnco-luxury-p16-4.jpg',16),

('jacobnco-luxury-p17-1.jpg',17),
('jacobnco-luxury-p17-2.jpg',17),
('jacobnco-luxury-p17-3.jpg',17),
('jacobnco-luxury-p17-4.jpg',17),

('jacobnco-luxury-p18-1.jpg',18),

('jacobnco-luxury-p19-1.jpg',19),
('jacobnco-luxury-p19-2.jpg',19),
('jacobnco-luxury-p19-3.jpg',19),
('jacobnco-luxury-p19-4.jpg',19),


('jacobnco-field-p20-1.jpg',20),
('jacobnco-field-p20-2.jpg',20),
('jacobnco-field-p20-3.jpg',20),
('jacobnco-field-p20-4.jpg',20),

('casio-pilot-p21-1.jpg',21),
('casio-pilot-p21-2.jpg',21),
('casio-pilot-p21-3.jpg',21),
('casio-pilot-p21-4.jpg',21),

('casio-mechanical-p22-1.jpg',22),
('casio-mechanical-p22-2.jpg',22),
('casio-mechanical-p22-3.jpg',22),
('casio-mechanical-p22-4.jpg',22),

('casio-driving-p23-1.jpg',23),
('casio-driving-p23-2.jpg',23),
('casio-driving-p23-3.jpg',23),
('casio-driving-p23-4.jpg',23),

('casio-digital-p24-1.jpg',24),
('casio-digital-p24-2.jpg',24),
('casio-digital-p24-3.jpg',24),
('casio-digital-p24-4.jpg',24),

('casio-chronograph-p25-1.jpg',25),
('casio-chronograph-p25-2.jpg',25),
('casio-chronograph-p25-3.jpg',25),
('casio-chronograph-p25-4.jpg',25),

-----------------NAM---------------------

('citizen-analog-p26-1',26),
('citizen-analog-p26-2',26),
('citizen-analog-p26-3',26),
('citizen-field-p27-1',27),
('citizen-field-p27-2',27),
('citizen-field-p27-3',27),
('citizen-automatic-p28-1',28),
('citizen-automatic-p28-2',28),
('citizen-automatic-p28-3',28),
('citizen-automatic-p29-1',29),
('citizen-automatic-p29-2',29),
('citizen-automatic-p29-3',29),
('citizen-dress-p30-1',30),
('citizen-dress-p30-2',30),
('citizen-dress-p30-3',30),


('daniel wellington-analog-p31-1',31),
('daniel wellington-analog-p31-2',31),
('daniel wellington-analog-p31-3',31),
('daniel wellington-dress-p32-1',32),
('daniel wellington-dress-p32-2',32),
('daniel wellington-dress-p32-3',32),
('daniel wellington-quartz-p33-1',33),
('daniel wellington-quartz-p33-2',33),
('daniel wellington-quartz-p33-3',33),
('daniel wellington-automatic-p34-1',34),
('daniel wellington-automatic-p34-2',34),
('daniel wellington-automatic-p34-3',34),
('daniel wellington-dress-p35-1',35),
('daniel wellington-dress-p35-2',35),
('daniel wellington-dress-p35-3',35),

('doxa-automatic-p36-1',36),
('doxa-automatic-p36-2',36),
('doxa-automatic-p36-3',36),
('doxa-luxury-p37-1',37),
('doxa-luxury-p37-2',37),
('doxa-luxury-p37-3',37),
('doxa-quartz-p38-1',38),
('doxa-quartz-p38-2',38),
('doxa-quartz-p38-3',38),
('doxa-luxury-p39-1',39),
('doxa-luxury-p39-2',39),
('doxa-luxury-p39-3',39),
('doxa-luxury-p40-1',40),

('fossil-analog-p41-1',41),
('fossil-analog-p41-2',41),
('fossil-analog-p41-3',41),
('fossil-quartz-p42-1',42),
('fossil-quartz-p42-2',42),
('fossil-quartz-p42-3',42),
('fossil-dress-p43-1',43),
('fossil-dress-p43-2',43),
('fossil-dress-p43-3',43),
('fossil-automatic-p44-1',44),
('fossil-automatic-p44-2',44),
('fossil-automatic-p44-3',44),
('fossil-automatic-p45-1',45),
('fossil-automatic-p45-2',45),
('fossil-automatic-p45-3',45),

('gshock-quartz-p46-1',46),
('gshock-quartz-p46-2',46),
('gshock-quartz-p46-3',46),
('gshock-quartz-p47-1',47),
('gshock-quartz-p47-2',47),
('gshock-quartz-p47-3',47),
('gshock-quartz-p48-1',48),
('gshock-quartz-p48-2',48),
('gshock-quartz-p48-3',48),
('gshock-quartz-p49-1',49),
('gshock-quartz-p49-2',49),
('gshock-quartz-p49-3',49),
('gshock-quartz-p50-1',50),
('gshock-quartz-p50-2',50),
('gshock-quartz-p50-3',50),


---------------VƯƠNG------------------
('longines-analog-p1-1.jpg',51),
('longines-analog-p1-2.jpg',51),
('longines-analog-p1-3',51),
('longines-analog-p1-4',51),
('longines-digital-p2-1.jpg',52),
('longines-digital-p2-2.jpg',52),
('longines-digital-p2-3.jpg',52),
('longines-digital-p2-4.jpg',52),
('longines-diving-p3-1.jpg',53),
('longines-diving-p3-2.jpg',53),
('longines-diving-p3-3.jpg',53),
('longines-diving-p3-4.jpg',53),
('longines-quartz-p4-1.jpg',54),
('longines-quartz-p4-2.jpg',54),
('longines-quartz-p4-3.jpg',54),
('longines-quartz-p4-4.jpg',54),
('longines-luxury-p5-1.jpg',55),
('longines-luxury-p5-2.jpg',55),
('longines-luxury-p5-3.jpg',55),
('longines-luxury-p5-4.jpg',55),
('pulsar-automatic-p1-1.jpg',56),
('pulsar-automatic-p1-2.jpg',56),
('pulsar-automatic-p1-3.jpg',56),
('pulsar-automatic-p1-4.jpg',56),
('pulsar-quartz-p1.jpg',57),
('pulsar-quartz-p2.jpg',57),
('pulsar-quartz-p3.jpg',57),
('pulsar-quartz-p4.jpg',57),
('pulsar-mechanical-p1.jpg',58),
('pulsar-mechanical-p2.jpg',58),
('pulsar-mechanical-p3.jpg',58),
('pulsar-mechanical-p4.jpg',58),
('pulsar-diving-p1.jpg',59),
('pulsar-diving-p2.jpg',59),
('pulsar-diving-p3.jpg',59),
('pulsar-diving-p4.jpg',59),
('pulsar-chronograph-p1.jpg',60),
('pulsar-chronograph-p2.jpg',60),
('pulsar-chronograph-p3.jpg',60),
('pulsar-chronograph-p4.jpg',60),
('saga-p1-1.jpg',61),
('saga-p1-2.jpg',61),
('saga-p1-3.jpg',61),
('saga-p1-4.jpg',61),
('saga-chronograph-p2-1.jpg',62),
('saga-chronograph-p2-2.jpg',62),
('saga-chronograph-p3-3.jpg',62),
('saga-chronograph-p4-4.jpg',62),
('saga-diving-p3-1.jpg',63),
('saga-diving-p3-2.jpg',63),
('saga-diving-p3-3.jpg',63),
('saga-diving-p3-4.jpg',63),
('saga-pilot-p4-1.jpg',64),
('saga-pilot-p4-2.jpg',64),
('saga-pilot-p4-3.jpg',64),
('saga-pilot-p4-4.jpg',64),
('saga-p5-1.jpg',65),
('saga-p5-2.jpg',65),
('saga-p5-3.jpg',65),
('saga-p5-4.jpg',65),
('seiko-smart-p1-1.jpg',66),
('seiko-smart-p1-2.jpg',66),
('seiko-smart-p1-3.jpg',66),
('seiko-smart-p1-4.jpg',66),
('seiko-dress-p2-1.jpg',67),
('seiko-dress-p2-2.jpg',67),
('seiko-dress-p2-3.jpg',67),
('seiko-dress-p2-4.jpg',67),
('seiko-mechanical-p3-1.jpg',68),
('seiko-mechanical-p3-2.jpg',68),
('seiko-mechanical-p3-3.jpg',68),
('seiko-mechanical-p3-4.jpg',68),
('seiko-quartz-p4-1.jpg',69),
('seiko-quartz-p4-2.jpg',69),
('seiko-quartz-p4-3.jpg',69),
('seiko-quartz-p4-4.jpg',69),
('seiko-analog-p5-1.jpg',70),
('seiko-analog-p5-2.jpg',70),
('seiko-analog-p5-3',70),
('seiko-analog-p5-4',70),
--rolex
('rolex-luxury-p1-1.jpg',71),
('rolex-luxury-p1-2.jpg',71),
('rolex-luxury-p1-3.jpg',71),
('rolex-luxury-p1-4.jpg',71),
('rolex-luxury-p2-1.jpg',72),
('rolex-luxury-p2-2.jpg',72),
('rolex-luxury-p2-3.jpg',72),
('rolex-luxury-p2-4.jpg',72),
('rolex-luxury-p3-1.jpg',73),
('rolex-luxury-p3-2.jpg',73),
('rolex-luxury-p3-3.jpg',73),
('rolex-luxury-p3-4.jpg',73),
('rolex-luxury-p4-1.jpg',74),
('rolex-luxury-p4-2.jpg',74),
('rolex-luxury-p4-3.jpg',74),
('rolex-luxury-p4-4.jpg',74),
('rolex-luxury-p5-1.jpg',75),
('rolex-luxury-p5-2.jpg',75),
('rolex-luxury-p5-3.jpg',75),
('rolex-luxury-p5-4.jpg',75)



