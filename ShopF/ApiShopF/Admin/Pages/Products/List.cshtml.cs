using ApiShopF.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;

namespace Admin.Pages.Products
{
    public class ListModel : PageModel
    {
        string ApiUrl = "http://localhost:5000/api/Admin";

        public string? TotalUsers;
        public string? TotalOrders;
        public string? TotalRevenue;
        public string? TotalProducts;
        public string? TotalCategories;
        public List<Product>? Products;
        public async Task OnGetAsync()
        {
            using (HttpClient client = new HttpClient())
            {
                using (HttpResponseMessage res = await client.GetAsync(ApiUrl + "/Products"))
                {
                    using (HttpContent content = res.Content)
                    {
                        string data = await res.Content.ReadAsStringAsync();
                        if (res.IsSuccessStatusCode)
                        {
                            Products = JsonConvert.DeserializeObject<List<Product>>(data);
                        }
                    }

                }
            }
        }
    }
}
