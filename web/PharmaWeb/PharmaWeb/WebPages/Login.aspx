<%@ Page Title="" Language="C#" MasterPageFile="~/WebPages/User.Master" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="PharmaWeb.WebPages.Login" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


 <h1>Login with valid Credentials</h1>

<table class="minitable"> 
                    	  <tr>
		        <td width="35%">
			        User ID
            	</td>
		
                 <td>
                  <asp:TextBox ID="txtUserID" runat="server" MaxLength="100"></asp:TextBox>
                  <br />
                     <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="Enter User Id" ControlToValidate="txtUserID" Display="Dynamic" ValidationGroup="test" CssClass="error"></asp:RequiredFieldValidator>
                 </td>
          </tr>
        
           <tr>
		        <td>
		        	Password
              	</td>
		
                 <td>
                      <asp:TextBox ID="txtPassword" runat="server" MaxLength="12" TextMode="Password"></asp:TextBox>
                      <br />
                      <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ErrorMessage="Enter Password" ControlToValidate="txtPassword" Display="Dynamic" ValidationGroup="test" CssClass="error"></asp:RequiredFieldValidator>
                       
                 </td>
            </tr>

          

                <tr>
                <td colspan="2" style="text-align:center;">
                <asp:Button ID="btnLogin" runat="server" Text="Login"  
                        ValidationGroup="test" onclick="btnLogin_Click"/>
                        <br />
                         <asp:Label ID="lblerror" runat="server" Text="---" Visible="false" CssClass="error"></asp:Label>
                </td>
                
                </tr>

             </table>



</asp:Content>
