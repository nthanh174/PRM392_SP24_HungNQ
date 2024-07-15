namespace WebAPI_Admin.DTO
{
    public class RegisterDTO
    {
        public string? Email { get; set; }
        public string? Username { get; set; }
        public string? Password { get; set; }
        public string? Phone { get; set; }
        public DateTime? Dob { get; set; }
        public int? RoleId { get; set; }
    }
}
