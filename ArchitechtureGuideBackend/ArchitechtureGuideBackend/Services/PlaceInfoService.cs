using ArchitechtureGuideBackend.Data;
using ArchitechtureGuideBackend.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ArchitechtureGuideBackend.Services
{
    public class PlaceInfoService : IPlaceInfoService
    {
        private readonly ArchitechtureGuideDbContext _dbContext;

        public PlaceInfoService(ArchitechtureGuideDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public IEnumerable<PlaceInfo> GetAllPlaces()
        {
            return _dbContext.Places;
        }

        public PlaceInfo GetPlaceById(int id)
        {
            var route = _dbContext.Places
                .FirstOrDefault(x => x.Id == id);

            return route;
        }

        public void CreateNewPlace(PlaceInfo newPlace)
        {
            _dbContext.Places.Add(newPlace);
            _dbContext.SaveChanges();
        }
    }
}
