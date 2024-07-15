using System;
using System.Collections.Generic;

namespace ApiShopF.Models
{
    public partial class OrderStatus
    {
        public OrderStatus()
        {
            Orders = new HashSet<Order>();
        }

        public int StatusId { get; set; }
        public string? StatusName { get; set; }

        public virtual ICollection<Order> Orders { get; set; }
    }
}
