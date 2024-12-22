using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using System.Data.SqlClient;

namespace PharmaWeb.WebPages.Admin
{
    public partial class AdminOrdersDetails : System.Web.UI.Page
    {
        readyclass obj = new readyclass();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                lblID.Text = Request.QueryString["ID"].ToString();

                fillDetails();
            }
        }

        private void fillDetails()
        {
            string sql = "select * from tblOrders where ID='" + lblID.Text.Trim() + "'";
            SqlDataReader dr = Database.getDataReader(sql);
            if (dr.Read())
            {
                lblID.Text = dr[0].ToString().Trim();
                lblOrderDate.Text= dr[2].ToString().Trim();
                lblUMobile.Text = dr[3].ToString().Trim();
                lblDMobile.Text = dr[4].ToString().Trim();
                lblStatus.Text = dr[5].ToString().Trim();
            }
            dr.Close();


            sql = "select * from tblUser where Mobile='" + lblUMobile.Text.Trim() + "'";
            dr = Database.getDataReader(sql);
            if (dr.Read())
            {
                lblUUserName.Text = dr[1].ToString().Trim();
                lblUAddressLine1.Text = dr[2].ToString().Trim();
                lblUAddressLine2.Text = dr[3].ToString().Trim();
                lblUCity.Text = dr[4].ToString().Trim();
                lblUMobile.Text = dr[5].ToString().Trim();
                lblUEmailID.Text = dr[6].ToString().Trim();
            }
            dr.Close();



            sql = "select * from tblMedicalStore where Mobile='" + lblDMobile.Text.Trim() + "'";
            dr = Database.getDataReader(sql);
            if (dr.Read())
            {
                lblMedicalStore.Text = dr[1].ToString().Trim();
                lblDAddressLine1.Text = dr[2].ToString().Trim();
                lblDAddressLine2.Text = dr[3].ToString().Trim();
                lblDCity.Text = dr[4].ToString().Trim();
                lblDMobile.Text = dr[5].ToString().Trim();
                lblDEmailID.Text = dr[6].ToString().Trim();
            }
            dr.Close();
        }

        protected void btnDelete_Click(object sender, EventArgs e)
        {
            if (lblID.Text.Trim() == "")
                readyclass.errormessage(lblerror, "Select Order to delete");
            else
            {
                string sql = "delete from tblOrders where ID = '" + lblID.Text.Trim() + "'";
                Database.executeQuery(sql);

                obj.Show("Deleted Successfully", "AdminOrdersList.aspx");
            }
        }

        protected void btnBack_Click(object sender, EventArgs e)
        {
            Response.Redirect("AdminOrdersList.aspx");
        }

    }
}