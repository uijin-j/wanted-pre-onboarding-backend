package wanted.backend.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wanted.backend.business.JobOpeningService;
import wanted.backend.dto.request.JobOpeningCreateRequest;
import wanted.backend.dto.request.JobOpeningUpdateRequest;
import wanted.backend.dto.response.JobOpeningResponse;
import wanted.backend.presentation.ApiResponse;

@RestController
@RequestMapping("/api/job-openings")
@RequiredArgsConstructor
public class JobOpeningController {

    private final JobOpeningService jobOpeningService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<JobOpeningResponse> createJobOpening(
        @RequestBody @Valid JobOpeningCreateRequest request
    ) {
        JobOpeningResponse response = jobOpeningService.post(request);
        return ApiResponse.of(HttpStatus.CREATED, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<JobOpeningResponse> updateJobOpening(
        @PathVariable Long id,
        @RequestBody @Valid JobOpeningUpdateRequest request
    ) {
        JobOpeningResponse response = jobOpeningService.update(id, request);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteJobOpening(@PathVariable Long id) {
        jobOpeningService.delete(id);
        return ApiResponse.noContent();
    }
}
