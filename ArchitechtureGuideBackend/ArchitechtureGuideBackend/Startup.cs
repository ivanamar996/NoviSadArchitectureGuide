using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ArchitechtureGuideBackend.Data;
using ArchitechtureGuideBackend.Services;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.OpenApi.Models;

namespace ArchitechtureGuideBackend
{
    public class Startup
    {
        readonly string MyAllowSpecificOrigins = "_myAllowSpecificOrigins";

        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_2);

            services.AddCors(options =>
            {
                options.AddPolicy(name: MyAllowSpecificOrigins,
                                  builder =>
                                  {
                                      builder.AllowAnyOrigin()
                                             .AllowAnyHeader()
                                             .AllowAnyMethod();
                                  });
            });

            services.AddDbContext<ArchitechtureGuideDbContext>(options => options.UseSqlServer(Configuration.GetConnectionString("ArchitechtureGuideConnection")));

            services.AddTransient<IPlaceInfoService, PlaceInfoService>();
            services.AddTransient<IRouteInfoService, RouteInfoService>();

            services.AddSwaggerGen(swagger =>
            {
                swagger.SwaggerDoc("AppAdministration", new OpenApiInfo { Title = "App Administration API", Version = "v1.0" });

            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger ();
                app.UseSwaggerUI(c =>
                {
                    c.SwaggerEndpoint("/swagger/AppAdministration/swagger.json", "App Administration");
                });
            }
            else
            {
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }
    
            app.UseCors(MyAllowSpecificOrigins);

            app.UseHttpsRedirection();
            app.UseMvc();
        }
    }
}
