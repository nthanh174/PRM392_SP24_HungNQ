CREATE DATABASE ShopF;
GO
USE ShopF;
GO
-- Create Category Table
CREATE TABLE Category (
    categoryId INT IDENTITY(1,1) PRIMARY KEY,
    categoryName NVARCHAR(255) ,
    createdAt DATETIME  DEFAULT GETDATE(),
    updatedAt DATETIME  DEFAULT GETDATE()
);

-- Create Product Table
CREATE TABLE Product (
    productId INT IDENTITY(1,1) PRIMARY KEY,
    productName NVARCHAR(255) ,
    quantity INT ,
    description NVARCHAR(MAX) ,
    price DECIMAL(18, 2) ,
    discount DECIMAL(18, 2) ,
    image NVARCHAR(MAX),
    categoryId INT,
    sold INT  DEFAULT 0,
    createdAt DATETIME  DEFAULT GETDATE(),
    updatedAt DATETIME  DEFAULT GETDATE(),
    FOREIGN KEY (categoryId) REFERENCES Category(categoryId)
);

-- Create Role Table
CREATE TABLE Role (
    roleId INT IDENTITY(1,1) PRIMARY KEY,
    roleName NVARCHAR(50) 
);

-- Create User Table
CREATE TABLE [User] (
    userId INT IDENTITY(1,1) PRIMARY KEY,
    email NVARCHAR(255)  UNIQUE,
    username NVARCHAR(255)  UNIQUE,
    password NVARCHAR(255) ,
    phone NVARCHAR(50) ,
    roleId INT ,
    dob DATE ,
    createdAt DATETIME  DEFAULT GETDATE(),
    updatedAt DATETIME  DEFAULT GETDATE(),
    FOREIGN KEY (roleId) REFERENCES Role(roleId)
);

-- Create OrderStatus Table
CREATE TABLE OrderStatus (
    statusId INT IDENTITY(1,1) PRIMARY KEY,
    statusName NVARCHAR(50) 
);

-- Create Order Table
CREATE TABLE [Order] (
    orderId INT IDENTITY(1,1) PRIMARY KEY,
    userId INT ,
    orderDate DATETIME  DEFAULT GETDATE(),
    total DECIMAL(18, 2) ,
    orderAddress NVARCHAR(MAX) ,
    statusId INT ,
    createdAt DATETIME  DEFAULT GETDATE(),
    updatedAt DATETIME  DEFAULT GETDATE(),
    FOREIGN KEY (userId) REFERENCES [User](userId),
    FOREIGN KEY (statusId) REFERENCES OrderStatus(statusId)
);

-- Create OrderDetail Table
CREATE TABLE OrderDetail (
    orderDetailId INT IDENTITY(1,1) PRIMARY KEY,
    orderId INT ,
    productId INT ,
    quantity INT ,
    price DECIMAL(18, 2) ,
    discount DECIMAL(18, 2),
    createdAt DATETIME  DEFAULT GETDATE(),
    updatedAt DATETIME  DEFAULT GETDATE(),
    FOREIGN KEY (orderId) REFERENCES [Order](orderId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

-- Create Review Table
CREATE TABLE Review (
    reviewId INT IDENTITY(1,1) PRIMARY KEY,
    userId INT ,
    productId INT ,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment NVARCHAR(MAX),
    reviewDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (userId) REFERENCES [User](userId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

-- Insert mock data into Category
INSERT INTO Category (categoryName) VALUES
(N'Đồ ăn nhanh'),
(N'Món Việt'),
(N'Món Nhật'),
(N'Món Hàn'),
(N'Tráng miệng');

-- Insert mock data into Product
INSERT INTO Product (productName, quantity, description, price, discount, image, categoryId) VALUES
(N'Bánh mì kẹp thịt', 100, N'Bánh mì kẹp thịt và rau', 2.00, 0.00, 'banhmikepthit.jpg', 1),
(N'Phở bò', 150, N'Phở bò Hà Nội', 3.00, 0.00, 'phobo.jpg', 2),
(N'Sushi', 200, N'Sushi cá hồi', 5.00, 0.00, 'sushi.jpg', 3),
(N'Kimchi', 50, N'Kimchi cải thảo', 1.50, 0.00, 'kimchi.jpg', 4),
(N'Chè thái', 75, N'Chè thái sầu riêng', 2.00, 0.00, 'chethai.jpg', 5);

-- Insert mock data into Role
INSERT INTO Role (roleName) VALUES ('Quản trị viên'), ('Khách hàng');

-- Insert mock data into User
INSERT INTO [User] (email, username, password, phone,roleId, dob) VALUES
('quantri@example.com', 'quantri', 'matkhauquantri', '0123456789', 1, '1980-01-01'),
('khachhang1@example.com', 'khachhang1', 'matkhau1', '0987654321', 2, '1990-02-02'),
('khachhang2@example.com', 'khachhang2', 'matkhau2', '0912345678', 2, '1995-03-03');

-- Insert mock data into OrderStatus
INSERT INTO OrderStatus (statusName) VALUES (N'Đang chờ'), (N'Đã xác nhận'), (N'Hoàn thành'), (N'Đã hủy');

-- Insert mock data into [Order]
INSERT INTO [Order] (userId, orderDate, total, orderAddress, statusId) VALUES
(2, GETDATE(), 10.00, N'456 Đường Khách Hàng', 1),
(3, GETDATE(), 15.00, N'789 Đường Khách Hàng', 2);

-- Insert mock data into OrderDetail
INSERT INTO OrderDetail (orderId, productId, quantity, price, discount) VALUES
(1, 1, 2, 2.00, 0.00),
(1, 2, 1, 3.00, 0.00),
(2, 3, 4, 5.00, 0.00),
(2, 4, 3, 1.50, 0.00);

-- Insert mock data into Review
INSERT INTO Review (userId, productId, rating, comment) VALUES
(2, 1, 5, N'Bánh mì rất ngon!'),
(2, 2, 4, N'Phở bò tuyệt vời'),
(3, 3, 3, N'Sushi bình thường'),
(3, 4, 4, N'Kimchi ngon'),
(3, 5, 5, N'Chè thái rất ngon');

