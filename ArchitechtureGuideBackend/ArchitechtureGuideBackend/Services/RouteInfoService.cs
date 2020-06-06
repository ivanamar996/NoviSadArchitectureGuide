using ArchitechtureGuideBackend.Data;
using ArchitechtureGuideBackend.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ArchitechtureGuideBackend.Services
{
    public class RouteInfoService : IRouteInfoService
    {
        private readonly ArchitechtureGuideDbContext _dbContext;

        public RouteInfoService(ArchitechtureGuideDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public IEnumerable<RouteInfo> GetAllRoutes()
        {
            return _dbContext.Routes.Include(x => x.Places);
        }

        public RouteInfo GetRouteById(int id)
        {
            var route = _dbContext.Routes
                .Include(x => x.Places)
                .FirstOrDefault(x => x.Id == id);

            return route;
        }

        public void CreateNewRoute(RouteInfo newRoute)
        {

            foreach (var place in newRoute.Places)
            {
                var placeInDb = _dbContext.Places.SingleOrDefault(x => x.Title.Equals(place.Title)) ??
                                 _dbContext.Places.Add(place).Entity;

                _dbContext.Places.Add(place);
            }

            _dbContext.Routes.Add(newRoute);
            _dbContext.SaveChanges();
        }
    }
}
