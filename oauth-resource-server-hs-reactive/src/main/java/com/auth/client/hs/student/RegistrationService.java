package com.auth.client.hs.student;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
@Service
@Transactional
@AllArgsConstructor
public class RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;
    public Flux<Student> getAll() {
        return registrationRepository.findAll().switchIfEmpty(Flux.empty());
    }
    public Mono<Student> getById(final String id) {
        return registrationRepository.findById(id);
    }
    public Mono update(final Student student) {
        return registrationRepository.save(student);
    }
    public Mono save(final Student student) {
        return registrationRepository.save(student);
    }

    public Mono delete(final String id) {
        final Mono<Student> dbStudent = getById(id);
        if (Objects.isNull(dbStudent)) {
            return Mono.empty();
        }
        return getById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull).flatMap(studentToBeDeleted -> registrationRepository
                .delete(studentToBeDeleted).then(Mono.just(studentToBeDeleted)));
    }
}
