package com.auth.client.hs.student;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("student")
@AllArgsConstructor
@RestController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @GetMapping
    public Flux<Student> getAll() {
        System.out.println("::GET_ALL Products::");
        return registrationService.getAll();
    }
    @GetMapping("{id}")
    public Mono<Student> getById(@PathVariable("id") final String id) {
        System.out.println("::Will Return a Product::");
        return registrationService.getById(id);
    }
    @PutMapping("{id}")
    public Mono updateById(@RequestBody final Student Student) {
        System.out.println("::Update the Product record::");
        return registrationService.update(Student);
    }
    @PostMapping
    public Mono save(@RequestBody final Student Student) {
        System.out.println("Will register the Product :: "+ Student.getId() + " :: " + Student.getFirstName());
        return registrationService.save(Student);
    }
    @DeleteMapping("{id}")
    public Mono delete(@PathVariable final String id) {
        System.out.println("::Will delete a Product::");
        return registrationService.delete(id);
    }
}
