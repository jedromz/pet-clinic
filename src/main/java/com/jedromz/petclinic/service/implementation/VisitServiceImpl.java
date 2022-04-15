package com.jedromz.petclinic.service.implementation;

import com.jedromz.petclinic.error.BadDateException;
import com.jedromz.petclinic.error.EntityNotFoundException;
import com.jedromz.petclinic.error.ScheduleConflictException;
import com.jedromz.petclinic.model.*;
import com.jedromz.petclinic.model.command.CheckVisitsCommand;
import com.jedromz.petclinic.model.command.CreateVisitCommand;
import com.jedromz.petclinic.model.command.UpdateVisitCommand;
import com.jedromz.petclinic.model.dto.NotificationEmail;
import com.jedromz.petclinic.repository.PetRepository;
import com.jedromz.petclinic.repository.VetRepository;
import com.jedromz.petclinic.repository.VisitRepository;
import com.jedromz.petclinic.repository.VisitTokenRepository;
import com.jedromz.petclinic.service.MailService;
import com.jedromz.petclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final MailService mailService;
    private final VetRepository vetRepository;
    private final PetRepository petRepository;
    private final VisitTokenRepository visitTokenRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<Visit> findAll(Pageable pageable) {
        return visitRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Visit> findById(Long id) {
        return visitRepository.findById(id);
    }

    @Transactional
    public Visit save(CreateVisitCommand command) {
        if (checkSchedules(command)) {
            throw new ScheduleConflictException();
        }
        Vet vet = vetRepository.findVetWithVisits(command.getVetId(), LockModeType.PESSIMISTIC_READ).orElseThrow(() -> new EntityNotFoundException("Vet", Long.toString(command.getVetId())));
        Pet pet = petRepository.findPetWithVisits(command.getPetId(), LockModeType.PESSIMISTIC_READ).orElseThrow(() -> new EntityNotFoundException("Pet", Long.toString(command.getPetId())));

        Visit visit = modelMapper.map(command, Visit.class);
        Visit savedVisit = visitRepository.saveAndFlush(visit);
        VisitToken visitToken = generateVerificationToken(visit);
        mailService.sendMail(new NotificationEmail("pet-clinic@info.com", "Visit confirmation", pet.getOwnerEmail(), "Thank you for signing up for a visit, please click the link below to confirm:" + "http://localhost:8080/api/visits/confirm/" + visitToken.getToken()
                + " your vet is: " + vet.getFirstname() + " " + vet.getLastname()));

        return savedVisit;
    }

    @Transactional
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    private boolean checkSchedules(CreateVisitCommand command) {
        return visitRepository.existsByDateTimeAndVet_Id(command.getDateTime(), command.getVetId()) || visitRepository.existsByDateTimeAndPet_Id(command.getDateTime(), command.getPetId());
    }

    @Transactional
    public void deleteById(Long id) {
        visitRepository.findById(id).ifPresent(visitRepository::delete);
    }

    @Transactional
    public Visit edit(Visit toEdit, UpdateVisitCommand command) {

        toEdit.setDateTime(command.getDateTime());
        toEdit.setVersion(command.getVersion());

        return visitRepository.save(toEdit);
    }

    @Transactional
    public Visit confirmVisit(VisitToken visitToken) {
        Visit visit = visitToken.getVisit();
        if (visit.getDateTime().isAfter(LocalDateTime.now())) {
            throw new BadDateException("Visit", "datetime");
        }
        visit.setConfirmed(true);
        return visitRepository.saveAndFlush(visit);
    }

    @Transactional
    public void cancelVisit(VisitToken visitToken) {
        Long visitId = visitToken.getVisit().getId();
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new EntityNotFoundException("Visit", Long.toString(visitId)));
        if (visit.getDateTime().isBefore(LocalDateTime.now())) {
            throw new BadDateException("Visit", "dateTime");
        }
        visitRepository.delete(visit);
    }

    @Override
    public void deleteAll() {
        visitRepository.deleteAll();
    }

    @Override
    public void saveVisits(List<Visit> newVisit) {
        visitRepository.saveAllAndFlush(newVisit);
    }

    @Override
    public List<Visit> check(CheckVisitsCommand command) {
        return visitRepository.findByDateTimeIsBetweenAndVet_SpecializationAndVet_PetSpecialization(command.getFromDate(), command.getToDate(), command.getSpecialization(), command.getPetSpecialization());
    }

    @Transactional
    public VisitToken generateVerificationToken(Visit visit) {
        String token = UUID.randomUUID().toString();
        VisitToken verificationToken = new VisitToken();
        verificationToken.setToken(token);
        verificationToken.setVisit(visit);
        return visitTokenRepository.saveAndFlush(verificationToken);
    }

    @Scheduled(cron = "00 00 23 * * *")
    public void nextDayVisitNotification() {
        List<Visit> tomorrowVisits = visitRepository.findAll().stream().filter(v -> v.getDateTime().toLocalDate().equals(LocalDate.now().plusDays(1))).toList();
        for (Visit tomorrowVisit : tomorrowVisits) {
            mailService.sendMail(new NotificationEmail("pet-clinic@info.com", "Visit reminder", tomorrowVisit.getPet().getOwnerEmail(), "Just a friendly reminder about your visit planned for tomorrow at: " + tomorrowVisit.getDateTime().toLocalTime()));
        }
    }
}
