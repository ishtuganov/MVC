package ishtuganov.springcourse.controllers;
import ishtuganov.springcourse.services.BooksService;
import ishtuganov.springcourse.services.PeopleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ishtuganov.springcourse.models.Book;
import ishtuganov.springcourse.models.Person;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "num", defaultValue = "0", required = false) int pageNumber,
                        @RequestParam(value = "count", defaultValue = "6", required = false) int booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        model.addAttribute("books", booksService.findAll(pageNumber, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam(value = "text") String text){
        model.addAttribute("books", booksService.findAllByNameStartingWith(text));
        return "books/search";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model){
        Person owner = booksService.findOne(id).getPerson();
        if (owner != null)
            model.addAttribute("owner", owner);
        else model.addAttribute("people", peopleService.findAll());
        model.addAttribute("book", booksService.findOne(id));

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/create";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @GetMapping("/{id}/free")
    public String free(@PathVariable("id") int id){
        booksService.releaseBookById(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String add(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        booksService.assignBookOwner(person, id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
}
