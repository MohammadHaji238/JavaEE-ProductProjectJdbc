package ir.digixo.controller;

import ir.digixo.dao.ProductDbUtil;
import ir.digixo.entity.Product;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet("/productController")
public class ProductController extends HttpServlet {

    protected ProductDbUtil productDao;
    @Resource(name = "jdbc/product")
    protected DataSource dataSource;

    @Override
    public void init() throws ServletException {
        productDao =new ProductDbUtil(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //todo: read command paramatere
            String command = request.getParameter("command");
            //todo: if the command is missing.then default to listing products
            if (command==null)
                command="LIST";
            //todo: route to the appropriate  method
            switch (command){
                case "LIST":
                    listProducts(request,response);
                    break;
                case "ADD":
                    addProduct(request,response);
                    break;
                case "LOAD":
                    loadProduct(request,response);
                    break;
                case "UPDATE":
                    updateProduct(request,response);
                    break;
                case "DELETE":
                    deleteProduct(request,response);
                    break;
                default:
                    listProducts(request,response);
            }
            listProducts(request,response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String productId = request.getParameter("productId");
        productDao.delete(Long.valueOf(productId));
        listProducts(request,response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String productId = request.getParameter("productId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        Product product=new Product(name,description,new BigDecimal(price));
        product.setId(Long.valueOf(productId));
        productDao.update(product);

        listProducts(request,response);



    }

    private void loadProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");

        Optional<Product> product = productDao.findById(Long.valueOf(productId));
        product.ifPresent(product1 -> request.setAttribute("product",product1));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/update-student-form.jsp");
        requestDispatcher.forward(request,response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String description=request.getParameter("description");
        String price=request.getParameter("price");

        Product product=new Product(name,description,new BigDecimal(price));

        productDao.createProduct(product);

        listProducts(request,response);
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Product> products = productDao.getProducts();
        request.setAttribute("products_list",products);
        System.out.println(products.size());
        request.getRequestDispatcher("/list-products-jstl.jsp").forward(request,response);
    }
}
