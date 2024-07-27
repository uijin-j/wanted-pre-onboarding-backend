package wanted.backend.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.backend.business.JobOpeningService;
import wanted.backend.dto.request.JobOpeningCreateRequest;
import wanted.backend.dto.response.JobOpeningResponse;
import wanted.backend.presentation.ApiResponse;

@RestController
@RequestMapping("/api/job-openings")
@RequiredArgsConstructor
public class JobOpeningController {

    private final JobOpeningService jobOpeningService;

    @PostMapping
    public ApiResponse<JobOpeningResponse> createJobOpening(
        @RequestBody @Valid JobOpeningCreateRequest request
    ) {
        JobOpeningResponse response = jobOpeningService.createJobOpening(request);
        return ApiResponse.ok(response);
    }
}
