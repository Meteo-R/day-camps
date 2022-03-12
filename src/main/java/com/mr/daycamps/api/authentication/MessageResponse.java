package com.mr.daycamps.api.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
