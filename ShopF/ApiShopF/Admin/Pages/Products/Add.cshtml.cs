using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace Admin.Pages.Products
{
    public class AddModel : PageModel
    {
        string ApiUrl = "http://localhost:5000/api/Admin";

        public List<Category>? Categories { get; set; }

        [BindProperty]
        public Product NewProduct { get; set; }

        [BindProperty]
        public IFormFile? Photo { get; set; }

        public async Task OnGetAsync()
        {
            using (HttpClient client = new HttpClient())
            {
                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/Categories"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            Categories = JsonConvert.DeserializeObject<List<Category>>(data);
                        }
                    }
                }
            }
        }

        public async Task<IActionResult> OnPostAsync()
        {
            using (HttpClient client = new HttpClient())
            {
                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/Categories"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            Categories = JsonConvert.DeserializeObject<List<Category>>(data);
                        }
                    }
                }
            }

            // Kiểm tra validation errors
            if (!ModelState.IsValid)
            {
                Console.WriteLine("There are validation errors:");

                foreach (var modelStateValue in ModelState.Values)
                {
                    foreach (var error in modelStateValue.Errors)
                    {
                        Console.WriteLine($"Error: {error.ErrorMessage}");
                    }
                }

                return Page();
            }

            // Kiểm tra và lưu ảnh nếu có
            if (Photo != null && Photo.Length > 0)
            {
                // Lấy tên file và đường dẫn lưu trữ
                var fileName = Path.GetFileName(Photo.FileName);
                var directoryPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "images", "products");

                // Kiểm tra và tạo thư mục nếu chưa tồn tại
                if (!Directory.Exists(directoryPath))
                {
                    Directory.CreateDirectory(directoryPath);
                }

                var filePath = Path.Combine(directoryPath, fileName);

                // Lưu file vào đường dẫn
                using (var stream = new FileStream(filePath, FileMode.Create))
                {
                    await Photo.CopyToAsync(stream);
                }

                // Lưu đường dẫn vào model NewProduct
                NewProduct.Image = "/images/products/" + fileName;
            }

            // Gửi NewProduct lên API để lưu vào database
            using (HttpClient client = new HttpClient())
            {
                HttpResponseMessage response = await client.PostAsJsonAsync(ApiUrl + "/Products", NewProduct);

                if (response.IsSuccessStatusCode)
                {
                    return RedirectToPage("/Products/List");
                }
                else
                {
                    ModelState.AddModelError(string.Empty, "Failed to add the product. Please try again later.");
                    return Page();
                }
            }
        }
    }
}
