package com.auth.client.hs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LargeMessage {
    String id;
    String message;
}
