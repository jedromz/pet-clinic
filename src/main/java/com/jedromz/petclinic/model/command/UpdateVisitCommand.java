package com.jedromz.petclinic.model.command;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.Visit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class UpdateVisitCommand {
    private Long id;
    private LocalDateTime dateTime;
    private int version;
}
