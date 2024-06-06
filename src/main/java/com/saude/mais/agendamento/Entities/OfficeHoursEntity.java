package com.saude.mais.agendamento.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "office_hours")
public class OfficeHoursEntity implements Serializable {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "week_day", nullable = false)
    String weekDay;

    @Column(name = "start_hour", nullable = false)
    String startHour;

    @Column(name = "end_hour", nullable = false)
    String endHour;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospital;

    public OfficeHoursEntity(String weekDay, String startHour, String endHour) {this.id = id;
        this.weekDay = weekDay;
        this.startHour = startHour;
        this.endHour = endHour;
    }

}
