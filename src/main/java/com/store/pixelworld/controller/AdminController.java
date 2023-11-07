package com.store.pixelworld.controller;

import com.store.pixelworld.dto.ProductDTO;
import com.store.pixelworld.model.Category;
import com.store.pixelworld.model.Product;
import com.store.pixelworld.service.CategoryService;
import com.store.pixelworld.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir")+ "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
//...............................Category Section .............................................

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }
    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category",new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryService.removedCategoryById(id);
         return "redirect:/admin/categories";

    }
    @GetMapping("/admin/categories/update/{id}")
        public String updateCat(@PathVariable int id , Model model){
        Optional <Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "categoriesAdd";
        }else
            return "404";

    }

    //.................Product Section...............................

    @GetMapping("/admin/products")
    public String product(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "products";

    }

    @GetMapping("/admin/products/add")
    public String ProductAddGet(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "productsAdd";

    }
    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("ProductDTO")ProductDTO productDTO ,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndpath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndpath, file.getBytes());
        }else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";

    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
       productService. removeProductById(id);
        return "redirect:/admin/products";

    }
    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id, Model model){
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId((product.getCategory().getId()));
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight((product.getWeight()));
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());


       model.addAttribute("categories",categoryService.getAllCategory());
       model.addAttribute("productDTO", productDTO);
        return "productsAdd";

    }


}
