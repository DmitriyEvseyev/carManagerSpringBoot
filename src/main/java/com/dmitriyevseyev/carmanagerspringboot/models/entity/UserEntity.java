package com.dmitriyevseyev.carmanagerspringboot.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id) && Objects.equals(userName, userEntity.userName) && Objects.equals(password, userEntity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password);
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String userName;
        private String password;

        Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password (String password) {
            this.password = password;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(
                    id,
                    userName,
                    password
            );
        }
    }
}


