using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PharmaWeb.WebPages.Admin
{
    public partial class AdminOrdersList : System.Web.UI.Page
    {
        readyclass obj = new readyclass();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                string sql = "Select * from tblOrders";
                obj.fill(grdOrders, sql, lblerror);
            }
        }
    }
}