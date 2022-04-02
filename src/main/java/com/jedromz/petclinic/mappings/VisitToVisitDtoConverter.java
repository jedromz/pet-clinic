package com.jedromz.petclinic.mappings;

import com.jedromz.petclinic.model.Visit;
import com.jedromz.petclinic.model.dto.VisitDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class VisitToVisitDtoConverter implements Converter<Visit, VisitDto> {
    @Override
    public VisitDto convert(MappingContext<Visit, VisitDto> mappingContext) {
        Visit visit = mappingContext.getSource();
        VisitDto visitDto = VisitDto.builder()
                .dateTime(visit.getDateTime())
                .version(visit.getVersion())
                .build();

        return visitDto;
    }
}
