using ApiShopF.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace ApiShopF.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AdminController : ControllerBase
    {
        private readonly ShopFContext _context;

        public AdminController(ShopFContext context)
        {
            _context = context;
        }

        // GET: api/Dashboards/TotalUsers
        [HttpGet("TotalUsers")]
        public IActionResult GetTotalUsers()
        {
            var totalUsers = _context.Users.Count();
            return Ok(totalUsers);
        }

        // GET: api/Dashboards/TotalOrders
        [HttpGet("TotalOrders")]
        public IActionResult GetTotalOrders()
        {
            var totalOrders = _context.Orders.Count();
            return Ok(totalOrders);
        }

        // GET: api/Dashboards/TotalRevenue
        [HttpGet("TotalRevenue")]
        public IActionResult GetTotalRevenue()
        {
            var totalRevenue = _context.Orders.Sum(o => o.Total);
            return Ok(totalRevenue);
        }

        // GET: api/Dashboards/TotalProducts
        [HttpGet("TotalProducts")]
        public IActionResult GetTotalProducts()
        {
            var totalProducts = _context.Products.Count();
            return Ok(totalProducts);
        }

        // GET: api/Dashboards/TotalCategories
        [HttpGet("TotalCategories")]
        public IActionResult GetTotalCategories()
        {
            var totalCategories = _context.Categories.Count();
            return Ok(totalCategories);
        }

        // GET: api/Dashboards/OrdersByStatus
        [HttpGet("OrdersByStatus")]
        public IActionResult GetOrdersByStatus()
        {
            var ordersByStatus = _context.Orders
                .GroupBy(o => o.StatusId)
                .Select(g => new
                {
                    StatusId = g.Key,
                    Count = g.Count()
                })
                .ToList();

            return Ok(ordersByStatus);
        }

        // GET: api/Dashboards/RevenueByMonth
        /*[HttpGet("RevenueByMonth")]
        public IActionResult GetRevenueByMonth()
        {
            var revenueByMonth = _context.Orders
                .GroupBy(o => new { o.OrderDate.Year, o.OrderDate.Month })
                .Select(g => new
                {
                    Year = g.Key.Year,
                    Month = g.Key.Month,
                    TotalRevenue = g.Sum(o => o.Total)
                })
                .ToList();

            return Ok(revenueByMonth);
        }*/

        // GET: api/Dashboards/TopSellingProducts
        [HttpGet("TopSellingProducts")]
        public IActionResult GetTopSellingProducts()
        {
            var topSellingProducts = _context.Products
                .OrderByDescending(p => p.Sold)
                .Select(p => new
                {
                    p.ProductName,
                    p.Sold
                })
                .Take(10)
                .ToList();

            return Ok(topSellingProducts);
        }

        // CRUD Operations for Category

        // GET: api/Dashboards/Categories
        [HttpGet("Categories")]
        public IActionResult GetCategories()
        {
            var categories = _context.Categories.ToList();
            return Ok(categories);
        }

        // GET: api/Dashboards/Categories/{id}
        [HttpGet("Categories/{id}")]
        public IActionResult GetCategory(int id)
        {
            var category = _context.Categories.Find(id);
            if (category == null)
            {
                return NotFound();
            }
            return Ok(category);
        }

        // POST: api/Dashboards/Categories
        [HttpPost("Categories")]
        public IActionResult CreateCategory(Category category)
        {
            _context.Categories.Add(category);
            _context.SaveChanges();
            return CreatedAtAction(nameof(GetCategory), new { id = category.CategoryId }, category);
        }

        // PUT: api/Dashboards/Categories
        [HttpPut("Categories")]
        public IActionResult UpdateCategory(Category updatedCategory)
        {
            var category = _context.Categories.Find(updatedCategory.CategoryId);
            if (category == null)
            {
                return NotFound();
            }

            category.CategoryName = updatedCategory.CategoryName;
            category.UpdatedAt = DateTime.Now;

            _context.Categories.Update(category);
            _context.SaveChanges();
            return NoContent();
        }

        // DELETE: api/Dashboards/Categories/{id}
        [HttpDelete("Categories/{id}")]
        public IActionResult DeleteCategory(int id)
        {
            var category = _context.Categories.Include(c => c.Products).FirstOrDefault(c => c.CategoryId == id);
            if (category == null)
            {
                return NotFound();
            }

            // Set all related products' CategoryId to null
            foreach (var product in category.Products)
            {
                product.CategoryId = null; // or any default value you prefer
            }

            _context.Categories.Remove(category);
            _context.SaveChanges();
            return NoContent();
        }


        // CRUD Operations for Product

        // GET: api/Dashboards/Products
        [HttpGet("Products")]
        public IActionResult GetProducts()
        {
            var products = _context.Products.ToList();
            return Ok(products);
        }

        // GET: api/Dashboards/Products/{id}
        [HttpGet("Products/{id}")]
        public IActionResult GetProduct(int id)
        {
            var product = _context.Products.Find(id);
            if (product == null)
            {
                return NotFound();
            }
            return Ok(product);
        }

        // POST: api/Dashboards/Products
        [HttpPost("Products")]
        public IActionResult CreateProduct(Product product)
        {
            _context.Products.Add(product);
            _context.SaveChanges();
            return CreatedAtAction(nameof(GetProduct), new { id = product.ProductId }, product);
        }

        // PUT: api/Dashboards/Products
        [HttpPut("Products")]
        public IActionResult UpdateProduct(Product updatedProduct)
        {
            var product = _context.Products.Find(updatedProduct.ProductId);
            if (product == null)
            {
                return NotFound();
            }

            // Update other properties
            product.ProductName = updatedProduct.ProductName;
            product.Quantity = updatedProduct.Quantity;
            product.Description = updatedProduct.Description;
            product.Price = updatedProduct.Price;
            product.Discount = updatedProduct.Discount;
            product.CategoryId = updatedProduct.CategoryId;
            product.Sold = updatedProduct.Sold;
            product.UpdatedAt = DateTime.Now;

            // Update image only if a new image is uploaded
            if (!string.IsNullOrEmpty(updatedProduct.Image))
            {
                product.Image = updatedProduct.Image;
            }

            _context.Products.Update(product);
            _context.SaveChanges();
            return NoContent();
        }


        // DELETE: api/Dashboards/Products/{id}
        [HttpDelete("Products/{id}")]
        public IActionResult DeleteProduct(int id)
        {
            var product = _context.Products.Find(id);
            if (product == null)
            {
                return NotFound();
            }

            _context.Products.Remove(product);
            _context.SaveChanges();
            return NoContent();
        }

        // CRUD Operations for User

        // GET: api/Dashboards/Users
        [HttpGet("Users")]
        public IActionResult GetUsers()
        {
            var users = _context.Users.ToList();
            return Ok(users);
        }

        // GET: api/Dashboards/Users/{id}
        [HttpGet("Users/{id}")]
        public IActionResult GetUser(int id)
        {
            var user = _context.Users.Find(id);
            if (user == null)
            {
                return NotFound();
            }
            return Ok(user);
        }

        // POST: api/Dashboards/Users
        [HttpPost("Users")]
        public IActionResult CreateUser(User user)
        {
            _context.Users.Add(user);
            _context.SaveChanges();
            return CreatedAtAction(nameof(GetUser), new { id = user.UserId }, user);
        }


        // PUT: api/Dashboards/Users
        [HttpPut("Users")]
        public IActionResult UpdateUser(User updatedUser)
        {
            var user = _context.Users.Find(updatedUser.UserId);
            if (user == null)
            {
                return NotFound();
            }

            user.Email = updatedUser.Email;
            user.Username = updatedUser.Username;
            user.Password = updatedUser.Password;
            user.Phone = updatedUser.Phone;
            user.RoleId = updatedUser.RoleId;
            user.Dob = updatedUser.Dob;
            user.UpdatedAt = DateTime.Now;

            _context.Users.Update(user);
            _context.SaveChanges();
            return NoContent();
        }

        // DELETE: api/Users/5
        [HttpDelete("Users/{id}")]
        public IActionResult DeleteUser(int id)
        {
            var user = _context.Users
                .Include(u => u.Orders)
                    .ThenInclude(o => o.OrderDetails)
                .Include(u => u.Reviews)
                .FirstOrDefault(u => u.UserId == id);

            if (user == null)
            {
                return NotFound();
            }

            // Remove all associated reviews
            _context.Reviews.RemoveRange(user.Reviews);

            // Remove all associated orders and their details
            foreach (var order in user.Orders)
            {
                _context.OrderDetails.RemoveRange(order.OrderDetails);
            }
            _context.Orders.RemoveRange(user.Orders);

            // Remove the user itself
            _context.Users.Remove(user);

            try
            {
                _context.SaveChanges();
            }
            catch (DbUpdateException ex)
            {
                // Handle exception if needed
                return BadRequest(new { error = "Error deleting user." });
            }

            return NoContent();
        }

        // CRUD Operations for Order

        // GET: api/Dashboards/Orders
        [HttpGet("Orders")]
        public IActionResult GetOrders()
        {
            var orders = _context.Orders
                /*.Include(o => o.User)*/
                .ToList();
            return Ok(orders);
        }

        // GET: api/Dashboards/Orders/{id}
        [HttpGet("Orders/{id}")]
        public IActionResult GetOrder(int id)
        {
            var order = _context.Orders
                .Where(o => o.OrderId == id)
                .Select(o => new Order
                {
                    OrderId = o.OrderId,
                    UserId = o.UserId,
                    OrderDate = o.OrderDate,
                    Total = o.Total,
                    OrderAddress = o.OrderAddress,
                    StatusId = o.StatusId,
                    CreatedAt = o.CreatedAt,
                    UpdatedAt = o.UpdatedAt,
                    OrderDetails = o.OrderDetails.Select(od => new OrderDetail
                    {
                        OrderDetailId = od.OrderDetailId,
                        OrderId = od.OrderId,
                        ProductId = od.ProductId,
                        Quantity = od.Quantity,
                        Price = od.Price,
                        Discount = od.Discount,
                        Product = new Product
                        {
                            ProductId = od.Product.ProductId,
                            ProductName = od.Product.ProductName,
                        }
                    }).ToList(),
                    Status = new OrderStatus
                    {
                        StatusId = o.Status.StatusId,
                        StatusName = o.Status.StatusName
                    },
                    User = new User
                    {
                        UserId = o.User.UserId,
                        Username = o.User.Username,
                    }
                })
                .FirstOrDefault();

            return Ok(order);
        }

        // POST: api/Dashboards/Orders
        [HttpPost("Orders")]
        public IActionResult CreateOrder(Order order)
        {
            _context.Orders.Add(order);
            _context.SaveChanges();
            return CreatedAtAction(nameof(GetOrder), new { id = order.OrderId }, order);
        }
        // PUT: api/Dashboards/Orders
        [HttpPut("Orders")]
        public IActionResult UpdateOrder(Order updatedOrder)
        {
            var order = _context.Orders.Find(updatedOrder.OrderId);
            if (order == null)
            {
                return NotFound();
            }

            /*            order.UserId = updatedOrder.UserId;
                        order.OrderDate = updatedOrder.OrderDate;
                        order.Total = updatedOrder.Total;*/
            order.OrderAddress = updatedOrder.OrderAddress;
            order.StatusId = updatedOrder.StatusId;

            _context.Orders.Update(order);
            _context.SaveChanges();
            return NoContent();
        }

        // DELETE: api/Dashboards/Orders/{id}
        [HttpDelete("Orders/{id}")]
        public IActionResult DeleteOrder(int id)
        {
            var order = _context.Orders.Include(o => o.OrderDetails).FirstOrDefault(o => o.OrderId == id);
            if (order == null)
            {
                return NotFound();
            }

            // Remove order details first
            _context.OrderDetails.RemoveRange(order.OrderDetails);
            // Remove the order itself
            _context.Orders.Remove(order);
            _context.SaveChanges();
            return NoContent();
        }


        // GET: api/Dashboards/Orders
        [HttpGet("OrderStatus")]
        public IActionResult GetOrderStatus()
        {
            var orders = _context.OrderStatuses
                .ToList();
            return Ok(orders);
        }


        [HttpPost("login")]
        public IActionResult Login(string username, string pass)
        {
            var user = _context.Users.SingleOrDefault(u => u.Username == username && u.Password == pass);
            if (user == null)
            {
                return Unauthorized(new { message = "Invalid username or password" });
            }

            // In a real application, you would generate a JWT token or similar here
            return Ok(new { message = "Login successful", user });
        }
        [HttpPost("register")]
        public IActionResult Register(User user)
        {
            try
            {
                // Kiểm tra xem user đã tồn tại trong hệ thống chưa (có thể dựa vào Email hoặc Username)
                var existingUser = _context.Users.FirstOrDefault(u => u.Email == user.Email || u.Username == user.Username);
                if (existingUser != null)
                {
                    return Conflict("User already exists.");
                }

                // Thêm user mới vào cơ sở dữ liệu
                user.CreatedAt = DateTime.UtcNow;
                user.UpdatedAt = DateTime.UtcNow;

                _context.Users.Add(user);
                _context.SaveChanges();

                return Ok("User registered successfully.");
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Internal server error: {ex.Message}");
            }
        }
    }
}
