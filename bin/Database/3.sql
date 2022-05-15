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
	CreateDate DATETIME NOT NULL,
	IsDeleted BIT DEFAULT 0,
);
GO

CREATE TABLE RefAccounts(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Refer VARCHAR(50), -- người nhập mã giới thiệu
	Receiver VARCHAR(50), -- người giới thiệu
	IsReward BIT DEFAULT 0,
	FOREIGN KEY (Receiver) REFERENCES Accounts(Username),
);
GO

CREATE TABLE Authorities(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Username VARCHAR(50) NOT NULL,
	RoleId VARCHAR(15) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username),
	FOREIGN KEY (RoleId) REFERENCES Roles(Id)
);
GO

CREATE TABLE Users(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Fullname NVARCHAR(50) NOT NULL,
	Dob DATE NOT NULL,
	Gender INT DEFAULT 1, -- 0 Nữ | 1 Nam | 2 Others 
	Phone VARCHAR(12) NOT NULL,
	Email VARCHAR(100) UNIQUE  NOT NULL,
	Photo VARCHAR(255) NULL,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);


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

/*
CREATE TABLE Wallets(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	GcoinId INT,
	VoucherListId INT,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username),
	FOREIGN KEY (GcoinId) REFERENCES Gcoins(id),
	FOREIGN KEY (VoucherListId) REFERENCES VoucherLists(id)
);
GO
*/

CREATE TABLE Brands(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(250) NOT NULL
);
GO

CREATE TABLE Categories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL
);
GO

CREATE TABLE SubCategories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(50) NOT NULL,
	CategoryId INT,
	FOREIGN KEY (CategoryId) REFERENCES Categories(Id)
);
GO

CREATE TABLE Products(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(255) NOT NULL,
	Price FLOAT DEFAULT 0,
	Material NVARCHAR(200) NOT NULL, -- chất liệu
	MadeIn NVARCHAR(50) NOT NULL,
	CreateDate DATETIME NOT NULL,
	Gender INT DEFAULT 0, -- 0 Nữ | 1 Nam | 2 Unisex 
	Detail NTEXT, -- Mô tả thêm thông tin
	BrandId INT NOT NULL,
	CategoryId INT NULL,
	SubCategoryId INT NULL,
	FOREIGN KEY (SubCategoryId) REFERENCES SubCategories(Id),
	FOREIGN KEY (CategoryId) REFERENCES Categories(Id),
	FOREIGN KEY (BrandId) REFERENCES Brands(Id)
);
GO

-- Giảm giá sản phẩm
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

CREATE TABLE ProductPhotos(
	Id VARCHAR(100) PRIMARY KEY,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

CREATE TABLE ProductDetails(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	DetailKey NVARCHAR(150) NOT NULL,
	DetailValue NVARCHAR(150) NOT NULL,
	ProductId INT NOT NULL,
	FOREIGN KEY (ProductId) REFERENCES Products(Id)
);
GO

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

CREATE TABLE OrderStatuses(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	Name NVARCHAR(25)
);
GO

CREATE TABLE Orders(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	CreateDate DATE NOT NULL,
	Address NTEXT NOT NULL,
	Username VARCHAR(50) NOT NULL,
	OrderStatusId INT NOT NULL,
	FOREIGN KEY (OrderStatusId) REFERENCES OrderStatuses(Id),
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
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


CREATE TABLE Histories(
	Id INT IDENTITY(1,1) PRIMARY KEY,
	IdObject NVARCHAR(50) NOT NULL,
	ObjectName NVARCHAR(50) NOT NULL,
	DetailKey NVARCHAR(150) NOT NULL,
	DetailValue NVARCHAR(150) NOT NULL,
	CreateDate DATETIME NOT NULL,
	Username VARCHAR(50) NOT NULL,
	FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

--==============================================================================================================
--======= Roles
INSERT INTO Roles(Id, Name) VALUES
('CUSTOMER', N'Khách hàng'),
('MANAGER', N'Quản lý'),
('ADMIN', N'Chủ cửa hàng')
GO

--======= Accounts
INSERT INTO Accounts(Username, Password, CreateDate, IsDeleted) VALUES
('admin01', 'admin01', '2021-07-19 10:00:00 AM', 0),
('manager01', 'manager01', '2021-07-19', 0),
('manager02', '123', '2021-07-19', 0),
('customer1', 'customer1', '2021-12-13', 0),
('customer2', '123', '2022-02-26', 0),
('customer3', '123', '2022-02-26', 0),
('customer4', '123', '2022-02-26', 0),
('customer5', '123', '2021-12-13', 0),
('customer6', '123', '2021-12-13', 0),
('customer7', '123', '2021-12-13', 0),
('customer8', '123', '2021-12-13', 0),
('customer9', '123', '2022-04-15', 0),
('customer10', '123', '2022-04-15', 0),
('customer11', '123', '2022-04-13', 0),
('customer12', '123', '2022-03-15', 0),
('customer13', '123', '2022-01-15', 0)
GO

--======= RefAccounts
INSERT INTO RefAccounts(Refer, Receiver, IsReward) VALUES
('customer2', 'customer1', 0),
('customer3', 'customer1', 0),
('customer4', 'customer1', 0),
('customer5', 'customer1', 0),
('customer6', 'customer1', 0),
('customer7', 'customer2', 0),
('customer8', 'customer2', 0),
('customer9', 'customer3', 0),
('customer10', 'customer4', 0)
GO

--======= Authorities
INSERT INTO Authorities(Username, RoleId) VALUES
('admin01', 'ADMIN'),
('manager01', 'MANAGER'),
('manager02', 'MANAGER'),
('customer1', 'CUSTOMER'),
('customer2', 'CUSTOMER'),
('customer3', 'CUSTOMER'),
('customer4', 'CUSTOMER'),
('customer5', 'CUSTOMER'),
('customer6', 'CUSTOMER'),
('customer7', 'CUSTOMER'),
('customer8', 'CUSTOMER'),
('customer9', 'CUSTOMER'),
('customer10', 'CUSTOMER'),
('customer11', 'CUSTOMER'),
('customer12', 'CUSTOMER'),
('customer13', 'CUSTOMER')
GO

--======= Users
INSERT INTO Users(Fullname, Dob, Gender, Phone, Email, Photo, Username) VALUES
(N'Trần Trung Nghĩa', '1997-04-13', 1, '0366888470', 'nghiattps14820@fpt.edu.vn', 'nghia.jpg', 'admin01')

GO

--======= Vouchers
INSERT INTO Vouchers(Name, Discount, CreateDate, EndDate, CreateBy) VALUES
('DISCOUNT01', 10, '20220501 10:00:09 AM', '20220830 10:00:09 AM', 'manager01'),
('DISCOUNT02', 5, '20220501 10:00:09 AM', '20220830 10:00:09 AM', 'manager01'),
('DISCOUNT03', 20, '20220213 07:00:00 AM', '20220213 11:00:00 PM', 'manager02'),
('DISCOUNT04', 15, '20220111 06:30:00 AM', '20220112 06:30:00 AM', 'manager02'),
('DISCOUNT05', 10, '20220315 12:00:00 AM', '20220316 12:00:00 PM', 'manager01')
GO

--======= Gcoins
INSERT INTO Gcoins(Gcoin, Username) VALUES
(10, 'customer1'),
(5, 'customer2'),
(20, 'customer3'),
(10, 'customer4'),
(0, 'customer5'),
(0, 'customer6'),
(10, 'customer7'),
(3, 'customer8'),
(7, 'customer9'),
(9, 'customer10'),
(1, 'customer11'),
(0, 'customer12'),
(0, 'customer13')
GO

--======= VoucherLists
INSERT INTO VoucherLists(VoucherID, Username) VALUES
(1, 'customer1'),
(2, 'customer1'),
(3, 'customer1'),
(4, 'customer1'),
(1, 'customer2'),
(3, 'customer2'),
(4, 'customer4'),
(5, 'customer5')
GO

--======= Brands
INSERT INTO Brands(Name) VALUES
--======= A
('Avène'),
('Atelier Cologne'),
('Asics'),
('Artistic&Co'),
('Artist Couture'),
('Armitron'),
('Armaf'),
('Aristino'),
('Ariana Grande'),
('Aokang'),
('Anta'),
('Anne Klein'),
('Anna Sui'),
('Anello'),
('AndZ'),
('Andrew Marc'),
('Anastasia Beverly Hills'),
('Amouage'),
('AmorePacific'),
('Amiri'),
('Amika'),
('American Eagle'),
('Am Young'),
('Alterna'),
('Algenist'),
('Alexander Mcqueen'),
('Aldo'),
('Albion'),
('Alaia Paris'),
('Al Haramain'),
('Afnan Perfumes'),
('Adwoa Beauty'),
('Adidas'),
('Acqua Di Parma'),
('Acmé De La Vie'),
('Abercrombie & Fitch'),
('A-Derma'),

--======= B
('Byredo'),
('By Far'),
('Bvlgari'),
('Burt s Bees'),
('Burgi'),
('Burberry'),
('Bumble And Bumble'),
('Bulova'),
('Bullko'),
('Brut'),
('Britney Spears'),
('Briogeo'),
('Bread beauty'),
('Braun'),
('Bottega Veneta'),
('Bonpoint'),
('Bond No.9'),
('Boldr'),
('Bobbi Brown'),
('Blue Lizard'),
('Bite Beauty'),
('Biologique Recherche'),
('Bioderma'),
('Better Not Younger'),
('Bershka'),
('Bentley'),
('Benkii'),
('Benefit Cosmetics'),
('Belulu'),
('Beentrill'),
('Beautyblender'),
('BeautyBio'),
('Be Classy'),
('Bape'),
('Balmain'),
('Bally'),
('Balenciaga'),
--======= C
('Cure'),
('Crocs'),
('Cristiano Ronaldo'),
('Creed'),
('Cosrx'),
('Corthe'),
('Corèle V'),
('Converse'),
('Comly'),
('Comfort Zone'),
('Cole Haan'),
('Coach'),
('Clive Christian'),
('Clinique'),
('Clean Reserve'),
('Clé De Peau'),
('Clarins'),
('Clae'),
('Civasan'),
('Citizen'),
('Cinema Secrets'),
('Christina'),
('Christian Louboutin'),
('Chopard'),
('Chloé'),
('Chaumet'),
('Charriol'),
('Charlotte Tilbury'),
('Charles & Keith'),
('Chanel'),
('Champion'),
('Cellcosmet & Cellmen'),
('Celine'),
('Caudalie'),
('Casmara'),
('Casio'),
('Cartier'),
('Carolina Herrera'),
('Calvin Klein'),
('Callaway'),

--======= D
('Dyson'),
('Dunhill'),
('Ducray'),
('DSquared2'),
('Drunk Elephant'),
('Dr. Barbara Sturm'),
('Dr Dennis Gross'),
('dpHUE'),
('Dooney & Bourke'),
('Domba'),
('Dolce & Gabbana'),
('Dockers'),
('DKNY'),
('Dita'),
('Diptyque'),
('Dior'),
('Diesel'),
('Dickies'),
('DHC'),
('DevaCurl'),
('Dermeden'),
('Dermaquest'),
('Dermalogica'),
('Dermaceutic'),
('Dermaceleb'),
('Decorté'),
('DBH'),
('Davidoff'),
('Darphin'),
('Daniel Wellington'),
('Danessa Myricks Beauty'),

--=== E
('Express'),
('Exideal'),
('Eve Lom'),
('Eucerin'),
('Etat Libre dOrange'),
('Estée Lauder'),
('Ermenegildo Zegna'),
('Enzo Ferrari'),
('Endocare'),
('Emporio Armani'),
('Emile Chouriet'),
('EltaMD'),
('Elly'),
('Ellis Brooklyn'),
('Elizabeth Taylor'),
('Elizabeth Arden'),
('Elisabetta Franchi'),
('Elie Saab'),
('Edox'),
('Edition'),

--=== F
('Fusion Meso'),
('Furla'),
('Fresh'),
('Frederique Constant'),
('Frederic Malle'),
('Fred Perry'),
('Freck Beauty'),
('Franck Muller'),
('Franck Boclet'),
('Fossil'),
('Forvr Mood'),
('Foreo'),
('Floral Street'),
('Find Kapoor'),
('Fila'),
('Ferre Milano'),
('Fenty Beauty'),
('Fenty Beauty'),
('Fear Of God - FOG'),
('FaceGym'),
--=== G
('Guess'),
('Guerlain'),
('Gucci'),
('Gruppo Gamma'),
('Grès'),
('Goyard'),
('Gosto'),
('GoodnDoc'),
('Glow Recipe'),
('GLO Science'),
('GlamGlow'),
('Givenchy'),
('Giovanni'),
('Giorgio Armani'),
('GiGi'),
('Gianni Conti'),
('Ghala Zayed'),
('Gevril'),
('Geox'),
('Gentle Monster'),
('Genie'),

--===== H
('Hydrogen'),
('Huxley'),
('Hugo Boss'),
('Huda Beauty'),
('Hublot'),
('Hourglass'),
('HoMedics'),
('Hollister'),
('Hitachi'),
('Histolab'),
('Hermès'),
('Herbivore Botanicals'),
('HeraDG'),
('Henry London'),
('Heliocare'),
('HBB'),
('Hanboro'),

--==== I
('It Cosmetics'),
('Issey Miyake'),
('Isle Of Paradise'),
('iS Clinical'),
('Invicta'),
('InstaNatural'),
('Initio Parfums Prives'),
('Inglot'),
('Image'),
('ILIA'),

--==== J
('Jw Pei'),
('Juvera Cosmetic'),
('Just Cavalli'),
('Julius'),
('Juliette Has A Gun'),
('Juicy Couture'),
('Josie Maran'),
('John Varvatos'),
('Jo Malone'),
('Jimmy Choo'),
('Jemmia Diamond'),
('Jean Paul Gaultier'),
('Jaguar'),
('Jacquemus'),
('Jack Black'),

--=====K
('KVD Beauty'),
('Knot'),
('Kilian'),
('Kiehl s'),
('Kérastase'),
('Kenzo'),
('Kenneth Cole'),
('Kate Spade'),
('Kate Somerville'),
('Karl Lagerfeld'),
('Kalos'),
('Kakkuda'),
('Kaja'),

--==== L
('Lyn'),
('Lucien Piccard'),
('Louis Vuitton'),
('Longines'),
('Laura Mercier'),
('L Oreal'),
('Lanvin'),
('Longchamp'),
('Lolita Lempicka'),
('LifeWork'),
('Li-Ning'),
('Levi s'),
('La Prairie'),
('Lộc Phúc Fine Jewelry'),
('Le Labo'),

--==== M
('Murad'),
('MTG'),
('Movado'),
('Moschino'),
('M.A.C'),
('Maison Martin Margiela'),
('Mango'),
('Maurice Lacroix'),
('Masque'),
('Meez'),
('MLB'),
('Molton Brown'),
('Montale'),
('Melvita'),
('Meishoku'),

--==== N
('Nails Inc'),
('NuFace'),
('Nike'),
('New Era'),
('New Balance'),
('Nest New York'),
('NBS'),
('Neil Barrett'),
('Nasomatto'),
('Nina Ricci'),
('Nars'),
('Neoretin'),
('Nautica'),
('Nishane'),
('Native'),

--=== O
('Off-White'),
('Obagi Medical'),
('Oakley'),
('Ogival'),
('Ohui'),
('OleHenriksen'),
('Omate'),
('Omega'),
('Orient'),
('Origins'),
('Oriza L. Legrand'),
('Ormonde Jayne'),
('Ouai'),

--===== P
('Pureology'),
('Puma'),
('Pull&Bear'),
('PrettyFit'),
('Prada'),
('PNJ'),
('Pixi'),
('Piaget'),
('Phillip Lim'),
('Philipp Plein'),
('Pedro'),
('Paco Rabanne'),
('Palm Angels'),
('Pandora'),
('Parker'),
('Peace Out'),

--==== Q
('QMS Medicosmetics'),
('Q&Q'),



--==== R
('Rolex'),
('Royal Crown'),
('Rossano Ferretti'),
('Rosie Jane'),
('RoC'),
('Roja Parfums'),
('Robert Wayne'),
('RMS Beauty'),
('Reebok'),
('Repetto Paris'),
('Ralph Lauren'),
('Rado'),
('Railtek'),
('Raymond Weil'),
('Rimowa'),

--==== S
('Saint laurent'),
('SVR'),
('Swarovski'),
('SY Medipharm'),
('Supergoop'),
('Supperdry'),
('Stuart Weitzman'),
('SkinClinic'),
('Skinbetter Science'),
('SK-II'),
('Shiseido'),
('SevenFriday'),
('Seiko'),
('Samsung'),
('Salvatore Ferragamo'),

--==== T
('Trussardi'),
('TruSkin Naturals'),
('Triumph'),
('Transino'),
('Tory Burch'),
('Topman'),
('Too Faced'),
('Tommy Hilfiger'),
('Tom Ford'),
('Tissot'),
('Timberland'),
('Tiffany & Co'),
('The Perfect Derma Peel'),
('The North Face'),
('The Body Shop'),

--==== U
('Urban Decay'),
('Uniqlo'),
('Under Armour'),

--=== V
('Voluspa'),
('Virtue'),
('Vionic'),
('Violet Voss'),
('Viniciobelt'),
('Viktor & Rolf'),
('Victorinox'),
('Vichy'),
('Versace'),
('Venuco'),
('Vascara'),
('Vans'),
('Valentino'),
('Victoria s Secret'),
('Van Cleef & Arpels'),

--==== W
('WULFUL'),
('Wilson'),
('Whoo'),
('WHOAU'),
('Westman Atelier'),
('Wellmaxx'),


--=== X
('Xerjoff'),
('X-Ray'),

--==== Y
('YSL'),
('Yaman'),

--====
('Zo Skin Health'),
('Ziozia'),
('Zenith'),
('Zelos'),
('Zara'),
('ZamST'),
('Zadig & Voltaire')
GO

--======= Categories
INSERT INTO Categories(Name) VALUES
(N'ĐỒNG HỒ'),
(N'TÚI XÁCH'),
(N'NƯỚC HOA'),
(N'MỸ PHẨM'),
(N'GIÀY'),
(N'THỜI TRANG'),
(N'MŨ NÓN'),
(N'KÍNH MĂT'),
(N'SON MÔI'),
(N'TRANG SỨC')
--======

GO

--======= SubCategories
INSERT INTO SubCategories (Name,CategoryId) VALUES

--== Đồng hồ
(N'Đồng hồ cơ',1),
(N'Đồng hồ điện tử',1),
(N'Đồng hồ đôi',1),
(N'Đồng hồ trẻ em',1),

--== Túi xách
(N'Túi đeo chéo',2),
(N'Túi xách tay',2),
(N'Balo',2),
(N'Túi cầm tay',2),
(N'Túi tote',2),
(N'Cặp xách tay',2),
(N'Túi laptop',2),
(N'Ví',2),
(N'Túi trống',2),
(N'Vali',2),
(N'Túi đeo vai',2),
(N'Túi đeo hông',2),
(N'Túi đeo Golf',2),
(N'Túi đeo cổ',2),
(N'Ví cáng dài',2),
(N'Túi du lịch',2),
(N'Túi đựng đồ',2),
(N'Túi tennnis',2),

--== Nước hoa
(N'Nước hoa xe hơi',3),
(N'Xịt dưỡng thể',3),


--== Mỹ phẩm
(N'Kem dưỡng da',4),
(N'Tinh chất/Serium',4),
(N'Kem/Sữa dưỡng thể',4),
(N'Kem/Gel trị mụn',4),
(N'Nước/Dầu tẩy trang',4),
(N'Kem/Sữa dưỡng ẩm',4),
(N'Nước hoa hồng/Toner',4),
(N'Phấn phủ',4),
(N'Dầu gội/Dầu xả',4),
(N'Nước dưỡng/Lotion',4),
(N'Tẩy tế bào chết',4),
(N'Mascara',4),
(N'Dưỡng mắt',4),
(N'Nước thần',4),
(N'Sữa tắm',4),
(N'Set trắng da trị nám',4),
(N'Dưỡng trắng',4),
(N'Xịt khoáng',4),
(N'Dầu dưỡng',4),
(N'Kem tan mỡ',4),
(N'Gel dưỡng',4),
(N'Phấn phủ',4),
(N'Xịt trang điểm',4),
(N'Kem dưỡng da tay',4),
(N'Kem dưỡng da chân',4),
(N'Kem dưỡng môi',4),
(N'Tẩy tế bào chết môi',4),
(N'Dưỡng móng tay',4),
(N'Dưỡng mi',4),

--== Giày
(N'Giày thể thao',5),
(N'Giày cao gót',5),
(N'Giày cổ thấp',5),
(N'Giày cổ cao',5),
(N'Giày lười',5),
(N'Giày tây',5),
(N'Giày da',5),
(N'Giày bóng đá',5),
(N'Giày vải',5),
(N'Giày cao su',5),
(N'Ủng',5),
(N'Guốc',5),
(N'Xăng đan và dép',5),
(N'Giày đế bằng',5),
(N'Sneakers',5),

--== Thời trang
(N'Áo thun',6),
(N'Áo sơ mi',6),
(N'Áo dài tay',6),
(N'Áo phông',6),
(N'Áo khoác',6),
(N'Áo choàng',6),
(N'Áo hoddie',6),
(N'Quần tây',6),
(N'Quần kaki',6),
(N'Quần đùi',6),
(N'Quần bò',6),
(N'Quần tất',6),
(N'Quần jean',6),
(N'Đồ thể thao',6),
(N'Đầm',6),
(N'Váy',6),


--==Mũ,Nón
(N'Mũ len',7),
(N'Mũ lưỡi trai',7),
(N'Mũ tròn',7),
(N'Mũ nồi',7),
(N'Mũ golf',7),
(N'Mũ/Nón bucket',7),

--== Kính mắt
(N'Kính mắt cận',8),
(N'Kính chống nắng',8),
(N'Kính cận thời trang',8),

--== Son môi
(N'Son lì',9),
(N'Son kem',9),
(N'Son dưỡng môi',9),
(N'Son nước',9),
(N'Son bóng',9),
(N'Son nhũ',9),


--== Trang sức
(N'Dây chuyền',10),
(N'Khuyên tai',10),
(N'Vòng đeo tay',10),
(N'Cài áo',10),
(N'Nhẫn',10),
(N'Nhẫn cưới',10),
(N'Nhẫn đính hôn',10),
(N'Nhẫn cầu hôn',10),
(N'Nhẫn kim cương',10),
(N'Hạt vòng charm',10)

GO

--======= Products

--======= ProductDiscount

--======= ProductPhotos

--======= ProductDetails

--======= ProductLikes

--======= ProductEvaluations

--======= ProductComments

--======= CommentPhotos

--======= ProductReplies

--======= Orders

--======= OrderDetails

--======= Histories

