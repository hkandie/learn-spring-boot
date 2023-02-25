package com.auth.client.hs.products;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDataGenerator {
    public List<Product> generatePersons(int no) {
        List<Product> personList = new ArrayList<>();
        Faker faker = new Faker();
        for (int j = 0; j < no; j++) {
            Product person = Product
                .builder()
                .productCode(faker.random().nextInt(1000,9999))
                .productName(faker.name().name())
                .retailPrice(faker.random().nextDouble()*100)
                .unitCost(faker.random().nextDouble()*100)
                .taxRateCode(faker.name().fullName().substring(0,1))
                .build();
            personList.add(person);
        }
        return personList;
    }
}
