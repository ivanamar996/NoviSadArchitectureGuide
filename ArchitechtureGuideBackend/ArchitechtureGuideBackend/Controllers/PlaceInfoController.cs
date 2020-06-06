using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using ArchitechtureGuideBackend.Models;
using ArchitechtureGuideBackend.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using static System.Net.Mime.MediaTypeNames;

namespace ArchitechtureGuideBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PlaceInfoController : ControllerBase
    {
        private readonly IPlaceInfoService _placeInfoService;

        public PlaceInfoController(IPlaceInfoService placeInfoService)
        {
            _placeInfoService = placeInfoService;
        }

        [HttpGet]
        public IActionResult Get()
        {
            byte[] bytes = System.IO.File.ReadAllBytes(@"C:\Users\Ivana\Desktop\gitAndroid\NoviSadArchitectureGuide\ArchitectureNS\app\src\main\res\drawable\zmajjovina.jpg");
            _placeInfoService.CreateNewPlace(new PlaceInfo() { Title = "Zmaj Jovina ulica", Description = "Navedena ulica predstavlja setaliste i nema saobrcaja u njoj. Ljudi rado setaju tom ulicom.", Image = bytes,RouteInfoId=2 });
            return Ok(_placeInfoService.GetAllPlaces());
        }

        [HttpGet("{id}")]
        public IActionResult GetPlaceById(int id)
        {
            var place = _placeInfoService.GetPlaceById(id);

            if (place == null)
                return NotFound("There is no place in db with that id.");

            return Ok(place);
        }

        [HttpPost]
        public IActionResult Post([FromBody] PlaceInfo newPlace)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            _placeInfoService.CreateNewPlace(newPlace);

            return CreatedAtAction("Post", newPlace);
        }
    }
}
