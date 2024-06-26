package com.altimetrik.category.controller;

import brave.Tracer;
import com.altimetrik.category.dto.CategoryAPIResponse;
import com.altimetrik.category.dto.CategoryDto;
import com.altimetrik.category.dto.CategoryResponse;
import com.altimetrik.category.errorHandling.ErrorHandlingControllerAdvice;
import com.altimetrik.category.services.CategoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@CrossOrigin(origins = "http://localhost:9191")
@RestController
@RequestMapping("/category-service")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    Tracer tracer;
    @PostMapping("categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CategoryAPIResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody @Valid CategoryDto categoryDto,@RequestHeader String role) throws IOException, ErrorHandlingControllerAdvice {
        if(!role.equals("Admin")){
            throw new AccessDeniedException("Not Authorised...!!!");
        }
        log.info("Inside addCategory() method....");

        CategoryResponse categoryResponse = categoryService.addCategory(categoryDto);
        log.info("Successfully returning addCategoryResponse...."+categoryResponse);
        return ResponseEntity.ok(categoryResponse);

    }
    @GetMapping("categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CategoryAPIResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<CategoryResponse> getCategoryDetails(@RequestParam(value = "type",defaultValue = "All") String CategoryType,
                                                               @RequestParam(value = "pageNo",defaultValue = "1") int page , @RequestParam(value="pageSize",defaultValue = "10") int size,
                                                               @RequestParam(value="order",defaultValue = "asc")String sortByOrder, @RequestParam(value = "sortBy",defaultValue = "inventory")String sortBy, @RequestHeader String role) throws ErrorHandlingControllerAdvice, AccessDeniedException {
        if(!role.equals("User") && !role.equals("Admin")){
            throw new AccessDeniedException("Not Authorised...!!!");
        }

        log.info("Inside getCategoryDetails() method....");
      //  MDC.put("correlationId",tracer.currentSpan().context().traceIdString());
        CategoryResponse categoryResponse = categoryService.getCategoryDetails(CategoryType,page,size,sortByOrder,sortBy);
        log.info("Successfully returning CategoryAPIResponse...."+categoryResponse);
        if( categoryResponse.getCategoryAPIResponse().isEmpty()){
            throw new NullPointerException("Inventory is Not Available for the Selected Category and Name...!!!");
        }
        return ResponseEntity.ok(categoryResponse);
    }
    @DeleteMapping("categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CategoryAPIResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity deleteCategory(@RequestBody CategoryDto categoryDto,@RequestHeader String role) throws IOException, ErrorHandlingControllerAdvice {
        if(!role.equals("Admin")){
            throw new AccessDeniedException("Not Authorised...!!!");
        }
        log.info("Inside deleteCategory() method....");
        CategoryResponse categoryResponse =  categoryService.deleteCategory(categoryDto);
        return ResponseEntity.ok(categoryResponse);
       }
}
