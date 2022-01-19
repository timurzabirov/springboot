package com.timurzabirov.todolist.springboot.service;

import com.timurzabirov.todolist.springboot.entity.Category;
import com.timurzabirov.todolist.springboot.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
// Все методы должны отработать без ошибки, чтобы транзакция завершилась, иначе все операции откатятся(Rollback)
public class CategoryService {

    //Доступ к данным из БД
    private final CategoryRepository categoryRepository;

    /*Внедрение экземпляра класса через конструктор.
     * Не используем @Autowired для переменной,т.к. Field injection is not recommended*/
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category add(Category category) {
        return categoryRepository.save(category); // метод save обновляет или создает новый объект, если его не было
    }

    public Category update(Category category) {
        return categoryRepository.save(category); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> findByTitle(String text) {
        return categoryRepository.findByTitle(text);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }

    public List<Category> findAllByOrderByTitleAsc() {
        return categoryRepository.findAllByOrderByTitleAsc();
    }


}
