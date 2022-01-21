package io.vukotic.flightadvisor.service;

import com.opencsv.bean.CsvToBeanBuilder;
import io.vukotic.flightadvisor.persistence.model.Airport;
import io.vukotic.flightadvisor.persistence.repository.AirportRepository;
import io.vukotic.flightadvisor.persistence.repository.CityRepository;
import io.vukotic.flightadvisor.dto.AirportDto;
import io.vukotic.flightadvisor.error.exception.CorruptedFileException;
import io.vukotic.flightadvisor.mapper.AirportMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;
    private final ModelMapper modelMapper;
    private final CityRepository cityRepository;

    public void importAirports(MultipartFile file) {

        List<AirportDto> airportDtos = null;
        try {
            airportDtos = new CsvToBeanBuilder<AirportDto>(new BufferedReader(new InputStreamReader(file.getInputStream())))
                    .withType(AirportDto.class)
                    .withThrowExceptions(false)
                    .build()
                    .parse();
        } catch (IOException ex) {
            throw new CorruptedFileException();
        }

        AirportMapper airportMapper = new AirportMapper(modelMapper, cityRepository.findAll());
        var airportEntities = airportDtos
                .parallelStream()
                .map(airportMapper::convertAirportToEntity)
                .filter(Objects::nonNull)
                .filter(this::validateAirportFields)
                .collect(Collectors.toList());
        airportRepository.saveAll(airportEntities);



    }

    private boolean validateAirportFields(Airport airport){
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(airport);
        return violations.isEmpty();
    }
}
