﻿// <auto-generated />
using System;
using ArchitechtureGuideBackend.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace ArchitechtureGuideBackend.Migrations
{
    [DbContext(typeof(ArchitechtureGuideDbContext))]
    [Migration("20200606140551_InitialMigration")]
    partial class InitialMigration
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("ArchitechtureGuideBackend.Models.PlaceInfo", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Description");

                    b.Property<byte[]>("Image");

                    b.Property<int?>("RouteInfoId");

                    b.Property<string>("Title");

                    b.HasKey("Id");

                    b.HasIndex("RouteInfoId");

                    b.ToTable("Places");
                });

            modelBuilder.Entity("ArchitechtureGuideBackend.Models.RouteInfo", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

                    b.Property<string>("Description");

                    b.Property<int>("Duration");

                    b.Property<byte[]>("Image");

                    b.Property<double>("Kilometres");

                    b.Property<string>("Title");

                    b.HasKey("Id");

                    b.ToTable("Routes");
                });

            modelBuilder.Entity("ArchitechtureGuideBackend.Models.PlaceInfo", b =>
                {
                    b.HasOne("ArchitechtureGuideBackend.Models.RouteInfo")
                        .WithMany("Places")
                        .HasForeignKey("RouteInfoId");
                });
#pragma warning restore 612, 618
        }
    }
}