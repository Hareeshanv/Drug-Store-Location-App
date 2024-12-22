<%@ Page Title="" Language="C#" MasterPageFile="~/WebPages/Admin/Admin.Master" AutoEventWireup="true" CodeBehind="AdminOrdersDetails.aspx.cs" Inherits="PharmaWeb.WebPages.Admin.AdminOrdersDetails" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<h1>Order Details page</h1>

<asp:Label ID="lblerror" runat="server" Text=""></asp:Label>

<table class="fulltable">

<tr>
<th colspan="4" style="text-align:center;">
Order Details page
</th>
</tr>

<tr>
<td style="width:20%;">
Order ID
</td>
<td style="width:30%;">
    <asp:Label ID="lblOrderId" runat="server" Text="Label"></asp:Label>
</td>

<td style="width:20%;">
Order Date
</td>
<td style="width:30%;">
    <asp:Label ID="lblOrderDate" runat="server" Text="Label"></asp:Label>
</td>
</tr>


<tr>
<td>
Status
</td>
<td>
    <asp:Label ID="lblStatus" runat="server" Text="Label"></asp:Label>
</td>

<td>
Bill Amount
</td>
<td>
    <asp:Label ID="lblBillAmount" runat="server" Text="Label"></asp:Label>
</td>
</tr>



<tr>
<th colspan="4">
User Details
</th>
</tr>


<tr>
                <td>
                   User Name
                </td>
                <td>
                    <asp:Label ID="lblUUserName" runat="server" Text=""></asp:Label>
                </td>
              
                <td >
                   Address Line1
                </td>
                <td>
                <asp:Label ID="lblUAddressLine1" runat="server" Text=""></asp:Label>
                </td>
            </tr>

               <tr>
                <td >
                   Address Line2
                </td>
                <td>
                  <asp:Label ID="lblUAddressLine2" runat="server" Text=""></asp:Label>
                </td>
          
       <td>
         City
       </td>
       <td>
         <asp:Label ID="lblUCity" runat="server" Text=""></asp:Label>    
           </td>
</tr>

           
                <tr>
                <td >
                   Mobile
                </td>
                <td>
                   <asp:Label ID="lblUMobile" runat="server" Text=""></asp:Label>        
                           </td>
           
                <td >
                   EmailID
                </td>
                <td>
                   <asp:Label ID="lblUEmailID" runat="server" Text=""></asp:Label>        
                           </td>
            </tr>




            
<tr>
<th colspan="4">
Medical Store Details
</th>
</tr>


<tr>
                <td>
               Medical Store
                </td>
                <td>
                    <asp:Label ID="lblMedicalStore" runat="server" Text=""></asp:Label>
                </td>
              
                <td >
                   Address Line1
                </td>
                <td>
                <asp:Label ID="lblDAddressLine1" runat="server" Text=""></asp:Label>
                </td>
            </tr>

               <tr>
                <td >
                   Address Line2
                </td>
                <td>
                  <asp:Label ID="lblDAddressLine2" runat="server" Text=""></asp:Label>
                </td>
          
       <td>
         City
       </td>
       <td>
         <asp:Label ID="lblDCity" runat="server" Text=""></asp:Label>    
           </td>
</tr>

           
                <tr>
                <td >
                   Mobile
                </td>
                <td>
                   <asp:Label ID="lblDMobile" runat="server" Text=""></asp:Label>        
                           </td>
           
                <td >
                   EmailID
                </td>
                <td>
                   <asp:Label ID="lblDEmailID" runat="server" Text=""></asp:Label>        
                           </td>
            </tr>

            
<tr>

<td colspan="4" style="text-align:center;">
    <asp:Button ID="btnDelete" runat="server" Text="Delete" 
        onclick="btnDelete_Click" />
    <asp:Button ID="btnBack" runat="server" Text="Back" onclick="btnBack_Click"/>
</td>


</tr>
</table>

<asp:Label ID="lblID" runat="server" Text=""></asp:Label>       
</asp:Content>
