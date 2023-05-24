package itmo.anasteshap.constrollers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.anasteshap.dto.create.CreateFleaRequest;
import itmo.anasteshap.dto.responses.FleaResponse;
import itmo.anasteshap.dto.update.UpdateFleaRequest;
import itmo.anasteshap.services.FleaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fleas")
@Tag(name = "Блохи", description = "Все методы для работы с блохами")
public class FleaController {
    private final FleaService fleaService;

    @Autowired
    public FleaController(FleaService fleaService) {
        this.fleaService = fleaService;
    }

    @PostMapping("/create")
    @Operation(summary = "Добавить новую блоху")
    public ResponseEntity<FleaResponse> createFlea(@Validated @RequestBody CreateFleaRequest request) {
        return new ResponseEntity<>(fleaService.save(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить блоху по ID")
    public ResponseEntity<FleaResponse> getFleaById(@Validated @PathVariable Long id) {
        return new ResponseEntity<>(fleaService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить блоху по ID")
    public ResponseEntity<FleaResponse> deleteFleaById(@Validated @PathVariable Long id) {
        fleaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить данные существующей блохи")
    public ResponseEntity<FleaResponse> updateFlea(@Validated @RequestBody UpdateFleaRequest catRequest) {
        return new ResponseEntity<>(fleaService.update(catRequest), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Получить информацию о всех блохах")
    public ResponseEntity<List<FleaResponse>> getFleas(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        var info = PageRequest.of(page - 1, size);
        return new ResponseEntity<>(fleaService.getAll(info), HttpStatus.OK);
    }

    @GetMapping("/{cat_id}/fleas")
    @Operation(summary = "Получить всех блох у кота по его ID")
    public ResponseEntity<List<FleaResponse>> getAllByOwnerId(@Validated @PathVariable("cat_id") Long id) {
        return new ResponseEntity<>(fleaService.getAllByCatId(id), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Удалить всех блох")
    public ResponseEntity<?> deleteFleas() {
        fleaService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
