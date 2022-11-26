package com.auth.client.hs.student;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends ReactiveMongoRepository<Student, String> {
}
