using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace Admin.Pages.Home
{
    public class IndexModel : PageModel
    {
        string ApiUrl = "http://localhost:5000/api/Admin";

        public string? TotalUsers;
        public string? TotalOrders;
        public string? TotalRevenue;
        public string? TotalProducts;
        public string? TotalCategories;
        public List<Order>? Orders;
        public async Task OnGetAsync()
        {
            using (HttpClient client = new HttpClient())
            {
                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/TotalUsers"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            TotalUsers = data;
                        }
                    }

                }

                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/TotalOrders"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            TotalOrders = data;
                        }
                    }

                }

                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/TotalRevenue"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            TotalRevenue = data;
                        }
                    }

                }

                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/TotalProducts"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            TotalProducts = data;
                        }
                    }

                }

                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/TotalCategories"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            TotalCategories = data;
                        }
                    }

                }

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
            }
        }
    }
}
