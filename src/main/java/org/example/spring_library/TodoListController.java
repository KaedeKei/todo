package org.example.spring_library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/todos")
public class TodoListController {
    @Autowired
    private TodoListRepository todoListRepository;

    @GetMapping
    public String getAllTodos(Model model) {
        model.addAttribute("todos", todoListRepository.findAll((Sort.by(Sort.Direction.ASC, "timeline"))));
        return "index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "create-todo";
    }

    @PostMapping
    public String createTodo(Todo todo) {
        System.out.println(todo);
        todoListRepository.save(todo);
        return "redirect:/api/todos";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Todo todo = todoListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo id: " + id));
        model.addAttribute("todo", todo);
        return "edit-todo";
    }

    @PostMapping("/update/{id}")
    public String updateTodoList(@PathVariable Long id, Todo updatedTodo) {
        Todo todo = todoListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo id: " + id));
        todo.setTitle(updatedTodo.getTitle());
        todo.setDescription(updatedTodo.getDescription());
        todo.setTimeline(updatedTodo.getTimeline());
        todo.setDone(updatedTodo.getDone());
        todoListRepository.save(todo);
        return "redirect:/api/todos";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        Todo todo = todoListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo id: " + id));
        todoListRepository.delete(todo);
        return "redirect:/api/todos";
    }

    @GetMapping("/active")
    public String showActiveTodo(Model model) {
        model.addAttribute("todos", todoListRepository.findAll((Sort.by(Sort.Direction.ASC, "timeline"))));
        return "active";
    }

    @GetMapping("/unactive")
    public String showUnactiveTodo(Model model) {
        model.addAttribute("todos", todoListRepository.findAll((Sort.by(Sort.Direction.ASC, "timeline"))));
        return "unactive";
    }
}
