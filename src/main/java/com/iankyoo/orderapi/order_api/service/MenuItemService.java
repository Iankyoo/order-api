package com.iankyoo.orderapi.order_api.service;

import com.iankyoo.orderapi.order_api.dto.CreateMenuItemRequest;
import com.iankyoo.orderapi.order_api.dto.MenuItemResponse;
import com.iankyoo.orderapi.order_api.dto.UpdateMenuItemRequest;
import com.iankyoo.orderapi.order_api.entity.MenuItem;
import com.iankyoo.orderapi.order_api.exception.MenuItemNotFoundException;
import com.iankyoo.orderapi.order_api.repository.MenuItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MenuItemService {
    private final MenuItemRepository repository;

    public MenuItemService(MenuItemRepository repository){
        this.repository = repository;
    }

    public MenuItemResponse toResponse(MenuItem menuItem){
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getCategory(),
                menuItem.getAvailable(),
                menuItem.getCreatedAt()
        );
    }

    public Page<MenuItemResponse> findAll(Pageable pageable){
        log.info("Finding all MenuItem");
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    public MenuItemResponse findById(Long id){
        log.info("Finding MenuItem with id: {}", id);
        MenuItem menuItem = repository.findById(id)
                .orElseThrow(()-> new MenuItemNotFoundException(id));
        return toResponse(menuItem);
    }

    public Page<MenuItemResponse> findByName(String name, Pageable pageable){
        log.info("Searching MenuItem with name: {}", name);
        return repository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::toResponse);
    }

    public MenuItemResponse createMenuItem(CreateMenuItemRequest request){
        log.info("Creating new MenuItem");
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.name());
        menuItem.setCategory(request.category());
        menuItem.setDescription(request.description());
        menuItem.setPrice(request.price());
        menuItem.setAvailable(true);
        MenuItem saved = repository.save(menuItem);
        return toResponse(saved);
    }

    public MenuItemResponse updateMenuItem(Long id, UpdateMenuItemRequest request){
        log.info("Update MenuItem with id: {}", id);
        MenuItem toUpdate = repository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
        toUpdate.setName(request.name());
        toUpdate.setPrice(request.price());
        toUpdate.setCategory(request.category());
        toUpdate.setDescription(request.description());
        toUpdate.setAvailable(true);
        MenuItem saved = repository.save(toUpdate);
        return toResponse(saved);
    }

    public void deleteMenuItem(Long id){
        log.info("Delete MenuItem with id: {}", id);
        MenuItem toDelete = repository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
        repository.delete(toDelete);
    }

}
