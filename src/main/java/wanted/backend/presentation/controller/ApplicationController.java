package wanted.backend.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wanted.backend.business.ApplicationService;
import wanted.backend.dto.request.ApplyRequest;
import wanted.backend.dto.response.ApplyResponse;
import wanted.backend.presentation.ApiResponse;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ApplyResponse> apply(
        @RequestBody @Valid ApplyRequest request
    ) {
        ApplyResponse response = applicationService.apply(request);
        return ApiResponse.ok(response);
    }
}
