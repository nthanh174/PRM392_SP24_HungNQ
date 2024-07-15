using ApiShopF.DTO;
using ApiShopF.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebAPI_Admin.DTO;

namespace ApiShopF.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly ShopFContext _context;

        public UserController(ShopFContext context)
        {
            _context = context;
        }

        [HttpPost("Login")]
        public IActionResult Login(LoginDTO loginDTO)
        {
            var user = _context.Users.SingleOrDefault(u => u.Username == loginDTO.Username && u.Password == loginDTO.Password);
            if (user == null)
            {
                return Unauthorized(new { message = "Invalid username or password" });
            }

            return Ok(new { message = "Login successful", user });
        }

        [HttpPost("Register")]
        public IActionResult Register(RegisterDTO registerDTO)
        {
            var newUser = new User
            {
                Email = registerDTO.Email,
                Username = registerDTO.Username,
                Password = registerDTO.Password,
                Phone = registerDTO.Phone,
                RoleId = registerDTO.RoleId,
            };
            try
            {
                // Kiểm tra xem user đã tồn tại trong hệ thống chưa (có thể dựa vào Email hoặc Username)
                var existingUser = _context.Users.FirstOrDefault(u => u.Email == newUser.Email || u.Username == newUser.Username);
                if (existingUser != null)
                {
                    return Conflict("User already exists.");
                }

                // Thêm user mới vào cơ sở dữ liệu
                newUser.CreatedAt = DateTime.UtcNow;
                newUser.UpdatedAt = DateTime.UtcNow;

                _context.Users.Add(newUser);
                _context.SaveChanges();

                return Ok("User registered successfully.");
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
        }

        [HttpPost("ChangePassword")]
        public IActionResult ChangePassword(LoginDTO loginDTO)
        {
            // Tìm người dùng dựa trên username và password cũ
            var user = _context.Users.SingleOrDefault(u => u.Username == loginDTO.Username);

            if (user == null)
            {
                return Unauthorized(new { message = "Invalid username or password" });
            }

            // Cập nhật mật khẩu mới
            user.Password = loginDTO.Password;

            _context.SaveChanges();

            return Ok(new { message = "Password changed successfully", user });
        }

        [HttpGet("Products")]
        public IActionResult GetProducts()
        {
            var products = _context.Products.ToList();

            var productDTOs = products.Select(product => new ProductDTO
            {
                ProductId = product.ProductId,
                ProductName = product.ProductName,
                Quantity = product.Quantity,
                Description = product.Description,
                Price = product.Price,
                Discount = product.Discount,
                Image = product.Image,
                CategoryId = product.CategoryId,
                Sold = product.Sold
            }).ToList();

            return Ok(productDTOs);
        }

        [HttpGet("GetProductById/{id}")]
        public IActionResult GetProductById(int id)
        {
            var product = _context.Products.FirstOrDefault(p => p.ProductId == id);

            if (product == null)
            {
                return NotFound();
            }

            var productDTO = new ProductDTO
            {
                ProductId = product.ProductId,
                ProductName = product.ProductName,
                Quantity = product.Quantity,
                Description = product.Description,
                Price = product.Price,
                Discount = product.Discount,
                Image = product.Image,
                CategoryId = product.CategoryId,
                Sold = product.Sold
            };

            return Ok(productDTO);
        }

        [HttpGet("Categories")]
        public IActionResult GetCategories()
        {
            var categories = _context.Categories.ToList();

            var cate = categories.Select(category => new CategoryDTO
            {
                CategoryId = category.CategoryId,
                CategoryName = category.CategoryName
            }).ToList();

            return Ok(cate);
        }

        [HttpGet("GetUserByUserName/{username}")]
        public IActionResult getUserByUserName(string username)
        {
            var user = _context.Users
                               .Where(u => u.Username.ToLower() == username.ToLower())
                               .Select(u => new UserDTO
                               {
                                   UserId = u.UserId,
                                   Email = u.Email,
                                   Username = u.Username,
                                   Password = u.Password,
                                   Phone = u.Phone,
                                   RoleId = u.RoleId,
                                   Dob = u.Dob,
                                   CreatedAt = u.CreatedAt,
                                   UpdatedAt = u.UpdatedAt
                               })
                               .FirstOrDefault();

            if (user == null)
            {
                return NotFound("User not found");
            }

            return Ok(user);
        }

        [HttpGet("GetOrderByUserId/{id}")]
        public IActionResult GetOrderByUserId(int id)
        {
            try
            {
                var orders = _context.Orders
                    .Include(o => o.OrderDetails)
                        .ThenInclude(s => s.Product)
                    .Where(o => o.UserId == id)
                    .ToList();

                if (orders == null)
                {
                    return NotFound();
                }

                // Map to OrderDTO
                var orderDTOs = orders.Select(order => new OrderDTO
                {
                    OrderId = order.OrderId,
                    UserId = order.UserId,
                    OrderDate = order.OrderDate,
                    Total = order.Total,
                    OrderAddress = order.OrderAddress,
                    StatusId = order.StatusId,
                    CreatedAt = order.CreatedAt,
                    UpdatedAt = order.UpdatedAt,
                    OrderDetails = order.OrderDetails.Select(detail => new OrderDetailDTO
                    {
                        OrderDetailId = detail.OrderDetailId,
                        OrderId = detail.OrderId,
                        ProductId = detail.ProductId,
                        Quantity = detail.Quantity,
                        Price = detail.Price,
                        Discount = detail.Discount,
                        Product = new ProductDTO
                        {
                            ProductId = detail.Product.ProductId,
                            ProductName = detail.Product.ProductName,
                            Quantity = detail.Product.Quantity,
                            Description = detail.Product.Description,
                            Price = detail.Product.Price,
                            Discount = detail.Product.Discount,
                            Image = detail.Product.Image,
                            CategoryId = detail.Product.CategoryId,
                            Sold = detail.Product.Sold
                        }
                    }).ToList()
                }).ToList();

                return Ok(orderDTOs);
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
        }

    }
}
