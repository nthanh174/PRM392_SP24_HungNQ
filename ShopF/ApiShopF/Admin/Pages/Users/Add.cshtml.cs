using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace Admin.Pages.Users
{
    public class AddModel : PageModel
    {
        private readonly string ApiUrl = "http://localhost:5000/api/Admin";

        [BindProperty]
        public User User { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            User.RoleId = 2;
            User.CreatedAt = DateTime.Now;
            User.UpdatedAt = DateTime.Now;

            using (var client = new HttpClient())
            {
                var response = await client.PostAsJsonAsync($"{ApiUrl}/Users", User);

                if (response.IsSuccessStatusCode)
                {
                    return RedirectToPage("/Users/List");
                }
                else
                {
                    ModelState.AddModelError(string.Empty, "Failed to create the user. Please try again later.");
                    return Page();
                }
            }
        }
    }
}
