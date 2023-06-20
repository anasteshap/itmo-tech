package itmo.anasteshap.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.anasteshap.dto.create.CreateOwnerRequest;
import itmo.anasteshap.dto.responses.OwnerResponse;
import itmo.anasteshap.dto.update.UpdateOwnerRequest;
import itmo.anasteshap.services.impl.OwnerServiceImpl;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@Tag(name = "Хозяины", description = "Все методы для работы с хозяинами")
public class OwnerController {
    private final OwnerServiceImpl ownerService;

    @Autowired
    public OwnerController(OwnerServiceImpl ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/create")
    @Operation(summary = "Добавить нового хозяина")
    public ResponseEntity<OwnerResponse> createOwner(@Validated @RequestBody CreateOwnerRequest request) {
        return new ResponseEntity<>(ownerService.save(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить хозяина по ID")
    public ResponseEntity<OwnerResponse> getOwnerById(@Min(1) @Validated @PathVariable Long id) {
        return new ResponseEntity<>(ownerService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить хозяина по ID")
    public ResponseEntity<OwnerResponse> deleteOwnerById(@Min(1) @Validated @PathVariable Long id) {
        ownerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить данные существующего хозяина")
    public ResponseEntity<OwnerResponse> updateOwner(@Validated @RequestBody UpdateOwnerRequest ownerRequest) {
        return new ResponseEntity<>(ownerService.update(ownerRequest), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Получить информацию о всех хозяинах")
    public ResponseEntity<List<OwnerResponse>> getOwners(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        var info = PageRequest.of(page - 1, size);
        return new ResponseEntity<>(ownerService.getAll(info), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Удалить всех хозяинов")
    public ResponseEntity<?> deleteOwners() {
        ownerService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
