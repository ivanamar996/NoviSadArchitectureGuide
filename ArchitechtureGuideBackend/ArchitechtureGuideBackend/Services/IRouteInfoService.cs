using System.Collections.Generic;
using ArchitechtureGuideBackend.Models;

namespace ArchitechtureGuideBackend.Services
{
    public interface IRouteInfoService
    {
        void CreateNewRoute(RouteInfo newRoute);
        IEnumerable<RouteInfo> GetAllRoutes();
        RouteInfo GetRouteById(int id);
    }
}