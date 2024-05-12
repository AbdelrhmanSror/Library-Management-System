package com.example.librarymanagementsystem.services;

import com.example.librarymanagementsystem.dtos.PatronDTO;
import com.example.librarymanagementsystem.entities.Patron;
import com.example.librarymanagementsystem.exceptions.PatronNotFoundException;
import com.example.librarymanagementsystem.repos.PatronRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@CacheConfig(cacheNames = "patrons")
@Service
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;

    @Cacheable(key = "'allPatrons'")
    @Override
    public List<PatronDTO> getAllPatrons() {
        return patronRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#id")
    @Override
    public PatronDTO getPatronById(Long id) {
        Optional<PatronDTO> optionalPatronDTO = patronRepository.findById(id).map(this::convertToDTO);
        return optionalPatronDTO.orElseThrow(PatronNotFoundException::new);
    }

    @Override
    public Long addPatron(PatronDTO patronDTO) {
        Patron savedPatron = patronRepository.save(convertToEntity(patronDTO));
        return savedPatron.getId();
    }

    @Override
    public void updatePatron(Long id, PatronDTO patronDTO) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        if (optionalPatron.isPresent()) {
            Patron existingPatron = optionalPatron.get();
            existingPatron.setName(patronDTO.getName());
            existingPatron.setContactInformation(patronDTO.getContactInformation());
            patronRepository.save(existingPatron);
        } else {
            throw new PatronNotFoundException();
        }
    }


    @Override
    public void deletePatron(Long id) {
        if (patronRepository.existsById(id)) {
            patronRepository.deleteById(id);
        } else {
            throw new PatronNotFoundException();
        }
    }

    private PatronDTO convertToDTO(Patron patron) {
        return PatronDTO.builder()
                .name(patron.getName())
                .contactInformation(patron.getContactInformation())
                .build();
    }

    private Patron convertToEntity(PatronDTO patronDTO) {
        return Patron.builder()
                .name(patronDTO.getName())
                .contactInformation(patronDTO.getContactInformation())
                .build();
    }
}
