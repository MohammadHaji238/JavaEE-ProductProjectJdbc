<%@ page import="java.util.*, ir.digixo.* "%>
<%@ page import="ir.digixo.entity.Product" %>
<!DOCTYPE html>
<html>

<head>
    <title>product Tracker App</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<%
    // get the products from the request object (sent by servlet)
    List<Product> products =
            (List<Product>) request.getAttribute("products_list");
%>

<body>

<div id="wrapper">
    <div id="header">
        <h2>Product List</h2>
    </div>
</div>

<div id="container">

    <div id="content">

        <table>

            <tr>
                <th> Name</th>
                <th>Description</th>
                <th>Price</th>
            </tr>

            <% for (Product tempProduct : products) { %>

            <tr>
                <td> <%= tempProduct.getName() %> </td>
                <td> <%= tempProduct.getDescription() %> </td>
                <td> <%= tempProduct.getPrice() %> </td>
            </tr>

            <% } %>

        </table>

    </div>

</div>
</body>


</html>








