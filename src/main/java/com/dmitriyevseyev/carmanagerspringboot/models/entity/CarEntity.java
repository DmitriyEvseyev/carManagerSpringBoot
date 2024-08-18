package com.dmitriyevseyev.carmanagerspringboot.models.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CARS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarEntity implements Serializable {
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    private CarDealershipEntity dealer;
    @Column(name = "car_name")
    private String name;
    @Column(name = "car_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @Column(name = "car_color")
    private String color;
    @Column(name = "is_after_crash")
    private boolean isAfterCrash;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", dealerId=" + dealer.getId() +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", color='" + color + '\'' +
                ", isAfterCrash=" + isAfterCrash +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarEntity carEntity = (CarEntity) o;
        return isAfterCrash == carEntity.isAfterCrash &&
                Objects.equals(id, carEntity.id) &&
                Objects.equals(dealer.getId(), carEntity.dealer.getId()) &&
                Objects.equals(name, carEntity.name) &&
                Objects.equals(date, carEntity.date) &&
                Objects.equals(color, carEntity.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dealer.getId(), name, date, color, isAfterCrash);
    }
}


