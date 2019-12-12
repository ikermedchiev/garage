package com.balt.garage.data.models;

import com.balt.garage.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends BaseEntity implements GrantedAuthority {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @Transient
    private String authority;

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
