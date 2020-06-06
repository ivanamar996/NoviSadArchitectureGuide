using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ArchitechtureGuideBackend.Models;
using ArchitechtureGuideBackend.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ArchitechtureGuideBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RouteInfoController : ControllerBase
    {
        private readonly IRouteInfoService _routeInfoService;

        public RouteInfoController(IRouteInfoService routeInfoService)
        {
            _routeInfoService = routeInfoService;
        }

        [HttpGet]
        public IActionResult Get()
        {
            return Ok(_routeInfoService.GetAllRoutes());
        }

        [HttpGet("{id}")]
        public IActionResult GetRouteById(int id)
        {
            var route = _routeInfoService.GetRouteById(id);

            if (route == null)
                return NotFound("There is no route in db with that id.");

            return Ok(route);
        }

        [HttpPost]
        public IActionResult Post([FromBody] RouteInfo newRoute)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            _routeInfoService.CreateNewRoute(newRoute);

            return CreatedAtAction("Post", newRoute);
        }
    }
}
