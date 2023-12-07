package com.example.itog.Controller;

import com.example.itog.model.Category;
import com.example.itog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "category/list"; // Замените на имя вашего Thymeleaf-шаблона
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/add"; // Замените на имя вашего Thymeleaf-шаблона для добавления категории
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "category/add"; // Останется на странице добавления с отображением ошибок
        }

        categoryRepository.save(category);
        return "redirect:/category"; // Перенаправление на страницу со всеми категориями
    }

    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));

        model.addAttribute("category", category);
        return "category/edit"; // Замените на имя вашего Thymeleaf-шаблона для редактирования категории
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Long id, @Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            category.setId(id); // Set the ID back to the model to render the edit form correctly.
            return "category/edit"; // Останется на странице редактирования с отображением ошибок
        }

        categoryRepository.save(category);
        return "redirect:/category"; // Перенаправление на страницу со всеми категориями
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));

        categoryRepository.delete(category);
        return "redirect:/category"; // Перенаправление на страницу со всеми категориями
    }
}