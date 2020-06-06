using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Metadata;
using System.Threading.Tasks;

namespace ArchitechtureGuideBackend.Models
{
    public class RouteInfo
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public int Duration { get; set; }
        public string Description { get; set; }
        public double Kilometres { get; set; }
        public byte[] Image { get; set; }
        public ICollection<PlaceInfo> Places { get; set; }
    }
}
