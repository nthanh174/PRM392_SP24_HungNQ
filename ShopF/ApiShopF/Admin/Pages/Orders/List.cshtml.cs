using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace Admin.Pages.Orders
{
    public class ListModel : PageModel
    {
        string ApiUrl = "http://localhost:5000/api/Admin";
        public List<Order>? Orders { get; set; }
        public List<OrderStatus>? OrderStatuses { get; set; }
        public async Task OnGetAsync()
        {
            using (HttpClient client = new HttpClient())
            {
                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/Orders"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            Orders = JsonConvert.DeserializeObject<List<Order>>(data);
                        }
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
        }
    }
}
