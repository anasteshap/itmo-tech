package itmo.anasteshap.constrollers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.anasteshap.dto.create.CreateOwnerRequest;
import itmo.anasteshap.dto.update.UpdateOwnerRequest;
import itmo.anasteshap.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
@Tag(name = "Хозяины", description = "Все методы для работы с хозяинами")
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/create")
    @Operation(summary = "Добавить нового хозяина")
    public ResponseEntity<?> createOwner(@Validated @RequestBody CreateOwnerRequest request) {
        return new ResponseEntity<>(ownerService.save(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить хозяина по ID")
    public ResponseEntity<?> getOwnerById(@Validated @PathVariable Long id) {
        return new ResponseEntity<>(ownerService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить хозяина по ID")
    public ResponseEntity<?> deleteOwnerById(@Validated @PathVariable Long id) {
        ownerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить данные существующего хозяина")
    public ResponseEntity<?> updateOwner(@Validated @RequestBody UpdateOwnerRequest ownerRequest) {
        return new ResponseEntity<>(ownerService.update(ownerRequest), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Получить информацию о всех хозяинах")
    public ResponseEntity<?> getOwners(@RequestParam(required = false, defaultValue = "1") Integer page,
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
