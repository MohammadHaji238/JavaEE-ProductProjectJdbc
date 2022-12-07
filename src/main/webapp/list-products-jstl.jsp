<%@ page import="ir.digixo.entity.Product" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <title>product Tracker App</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>



<body>

<div id="wrapper">
    <div id="header">
        <h2>Product List</h2>
    </div>
</div>

<div id="container">

    <div id="content">

        <input type="button" value="Add Product" onclick="window.location.href='add-student-form.jsp'; return false;"
    class="add-student-button">

        <table>


            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Action</th>
            </tr>

            <c:forEach var="tempProduct" items="${products_list}">

               <%-- set up a link for each product--%>
                <c:url var="tempLink" value="productController">
                    <c:param name="command" value="LOAD"/>
                    <c:param name="productId" value="${tempProduct.id}" />
                </c:url>

                <c:url var="tempLinkDelete" value="productController">
                    <c:param name="command" value="DELETE"/>
                    <c:param name="productId" value="${tempProduct.id}" />
                </c:url>

                <tr>
                    <td> ${tempProduct.name} </td>
                    <td> ${tempProduct.description} </td>
                    <td> ${tempProduct.price} </td>
                    <td>
                        <a href="${tempLink}">Update</a>
                    |
                        <a href="${tempLinkDelete}" onclick="if (!(confirm('Are you sure you want to delete?'))) return false;">Delete</a>
                    </td>
                </tr>

            </c:forEach>

        </table>

    </div>

</div>
</body>


</html>








