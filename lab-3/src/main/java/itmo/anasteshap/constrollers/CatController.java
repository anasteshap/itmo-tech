package itmo.anasteshap.constrollers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.anasteshap.dto.create.CreateCatRequest;
import itmo.anasteshap.dto.update.UpdateCatRequest;
import itmo.anasteshap.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cats")
@Tag(name = "Коты", description = "Все методы для работы с котами")
public class CatController {
    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @PostMapping("/create")
    @Operation(summary = "Добавить нового кота")
    public ResponseEntity<?> createCat(@Validated @RequestBody CreateCatRequest request) {
        return new ResponseEntity<>(catService.save(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить кота по ID")
    public ResponseEntity<?> getCatById(@Validated @PathVariable Long id) {
        return new ResponseEntity<>(catService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить кота по ID")
    public ResponseEntity<?> deleteCatById(@Validated @PathVariable Long id) {
        catService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить данные существующего кота")
    public ResponseEntity<?> updateCat(@Validated @RequestBody UpdateCatRequest catRequest) {
        return new ResponseEntity<>(catService.update(catRequest), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Получить информацию о всех котах")
    public ResponseEntity<?> getCats(@RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size) {
        var info = PageRequest.of(page - 1, size);
        return new ResponseEntity<>(catService.getAll(info), HttpStatus.OK);
    }

    @GetMapping("/{owner_id}/cats")
    @Operation(summary = "Получить всех котов у хозяина по его ID")
    public ResponseEntity<?> getAllByOwnerId(@Validated @PathVariable("owner_id") Long id) {
        return new ResponseEntity<>(catService.getAllByOwnerId(id), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Удалить всех котов")
    public ResponseEntity<?> deleteCats() {
        catService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
