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
@Table
public class UserProfile extends BaseEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @OneToOne(targetEntity = User.class, mappedBy = "userProfile")
    private User user;


    @OneToMany(targetEntity = Car.class, mappedBy = "user")
    private List<Car> cars;


    @OneToMany(targetEntity = Garage.class, mappedBy = "user")
    private List<Garage> garages;


    @OneToMany(targetEntity = Receipt.class, mappedBy = "user")
    private List<Receipt> receipts;


}
