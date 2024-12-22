<%@ Page Title="" Language="C#" MasterPageFile="~/WebPages/Admin/Admin.Master" AutoEventWireup="true" CodeBehind="AdminMedicalStoresList.aspx.cs" Inherits="PharmaWeb.WebPages.Admin.AdminMedicalStoresList" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


<h1>Medical Store List</h1>


        <table class="minitable">
     
        <tr>
        <td style="width:35%;">City</td>
        <td>
            <asp:DropDownList ID="ddlCity" runat="server" AutoPostBack="true" 
                onselectedindexchanged="ddlCity_SelectedIndexChanged">
            </asp:DropDownList>
        </td>
        </tr>
        
        </table>

  <asp:Label ID="lblerror" runat="server" Text="" CssClass="error"></asp:Label> 
 
        <asp:GridView ID="grdDBoy" runat="server" 
                     AutoGenerateColumns="False" Caption="MedicalStore List" 
        CssClass="fulltable">
             <Columns>

                  <asp:BoundField DataField="MedicalStore" HeaderText="MedicalStore">
                    <HeaderStyle HorizontalAlign="Center" />
                    <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                </asp:BoundField>
                  <asp:BoundField DataField="City" HeaderText="City">
                    <HeaderStyle HorizontalAlign="Center" />
                    <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                </asp:BoundField>
                 <asp:BoundField DataField="Mobile" HeaderText="Mobile">
                    <HeaderStyle HorizontalAlign="Center" />
                    <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                </asp:BoundField>

               <asp:HyperLinkField DataNavigateUrlFields="ID" DataNavigateUrlFormatString="AdminMedicalStoreDetails.aspx?ID={0}" Text="Info" HeaderText="Info">
                    <HeaderStyle HorizontalAlign="Center" />
                    <ItemStyle HorizontalAlign="Center" VerticalAlign="Middle" />
                </asp:HyperLinkField>
            </Columns>
</asp:GridView>


</asp:Content>
