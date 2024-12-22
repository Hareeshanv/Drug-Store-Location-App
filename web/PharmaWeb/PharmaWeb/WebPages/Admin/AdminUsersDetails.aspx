<%@ Page Title="" Language="C#" MasterPageFile="~/WebPages/Admin/Admin.Master" AutoEventWireup="true" CodeBehind="AdminUsersDetails.aspx.cs" Inherits="PharmaWeb.WebPages.Admin.AdminUsersDetails" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">



<h1>User Details</h1>

<asp:Label ID="lblerror" runat="server" Text=""></asp:Label>

<table class="minitable">
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
                    <asp:Label ID="lblUserName" runat="server" Text=""></asp:Label>
                </td>
                </tr>

                <tr>
                <td >
                   Address Line1
                </td>
                <td>
                <asp:Label ID="lblAddressLine1" runat="server" Text=""></asp:Label>
                </td>
            </tr>

               <tr>
                <td >
                   Address Line2
                </td>
                <td>
                  <asp:Label ID="lblAddressLine2" runat="server" Text=""></asp:Label>
                </td>
            </tr>

                <tr>
       <td>
         City
       </td>
       <td>
         <asp:Label ID="lblCity" runat="server" Text=""></asp:Label>    
           </td>
</tr>

           
                <tr>
                <td >
                   Mobile
                </td>
                <td>
                   <asp:Label ID="lblMobile" runat="server" Text=""></asp:Label>        
                           </td>
            </tr>

               <tr>
                <td >
                   EmailID
                </td>
                <td>
                   <asp:Label ID="lblEmailID" runat="server" Text=""></asp:Label>        
                           </td>
            </tr>
           
    
<tr>

<td colspan="2" style="text-align:center;">
    <asp:Button ID="btnDelete" runat="server" Text="Delete" 
        onclick="btnDelete_Click" />
    <asp:Button ID="btnBack" runat="server" Text="Back" onclick="btnBack_Click"/>
</td>


</tr>

</table>

<asp:Label ID="lblID" runat="server" Text=""></asp:Label>

</asp:Content>
