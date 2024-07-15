using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace Admin.Pages.Products
{
    public class EditModel : PageModel
    {
        private readonly string ApiUrl = "http://localhost:5000/api/Admin";

        public List<Category> Categories { get; set; }

        [BindProperty]
        public Product NewProduct { get; set; }

        [BindProperty]
        public IFormFile? Photo { get; set; }

        public async Task<IActionResult> OnGetAsync(int id)
        {
            // Fetch categories for dropdown
            await FetchCategories();

            // Fetch product details to populate the form
            using (HttpClient client = new HttpClient())
            {
                HttpResponseMessage response = await client.GetAsync($"{ApiUrl}/Products/{id}");

                if (response.IsSuccessStatusCode)
                {
                    string data = await response.Content.ReadAsStringAsync();
                    NewProduct = JsonConvert.DeserializeObject<Product>(data);
                }
                else
                {
                    return RedirectToPage("/Error");
                }
            }

            return Page();
        }

        public async Task<IActionResult> OnPostAsync()
        {
            // Fetch categories for dropdown
            await FetchCategories();

            // Validate model state
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

            // Handle photo upload if exists
            if (Photo != null && Photo.Length > 0)
            {
                var fileName = Path.GetFileName(Photo.FileName);
                var directoryPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "images", "products");

                // Check if directory exists, if not, create it
                if (!Directory.Exists(directoryPath))
                {
                    Directory.CreateDirectory(directoryPath);
                }

                var filePath = Path.Combine(directoryPath, fileName);

                // Save file to the specified path
                using (var stream = new FileStream(filePath, FileMode.Create))
                {
                    await Photo.CopyToAsync(stream);
                }

                NewProduct.Image = "/images/products/" + fileName;
            }

            // Update product via API
            using (HttpClient client = new HttpClient())
            {
                HttpResponseMessage response = await client.PutAsJsonAsync($"{ApiUrl}/Products/", NewProduct);

                if (response.IsSuccessStatusCode)
                {
                    return RedirectToPage("/Products/List");
                }
                else
                {
                    ModelState.AddModelError(string.Empty, "Failed to update the product. Please try again later.");
                    return Page();
                }
            }
        }



        private async Task FetchCategories()
        {
            using (HttpClient client = new HttpClient())
            {
                HttpResponseMessage res = await client.GetAsync($"{ApiUrl}/Categories");

                if (res.IsSuccessStatusCode)
                {
                    string data = await res.Content.ReadAsStringAsync();
                    Categories = JsonConvert.DeserializeObject<List<Category>>(data);
                }
                else
                {
                    Categories = new List<Category>();
                }
            }
        }
    }
}
