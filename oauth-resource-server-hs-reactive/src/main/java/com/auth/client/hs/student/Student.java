package com.auth.client.hs.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {
    private String id;
    private String firstName;
    private String lastName;
}
