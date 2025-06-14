package s05t02.interactiveCV.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import s05t02.interactiveCV.globalVariables.ApiPaths;
import s05t02.interactiveCV.service.cloud.CloudStorageService;

import java.util.Map;

@RestController
@RequestMapping(ApiPaths.CLOUD_STORAGE_PATH)
@RequiredArgsConstructor
public class CloudStorageController {

    private final CloudStorageService cloudStorageService;

    @PostMapping("/signature")
    public Mono<Map<String, Object>> getSignature(@PathVariable String username,
                                                  @RequestBody
                                                  @Valid
                                                  @NotEmpty(message = "Request body must not be empty")
                                                  Map<String, Object> body) {
        return cloudStorageService.authenticateUpload(username, body);
    }
}

