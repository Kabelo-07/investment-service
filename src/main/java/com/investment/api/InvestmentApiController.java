package com.investment.api;

import com.investment.model.WithdrawalRequest;
import com.investment.model.WithdrawalResponse;
import com.investment.service.contract.InvestmentWithdrawalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/investments")
@RequiredArgsConstructor
@Validated
public class InvestmentApiController {

    private final InvestmentWithdrawalService withdrawalService;

    /**
     *
     * @param request withdrawalRequest
     * @param investmentId unique investment product Identifier
     * @return WithdrawalResponse
     */
    @PostMapping("/{investment-id}/withdraw")
    public ResponseEntity<WithdrawalResponse> withdraw(@Valid @RequestBody WithdrawalRequest request,
                                                       @PathVariable("investment-id") UUID investmentId) {
        WithdrawalResponse response = withdrawalService.processWithdrawal(request, investmentId);
        return ResponseEntity.ok(response);
    }
}
