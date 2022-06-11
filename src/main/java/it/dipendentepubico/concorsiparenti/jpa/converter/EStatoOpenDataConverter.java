package it.dipendentepubico.concorsiparenti.jpa.converter;

import it.dipendentepubico.concorsiparenti.jpa.entity.EStatoOpenData;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EStatoOpenDataConverter implements AttributeConverter<EStatoOpenData, String> {
    @Override
    public String convertToDatabaseColumn(EStatoOpenData category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }

    @Override
    public EStatoOpenData convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(EStatoOpenData.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
