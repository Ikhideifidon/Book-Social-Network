package com.ikhideifidon.book_network.feedback;

import com.ikhideifidon.book_network.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Integer> saveFeedback(@RequestBody @Valid FeedbackRequest request, Authentication connectedUser) {
        return ResponseEntity.ok(feedbackService.save(request, connectedUser));
    }

    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> findAllFeedbackByBook(
            @PathVariable(name = "book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "page", defaultValue = "0", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(feedbackService.findAllFeedbackByBook(bookId, page, size, connectedUser));
    }

}
