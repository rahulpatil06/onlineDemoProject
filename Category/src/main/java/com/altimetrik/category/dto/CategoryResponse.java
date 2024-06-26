package com.altimetrik.category.dto;

import com.altimetrik.category.errorHandling.CustomErrorResponse;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class CategoryResponse {
    private Header header;
    private List<CategoryAPIResponse> categoryAPIResponse;
    private CustomErrorResponse errorDetails;
}
