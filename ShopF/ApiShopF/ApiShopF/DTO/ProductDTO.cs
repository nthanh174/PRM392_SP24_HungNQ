namespace ApiShopF.DTO
{
    public class ProductDTO
    {
        public int ProductId { get; set; }
        public string? ProductName { get; set; }
        public int? Quantity { get; set; }
        public string? Description { get; set; }
        public decimal? Price { get; set; }
        public decimal? Discount { get; set; }
        public string? Image { get; set; }
        public int? CategoryId { get; set; }
        public int? Sold { get; set; }
    }
}
