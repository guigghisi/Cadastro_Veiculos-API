package com.projedata.sms.controller;

import com.projedata.sms.entity.dto.RawMaterialDto;
import com.projedata.sms.service.RawMaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {
    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RawMaterialDto createRawMaterial(@RequestBody RawMaterialDto rawMaterialDto) {
        var rawMaterial = rawMaterialDto.transformEntity();
        var savedRawMaterial = rawMaterialService.createNewRawMaterial(rawMaterial);

        return rawMaterialDto.transformDto(savedRawMaterial);
    }

    @PutMapping("/{rawMaterialId}")
    @ResponseStatus(HttpStatus.OK)
    public RawMaterialDto updateRawMaterial(@PathVariable(value = "rawMaterialId") Long rawMaterialId, @RequestBody RawMaterialDto rawMaterialDto) {
        //TODO validar se a materia prima existe/fazer o usuario escolher entre uma lista de produtos
        var newRawMaterial = rawMaterialService.updateRawMaterial(rawMaterialId, rawMaterialDto.transformEntity());
        return rawMaterialDto.transformDto(newRawMaterial);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RawMaterialDto> findRawMaterial() {
        var rawMaterials = rawMaterialService.findAll();
        var rawMaterialsDto = new RawMaterialDto();
        return rawMaterialsDto.transformListDtos(rawMaterials);
    }

    @GetMapping("/{rawMaterialId}")
    @ResponseStatus(HttpStatus.OK)
    public RawMaterialDto findRawMaterial(@PathVariable(value = "rawMaterialId") Long rawMaterialId) {
        var rawMaterial = rawMaterialService.findById(rawMaterialId);
        var rawMaterialDto = new RawMaterialDto();
        return rawMaterialDto.transformDto(rawMaterial);
    }

    @DeleteMapping("/{rawMaterialId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRawMaterial(@PathVariable(value = "rawMaterialId") Long rawMaterialId) {
        rawMaterialService.deleteRawMaterial(rawMaterialId);
    }
}
