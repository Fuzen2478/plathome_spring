package com.example.plathome.login.email.dto.request;

import com.example.plathome.login.jwt.dto.request.annotation.AjouEmail;
import lombok.Builder;

@Builder
public record MailForm(
        @AjouEmail String email
) {

    public static MailFormBuilder of() {
        return MailForm.builder();
    }
}
