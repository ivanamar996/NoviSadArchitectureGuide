using Microsoft.EntityFrameworkCore.Migrations;

namespace ArchitechtureGuideBackend.Migrations
{
    public partial class AddedRouteInfoId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Places_Routes_RouteInfoId",
                table: "Places");

            migrationBuilder.AlterColumn<int>(
                name: "RouteInfoId",
                table: "Places",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Places_Routes_RouteInfoId",
                table: "Places",
                column: "RouteInfoId",
                principalTable: "Routes",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Places_Routes_RouteInfoId",
                table: "Places");

            migrationBuilder.AlterColumn<int>(
                name: "RouteInfoId",
                table: "Places",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddForeignKey(
                name: "FK_Places_Routes_RouteInfoId",
                table: "Places",
                column: "RouteInfoId",
                principalTable: "Routes",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
