using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace Admin.Pages.Users
{
    public class EditModel : PageModel
    {
        private readonly string ApiUrl = "http://localhost:5000/api/Admin";

        [BindProperty]
        public User User { get; set; }

        public async Task<IActionResult> OnGetAsync(int id)
        {
            using (HttpClient client = new HttpClient())
            {
                HttpResponseMessage res = await client.GetAsync($"{ApiUrl}/Users/{id}");

                if (res.IsSuccessStatusCode)
                {
                    string data = await res.Content.ReadAsStringAsync();
                    User = JsonConvert.DeserializeObject<User>(data);
                    return Page();
                }
                else
                {
                    // Log the error status and message
                    Console.WriteLine($"Error: {res.StatusCode} - {await res.Content.ReadAsStringAsync()}");
                    return RedirectToPage("/Users/List");
                }
            }
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            User.UpdatedAt = DateTime.Now;

            Console.WriteLine(User.ToString());
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    HttpResponseMessage response = await client.PutAsJsonAsync($"{ApiUrl}/Users/", User);

                    if (response.IsSuccessStatusCode)
                    {
                        return RedirectToPage("/Users/List");
                    }
                    else
                    {
                        // Log the error status and message
                        var errorMessage = await response.Content.ReadAsStringAsync();
                        Console.WriteLine($"Error: {response.StatusCode} - {errorMessage}");
                        ModelState.AddModelError(string.Empty, "Failed to update the user. Please try again later.");
                        return Page();
                    }
                }
            }
            catch (Exception ex)
            {
                // Log the exception message
                Console.WriteLine($"Exception: {ex.Message}");
                ModelState.AddModelError(string.Empty, $"An error occurred: {ex.Message}");
                return Page();
            }
        }
    }
}
