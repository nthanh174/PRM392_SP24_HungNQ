CREATE DATABASE ShopF;
USE ShopF;

-- Create Category Table
CREATE TABLE Category (
    categoryId INT IDENTITY(1,1) PRIMARY KEY,
    categoryName NVARCHAR(255) NOT NULL,
    image NVARCHAR(MAX) NOT NULL
);

-- Create Product Table
CREATE TABLE Product (
    productId INT IDENTITY(1,1) PRIMARY KEY,
    productName NVARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    description NVARCHAR(MAX) NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    discount DECIMAL(18, 2) NOT NULL,
    image NVARCHAR(MAX) NOT NULL,
    categoryIds INT NOT NULL,
    sellerIds INT NOT NULL,
    sold INT NOT NULL,
    FOREIGN KEY (categoryIds) REFERENCES Category(categoryId)
);

-- Create User Table
CREATE TABLE [User] (
    userId INT IDENTITY(1,1) PRIMARY KEY,
    email NVARCHAR(255) NOT NULL,
    username NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    phone NVARCHAR(50) NOT NULL,
    address NVARCHAR(MAX) NOT NULL,
    role INT NOT NULL,
    dob DATE NOT NULL,
);

-- Create Supplier Table
CREATE TABLE Supplier (
    supplierId INT IDENTITY(1,1) PRIMARY KEY,
    supplierName NVARCHAR(255) NOT NULL,
    contactName NVARCHAR(255),
    contactPhone NVARCHAR(50),
    contactEmail NVARCHAR(255),
    address NVARCHAR(MAX)
);

-- Create ProductSupplier Table
CREATE TABLE ProductSupplier (
    productId INT,
    supplierId INT,
    PRIMARY KEY (productId, supplierId),
    FOREIGN KEY (productId) REFERENCES Product(productId),
    FOREIGN KEY (supplierId) REFERENCES Supplier(supplierId)
);

-- Create Order Table
CREATE TABLE [Order] (
    orderId INT IDENTITY(1,1) PRIMARY KEY,
    productId INT NOT NULL,
    userId INT NOT NULL,
    orderDate DATETIME NOT NULL,
    total DECIMAL(18, 2) NOT NULL,
    orderAddress NVARCHAR(MAX) NOT NULL,
    status INT NOT NULL,
    FOREIGN KEY (productId) REFERENCES Product(productId),
    FOREIGN KEY (userId) REFERENCES [User](userId)
);

-- Create OrderDetail Table
CREATE TABLE OrderDetail (
    orderDetailId INT IDENTITY(1,1) PRIMARY KEY,
    orderId INT,
    productId INT,
    quantity INT NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    discount DECIMAL(18, 2),
    FOREIGN KEY (orderId) REFERENCES [Order](orderId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

-- Create Review Table
CREATE TABLE Review (
    reviewId INT IDENTITY(1,1) PRIMARY KEY,
    userId INT,
    productId INT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment NVARCHAR(MAX),
    reviewDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (userId) REFERENCES [User](userId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);
