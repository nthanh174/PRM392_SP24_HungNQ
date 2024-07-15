using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace Admin.Pages.Orders
{
    public class EditModel : PageModel
    {
        private readonly string ApiUrl = "http://localhost:5000/api/Admin";
        [BindProperty]
        public Order? Orders { get; set; }

        public List<OrderStatus>? OrderStatuses { get; set; }
        public async Task<IActionResult> OnGetAsync(int id)
        {
            using (var client = new HttpClient())
            {
                // Get order detail
                using (HttpResponseMessage res = await client.GetAsync($"{ApiUrl}/Orders/{id}"))
                {
                    string data = await res.Content.ReadAsStringAsync();
                    if (res.IsSuccessStatusCode)
                    {
                        Orders = JsonConvert.DeserializeObject<Order>(data);
                    }
                }

                // Get order statuses
                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/OrderStatus"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            OrderStatuses = JsonConvert.DeserializeObject<List<OrderStatus>>(data);
                        }
                    }

                }
            }

            return Page();
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            using (HttpClient client = new HttpClient())
            {
                using (HttpResponseMessage res = await client.PutAsJsonAsync($"{ApiUrl}/Orders/", Orders))
                {
                    if (!res.IsSuccessStatusCode)
                    {
                        return StatusCode((int)res.StatusCode);
                    }
                }
            }

            return RedirectToPage("/Orders/List");
        }
    }
}
