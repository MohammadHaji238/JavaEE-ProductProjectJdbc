<!DOCTYPE html>
<html>

<head>
    <title>update Product</title>

    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-product-style.css">
</head>

<body>
<div id="wrapper">
    <div id="header">
        <h2>Products</h2>
    </div>
</div>

<div id="container">
    <h3>Add Product</h3>

    <form action="productController" method="GET">

        <input type="hidden" name="command" value="UPDATE" />
        <input type="hidden" name="productId" value="${product.id}">

        <table>
            <tbody>
            <tr>
                <td><label>Name:</label></td>
                <td><input type="text" name="name" value="${product.name}" /></td>
            </tr>

            <tr>
                <td><label>Description:</label></td>
                <td><input type="text" name="description" value="${product.description}" /></td>
            </tr>

            <tr>
                <td><label>Price:</label></td>
                <td><input type="text" name="price" value="${product.price}" /></td>
            </tr>

            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Update" class="save" /></td>
            </tr>

            </tbody>
        </table>
    </form>

    <div style="clear: both;"></div>

    <p>
        <a href="productController">Back to List</a>
    </p>
</div>
</body>

</html>

