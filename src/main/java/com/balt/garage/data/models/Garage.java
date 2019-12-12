package com.balt.garage.data.models;

import com.balt.garage.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "garages")
public class Garage extends BaseEntity {

    @Column(name = "town", nullable = false)
    private String town;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "is_free", nullable = false)
    private boolean isFree;

    @OneToMany(targetEntity = Date.class, mappedBy = "garage")
    private List<Date> freeDates;

    @Column(name = "laititude")
    private String laititude;

    @Column(name = "longtitude")
    private String longtitude;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserProfile user;


}
