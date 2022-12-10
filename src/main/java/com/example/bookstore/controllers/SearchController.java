package com.example.bookstore.controllers;

import com.example.bookstore.repositories.ProductRepository;
import com.example.bookstore.services.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    @Value("${upload.path}")
    private String uploadPath;

    private final ProductService productService;
    private final ProductRepository productRepository;

    public SearchController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }


    @GetMapping("/search")
    public String getSearchPage(Model model) {
        model.addAttribute("search_product", productService.getProductList());
        return "product/search";
    }

    @PostMapping("/search")
    public String productSearch(
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "price_from", required = false) String price_from,
            @RequestParam(value = "price_to", required = false) String price_to,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "genre", required = false) String genre, Model model) {

        if (!title.isEmpty()) {
            if (!price_from.isEmpty() && !price_to.isEmpty()) {
                if (!title.isEmpty()) {
                    System.out.println("Фильтр # поля ввода <наименование> и <цена \"от\"> и <цена \"до\"> заполнены");
                    model.addAttribute("search_product", productRepository.filterProductByTitleIgnoreCaseAndByPriceFromAndPriceTo(title, Float.parseFloat(price_from), Float.parseFloat(price_to)));
                    model.addAttribute("value_title", title);
                    model.addAttribute("value_price_from", price_from);
                    model.addAttribute("value_price_to", price_to);
                    return "product/search";
                }
            }
            else if (!title.isEmpty() && !price_from.isEmpty()) {
                System.out.println("Фильтр # поля ввода <наименование> и <цена \"от\"> заполнены");
                model.addAttribute("search_product", productRepository.filterProductByTitleIgnoreCaseAndByPriceFrom(title, Float.parseFloat(price_from)));
                model.addAttribute("value_title", title);
                model.addAttribute("value_price_from", price_from);
                return "product/search";
            }
            else if (!title.isEmpty() && !price_to.isEmpty()) {
                System.out.println("Фильтр # поля ввода <наименование> и <цена \"до\"> заполнены");
                model.addAttribute("search_product", productRepository.filterProductByTitleIgnoreCaseAndByPriceTo(title, Float.parseFloat(price_to)));
                model.addAttribute("value_title", title);
                model.addAttribute("value_price_to", price_to);
                return "product/search";
            }
            System.out.println("Фильтр # поле ввода <наименование> заполнено");
            model.addAttribute("search_product", productRepository.filterByProductTitleIgnoreCase(title));
            model.addAttribute("value_title", title);
            return "product/search";
        }

        if (title.isEmpty()) {
            if (!price_from.isEmpty() && !price_to.isEmpty()) {
                System.out.println("Фильтр # поле ввода <наименование> пустое и поля <цена \"от\"> и <цена \"до\"> заполнены");
                model.addAttribute("search_product", productRepository.filterProductByPriceFromTo(Float.parseFloat(price_from), Float.parseFloat(price_to)));
                model.addAttribute("value_price_from", price_from);
                model.addAttribute("value_price_to", price_to);
                return "product/search";
            }
            else if (!price_from.isEmpty() && price_to.isEmpty()) {
                System.out.println("Фильтр # поля ввода <наименование> и <цена \"до\"> пустые и поле <цена \"от\"> заполнено");
                model.addAttribute("search_product", productRepository.filterProductByPriceFrom(Float.parseFloat(price_from)));
                model.addAttribute("value_price_from", price_from);
                return "product/search";
            }
            else if (!price_to.isEmpty() && price_from.isEmpty()) {
                System.out.println("Фильтр # поля ввода <наименование> и <цена \"от\"> пустые и поле <цена \"до\"> заполнено");
                model.addAttribute("search_product", productRepository.filterProductByPriceTo(Float.parseFloat(price_to)));
                model.addAttribute("value_price_to", price_to);
                return "product/search";
            }
            else if (price_to.isEmpty() && price_from.isEmpty()) {
                if (genre == null){
                    if (price == null){
                        System.out.println("Фильтр # Критерии поиска не заданы");
                        model.addAttribute("search_product", productService.getProductList());
                        return "redirect:/search";
                    }
                    else if(!price.isEmpty()){
                        if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByPriceAsc());
                            return "product/search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByPriceDesc());
                            return "product/search";
                        }
                    }
                }
                if (!genre.isEmpty()){
                    if ("belletristic".equals(genre)) {
                        if (price == null){
                            System.out.println("Фильтр # по жанру \"Художественная литература\"");
                            model.addAttribute("search_product", productRepository.findProductByGenreBelletristic(1));
                            return "product/search";
                        }
                        else if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenreBelletristicPriceAsc(1));
                            return "product/search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenreBelletristicPriceDesc(1));
                            return "product/search";
                        }
                    } else if ("business".equals(genre)) {
                        if (price == null){
                            System.out.println("Фильтр # по жанру \"Деловая литература\"");
                            model.addAttribute("search_product", productRepository.findProductByGenreBusiness(2));
                            return "product/search";
                        }
                        else if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenreBusinessPriceAsc(2));
                            return "product/search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenreBusinessPriceDesc(2));
                            return "product/search";
                        }
                    } else if ("psychology".equals(genre)) {
                        if (price == null){
                            System.out.println("Фильтр # по жанру \"Психология\"");
                            model.addAttribute("search_product", productRepository.findProductByGenrePsychology(3));
                            return "product/search";
                        }
                        else if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenrePsychologyPriceAsc(3));
                            return "product/search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenrePsychologyPriceDesc(3));
                            return "product/search";
                        }
                    } else if ("philosophy_and_religion".equals(genre)) {
                        if (price == null){
                            System.out.println("Фильтр # по жанру \"Философия и религия\"");
                            model.addAttribute("search_product", productRepository.findProductByGenrePhilosophyAndReligion(4));
                            return "product/search";
                        }
                        else if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenrePhilosophyAndReligionPriceAsc(4));
                            return "product/search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenrePhilosophyAndReligionPriceDesc(4));
                            return "product/search";
                        }
                    }
                }
            }
        }

    System.out.println("Фильтр # Критерии поиска не заданы");
    model.addAttribute("search_product", productService.getProductList());
    return "product/search";

    }

    /* =========================== */

    @GetMapping("/search-all")
    public String getSearchPageForAll(Model model) {
        model.addAttribute("search_product", productService.getProductList());
        return "search";
    }

    @PostMapping("/search-all")
    public String productSearchForAll(
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "price_from", required = false) String price_from,
            @RequestParam(value = "price_to", required = false) String price_to,
            @RequestParam(value = "price", required = false) String price,
            @RequestParam(value = "genre", required = false) String genre, Model model) {

        if (!title.isEmpty()) {
            if (!price_from.isEmpty() && !price_to.isEmpty()) {
                if (!title.isEmpty()) {
                    System.out.println("Фильтр # поля ввода <наименование> и <цена \"от\"> и <цена \"до\"> заполнены");
                    model.addAttribute("search_product", productRepository.filterProductByTitleIgnoreCaseAndByPriceFromAndPriceTo(title, Float.parseFloat(price_from), Float.parseFloat(price_to)));
                    model.addAttribute("value_title", title);
                    model.addAttribute("value_price_from", price_from);
                    model.addAttribute("value_price_to", price_to);
                    return "search";
                }
            }
            else if (!title.isEmpty() && !price_from.isEmpty()) {
                System.out.println("Фильтр # поля ввода <наименование> и <цена \"от\"> заполнены");
                model.addAttribute("search_product", productRepository.filterProductByTitleIgnoreCaseAndByPriceFrom(title, Float.parseFloat(price_from)));
                model.addAttribute("value_title", title);
                model.addAttribute("value_price_from", price_from);
                return "search";
            }
            else if (!title.isEmpty() && !price_to.isEmpty()) {
                System.out.println("Фильтр # поля ввода <наименование> и <цена \"до\"> заполнены");
                model.addAttribute("search_product", productRepository.filterProductByTitleIgnoreCaseAndByPriceTo(title, Float.parseFloat(price_to)));
                model.addAttribute("value_title", title);
                model.addAttribute("value_price_to", price_to);
                return "search";
            }
            System.out.println("Фильтр # поле ввода <наименование> заполнено");
            model.addAttribute("search_product", productRepository.filterByProductTitleIgnoreCase(title));
            model.addAttribute("value_title", title);
            return "search";
        }

        if (title.isEmpty()) {
            if (!price_from.isEmpty() && !price_to.isEmpty()) {
                System.out.println("Фильтр # поле ввода <наименование> пустое и поля <цена \"от\"> и <цена \"до\"> заполнены");
                model.addAttribute("search_product", productRepository.filterProductByPriceFromTo(Float.parseFloat(price_from), Float.parseFloat(price_to)));
                model.addAttribute("value_price_from", price_from);
                model.addAttribute("value_price_to", price_to);
                return "search";
            }
            else if (!price_from.isEmpty() && price_to.isEmpty()) {
                System.out.println("Фильтр # поля ввода <наименование> и <цена \"до\"> пустые и поле <цена \"от\"> заполнено");
                model.addAttribute("search_product", productRepository.filterProductByPriceFrom(Float.parseFloat(price_from)));
                model.addAttribute("value_price_from", price_from);
                return "search";
            }
            else if (!price_to.isEmpty() && price_from.isEmpty()) {
                System.out.println("Фильтр # поля ввода <наименование> и <цена \"от\"> пустые и поле <цена \"до\"> заполнено");
                model.addAttribute("search_product", productRepository.filterProductByPriceTo(Float.parseFloat(price_to)));
                model.addAttribute("value_price_to", price_to);
                return "search";
            }
            else if (price_to.isEmpty() && price_from.isEmpty()) {
                if (genre == null){
                    if (price == null){
                        System.out.println("Фильтр # Критерии поиска не заданы");
                        model.addAttribute("search_product", productService.getProductList());
                        return "redirect:/search-all";
                    }
                    else if(!price.isEmpty()){
                        if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByPriceAsc());
                            return "search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByPriceDesc());
                            return "search";
                        }
                    }
                }
                if (!genre.isEmpty()){
                    if ("belletristic".equals(genre)) {
                        if (price == null){
                            System.out.println("Фильтр # по жанру \"Художественная литература\"");
                            model.addAttribute("search_product", productRepository.findProductByGenreBelletristic(1));
                            return "search";
                        }
                        else if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenreBelletristicPriceAsc(1));
                            return "search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenreBelletristicPriceDesc(1));
                            return "search";
                        }
                    } else if ("business".equals(genre)) {
                        if (price == null){
                            System.out.println("Фильтр # по жанру \"Деловая литература\"");
                            model.addAttribute("search_product", productRepository.findProductByGenreBusiness(2));
                            return "search";
                        }
                        else if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenreBusinessPriceAsc(2));
                            return "search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenreBusinessPriceDesc(2));
                            return "search";
                        }
                    } else if ("psychology".equals(genre)) {
                        if (price == null){
                            System.out.println("Фильтр # по жанру \"Психология\"");
                            model.addAttribute("search_product", productRepository.findProductByGenrePsychology(3));
                            return "search";
                        }
                        else if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenrePsychologyPriceAsc(3));
                            return "search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenrePsychologyPriceDesc(3));
                            return "search";
                        }
                    } else if ("philosophy_and_religion".equals(genre)) {
                        if (price == null){
                            System.out.println("Фильтр # по жанру \"Философия и религия\"");
                            model.addAttribute("search_product", productRepository.findProductByGenrePhilosophyAndReligion(4));
                            return "search";
                        }
                        else if(price.equals("sort_by_asc_price")){
                            System.out.println("Сортировка по возрастанию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenrePhilosophyAndReligionPriceAsc(4));
                            return "search";
                        }
                        else if(price.equals("sort_by_desc_price")){
                            System.out.println("Сортировка по убыванию # Критерии поиска не заданы");
                            model.addAttribute("search_product", productRepository.findProductByGenrePhilosophyAndReligionPriceDesc(4));
                            return "search";
                        }
                    }
                }
            }
        }

        System.out.println("Фильтр # Критерии поиска не заданы");
        model.addAttribute("search_product", productService.getProductList());
        return "search";

    }

}
