
	USE MASTER
	GO
	DROP DATABASE GodShop
	GO


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

('Cityzen'), --= 6
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
(N'Kính cứng'),
(N'Kính Sapphire'),
(N'Kính nhựa')
GO

INSERT INTO BraceletMaterials(Name) VALUES
(N'Dây da'),
(N'Dây kim loại'),
(N'Dây lưới'),
(N'Dây vải'),
(N'Dây da cá sấu'),
(N'Dây nhựa/ cao su'),
(N'Dây TiTanium')
GO

INSERT INTO MachineInsides(Name) VALUES
(N'Pin (Quartz)'),
(N'Cơ (Automatic)'),
(N'Lightning'),
(N'Pin - Automatic')
GO


INSERT INTO Products(Name,Quantity,Price,CreateDate,Warranty,MadeIn,Detail,BrandId,CategoryId) VALUES
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
(N'BABY-G BA-110CP-4ADR',2,235,'2022-05-14',60,N'Nhật Bản',N'Mẫu Baby-G BA-110CP-4ADR với phiên bản tổng thể vỏ máy cùng dây đeo phối tone màu hồng nhạt chủ đạo nổi bật vẻ thời trang cá tính cho các bạn nữ, nền mặt số điện tử hiện thị đa chức năng tăng thêm vẻ ngoài đầy năng động.',10,7)--== nam


GO

INSERT INTO Watches(Gender,GlassSizes,ATM,GlassColors,CaseColors,ProductId,GlassmaterialId,BraceletmaterialId,MachineinsideId) VALUES

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
(2,46,10,N'Trắng',N'Trắng Hồng',50,1,6,1)

GO

INSERT INTO ProductPhotos(Id,ProductId) VALUES
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
('gshock-quartz-p50-3',50)
GO