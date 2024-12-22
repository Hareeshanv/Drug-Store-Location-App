using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PharmaWeb.WebPages.Admin
{
    public partial class AdminUsersList : System.Web.UI.Page
    {
        readyclass obj = new readyclass();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string sql = "Select distinct(City) from tblCities";
                obj.filllist(ddlCity, sql);
            }

        }

        protected void ddlCity_SelectedIndexChanged(object sender, EventArgs e)
        {
            string sql = "Select * from tblUser where City='" + ddlCity.SelectedValue + "'";
            obj.fill(grdUsers, sql, lblerror);
        }
    }
}