package com.projedata.sms.service;

import com.projedata.sms.entity.RawMaterial;
import com.projedata.sms.exception.ProductNotFoundException;
import com.projedata.sms.exception.RawMaterialNotFoundExeception;
import com.projedata.sms.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository repository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.repository = rawMaterialRepository;
    }

    public RawMaterial createNewRawMaterial(RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    public RawMaterial updateRawMaterial(Long rawMaterialId, RawMaterial rawMaterial) {
        var newRawMaterial = repository.findById(rawMaterialId).orElseThrow(RawMaterialNotFoundExeception::new);

        newRawMaterial.setName(rawMaterial.getName());
        newRawMaterial.setStocked(rawMaterial.getStocked());
        return repository.save(newRawMaterial);
    }

    public List<RawMaterial> findAll() {
        return repository.findAll();
    }

    public RawMaterial findById(Long rawMaterialId) {
        return repository.findById(rawMaterialId).orElseThrow(ProductNotFoundException::new);
    }

    public void deleteRawMaterial(Long rawMaterialId) {
        var rawMaterial = repository.findById(rawMaterialId).orElseThrow(RawMaterialNotFoundExeception::new);
        repository.delete(rawMaterial);
    }
}
