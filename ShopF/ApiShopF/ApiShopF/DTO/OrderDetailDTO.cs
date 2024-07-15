using ApiShopF.Models;

namespace ApiShopF.DTO
{
    public class OrderDetailDTO
    {
        public int OrderDetailId { get; set; }
        public int? OrderId { get; set; }
        public int? ProductId { get; set; }
        public int? Quantity { get; set; }
        public decimal? Price { get; set; }
        public decimal? Discount { get; set; }
        public virtual ProductDTO? Product { get; set; }

    }
}
