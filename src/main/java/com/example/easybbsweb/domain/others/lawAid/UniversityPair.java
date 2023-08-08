package com.example.easybbsweb.domain.others.lawAid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityPair {
    Long uniId;
    AtomicInteger cnt;
    Integer easyCnt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityPair that = (UniversityPair) o;
        return uniId.equals(that.uniId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniId);
    }
}
