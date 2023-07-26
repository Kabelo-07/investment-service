package com.investment.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    protected UUID id;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreationTimestamp
    protected Instant createdDate;

    @Column(name = "updated_date", nullable = false)
    @UpdateTimestamp
    protected Instant updatedDate;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractEntity)) {
            return false;
        }
        return id != null && id.equals(((AbstractEntity) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
