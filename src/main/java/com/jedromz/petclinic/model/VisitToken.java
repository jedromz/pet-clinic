package com.jedromz.petclinic.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Data
@NoArgsConstructor
@Entity
@Table(name = "visit_token")
public class VisitToken {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String token;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Visit visit;

    public VisitToken(String token, Visit visit) {
        this.token = token;
        this.visit = visit;
    }
}
