package com.iankyoo.orderapi.order_api.controller;

import com.iankyoo.orderapi.order_api.dto.CreateMenuItemRequest;
import com.iankyoo.orderapi.order_api.dto.MenuItemResponse;
import com.iankyoo.orderapi.order_api.dto.UpdateMenuItemRequest;
import com.iankyoo.orderapi.order_api.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menuitens")
public class MenuItemController {
    private final MenuItemService service;

    public MenuItemController(MenuItemService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Page<MenuItemResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort
    ){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MenuItemResponse>> findByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findByName(name, pageable));
    }

    @PostMapping()
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody CreateMenuItemRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createMenuItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@Valid @RequestBody UpdateMenuItemRequest request, @PathVariable Long id){
        return ResponseEntity.ok(service.updateMenuItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id){
        service.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}
