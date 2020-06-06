using System.Collections.Generic;
using ArchitechtureGuideBackend.Models;

namespace ArchitechtureGuideBackend.Services
{
    public interface IPlaceInfoService
    {
        void CreateNewPlace(PlaceInfo newPlace);
        IEnumerable<PlaceInfo> GetAllPlaces();
        PlaceInfo GetPlaceById(int id);
    }
}