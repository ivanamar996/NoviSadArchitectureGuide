using ArchitechtureGuideBackend.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ArchitechtureGuideBackend.Data
{
    public class ArchitechtureGuideDbContext : DbContext
    {
        public ArchitechtureGuideDbContext(DbContextOptions options) : base(options)
        {
        }

        public DbSet<PlaceInfo> Places { get; set; }
        public DbSet<RouteInfo> Routes { get; set; }
    }
}
