package com.covid19.plasma.dao.entities;

import com.covid19.plasma.enums.RequestToDonorStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "requestor_donor_mapper")
public class RequestorDonorMapperEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requestor_id")
    private PlasmaRequestor plasmaRequestor;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private PlasmaDonor plasmaDonor;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestToDonorStatus status;

    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}
