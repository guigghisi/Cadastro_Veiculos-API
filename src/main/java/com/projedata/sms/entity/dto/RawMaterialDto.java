package com.projedata.sms.entity.dto;

import com.projedata.sms.entity.RawMaterial;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RawMaterialDto {
    private String name;
    private Integer stocked;

    public RawMaterial transformEntity() {
        var rawMaterial = new RawMaterial();
        rawMaterial.setName(name);
        rawMaterial.setStocked(stocked);
        return rawMaterial;
    }

    public RawMaterialDto transformDto(RawMaterial rawMaterial) {
        var rawMaterialDto = new RawMaterialDto();
        rawMaterialDto.setName(rawMaterial.getName());
        rawMaterialDto.setStocked(rawMaterial.getStocked());
        return rawMaterialDto;
    }

    public List<RawMaterialDto> transformListDtos(List<RawMaterial> rawMaterials) {
        return rawMaterials.stream().map(this::transformDto).collect(Collectors.toList());
    }
}
