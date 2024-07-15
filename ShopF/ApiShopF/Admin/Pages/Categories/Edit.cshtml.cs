using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace Admin.Pages.Categories
{
    public class EditModel : PageModel
    {
        private readonly string ApiUrl = "http://localhost:5000/api/Admin";

        [BindProperty]
        public Category NewCategory { get; set; }

        public async Task<IActionResult> OnGetAsync(int id)
        {
            using (var client = new HttpClient())
            {
                HttpResponseMessage response = await client.GetAsync($"{ApiUrl}/Categories/{id}");

                if (response.IsSuccessStatusCode)
                {
                    string data = await response.Content.ReadAsStringAsync();
                    NewCategory = JsonConvert.DeserializeObject<Category>(data);
                    return Page();
                }
                else
                {
                    return RedirectToPage("/Categories/List");
                }
            }
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            using (var client = new HttpClient())
            {
                HttpResponseMessage response = await client.PutAsJsonAsync($"{ApiUrl}/Categories/", NewCategory);

                if (response.IsSuccessStatusCode)
                {
                    return RedirectToPage("/Categories/List");
                }
                else
                {
                    ModelState.AddModelError(string.Empty, "Failed to update the category. Please try again later.");
                    return Page();
                }
            }
        }
    }
}
