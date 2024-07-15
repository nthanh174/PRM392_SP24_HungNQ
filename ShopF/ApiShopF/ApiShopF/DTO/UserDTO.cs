﻿namespace ApiShopF.DTO
{
    public class UserDTO
    {
        public int UserId { get; set; }
        public string? Email { get; set; }
        public string? Username { get; set; }
        public string? Password { get; set; }
        public string? Phone { get; set; }
        public int? RoleId { get; set; }
        public DateTime? Dob { get; set; }
        public DateTime? CreatedAt { get; set; }
        public DateTime? UpdatedAt { get; set; }
    }
}
