package com.dmitriyevseyev.carmanagerspringboot.models.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "DEALERS")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarDealershipEntity implements Serializable {
    @Id
    @Column(name = "dealer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "dealer_name")
    private String name;
    @Column(name = "dealer_address")
    private String address;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dealer",
            cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CarEntity> carEntities;

    public CarDealershipEntity(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.carEntities = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDealershipEntity that = (CarDealershipEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String name;
        private String address;

        Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public CarDealershipEntity build() {
            return new CarDealershipEntity(
                    id,
                    name,
                    address
            );
        }
    }
}