package com.investment.service.contract;

import com.investment.model.EmailModel;
import jakarta.validation.constraints.NotNull;

public interface EmailService {

    void send(@NotNull EmailModel emailModel, @NotNull String templateKey);
}
