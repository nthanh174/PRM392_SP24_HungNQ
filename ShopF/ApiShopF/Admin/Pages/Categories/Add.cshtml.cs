using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Admin.Pages.Categories
{
    public class AddModel : PageModel
    {
        private readonly string ApiUrl = "http://localhost:5000/api/Admin";

        [BindProperty]
        public Category NewCategory { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            using (var client = new HttpClient())
            {
                HttpResponseMessage response = await client.PostAsJsonAsync($"{ApiUrl}/Categories", NewCategory);

                if (response.IsSuccessStatusCode)
                {
                    return RedirectToPage("/Categories/List");
                }
                else
                {
                    ModelState.AddModelError(string.Empty, "Failed to add the category. Please try again later.");
                    return Page();
                }
            }
        }
    }
}
