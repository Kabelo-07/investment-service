package com.investment.api;

import com.investment.domain.entity.Investor;
import com.investment.model.InvestmentModel;
import com.investment.model.InvestorModel;
import com.investment.model.page.InvestorsPage;
import com.investment.service.contract.InvestmentService;
import com.investment.service.contract.InvestorService;
import com.investment.utils.InvestorSpecificationBuilder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/investors")
@RequiredArgsConstructor
@Validated
public class InvestorsApiController {

    private final InvestorService investorService;
    private final InvestmentService investmentService;

    @GetMapping
    public ResponseEntity<InvestorsPage> investors(@RequestParam(value = "investor-id", required = false) UUID investorId,
                                                   @RequestParam(value = "first-name", required = false) String firstName,
                                                   @RequestParam(value = "last-name", required = false) String lastName,
                                                   @RequestParam(value = "email-address", required = false) String emailAddress,
                                                   @RequestParam(value = "mobile-number", required = false) String mobileNumber,
                                                   @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNo,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "100") int pageSize) {

        Specification<Investor> specification = InvestorSpecificationBuilder.instance()
                .withEmailAddress(emailAddress)
                .withInvestorId(investorId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withMobileNumber(mobileNumber)
                .build();

        InvestorsPage investorsPage = investorService.findAll(specification, PageRequest.of(pageNo, pageSize));
        return ResponseEntity.ok(investorsPage);
    }

    @GetMapping("/{investor-id}")
    @Operation(description = "Retrieve investor personal information")
    public ResponseEntity<InvestorModel> investorById(@PathVariable("investor-id") UUID investorId) {
        return ResponseEntity.ok(investorService.findInvestorById(investorId));
    }

    @GetMapping("/{investor-id}/investments")
    public ResponseEntity<List<InvestmentModel>> investmentsByInvestorId(@PathVariable("investor-id") UUID investorId) {
        List<InvestmentModel> products = investmentService.findInvestmentsByInvestorId(investorId);
        if (CollectionUtils.isEmpty(products)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

}
