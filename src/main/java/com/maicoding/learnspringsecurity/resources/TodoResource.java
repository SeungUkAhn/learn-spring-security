package com.maicoding.learnspringsecurity.resources;

import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final List<Todo> TODOS_LIST =
            List.of(new Todo("maicoding", "Learn AWS"),
                    new Todo("maicoding", "Learn Azure"));

    @GetMapping("/todos")
    public List<Todo> retrieveAllTodos(){
        return TODOS_LIST;
    }

    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authentication.name")
    @PostAuthorize("returnObject.username == 'maicoding'")
    @RolesAllowed({"ADMIN", "USER"})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Todo retrieveTodosForOneUser(@PathVariable String username){
        return TODOS_LIST.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void createTodoForOneUser(@PathVariable String username, @RequestBody Todo todo){
        logger.info("Create {} for {}", todo, username);
    }
}

record Todo (String username, String description){}