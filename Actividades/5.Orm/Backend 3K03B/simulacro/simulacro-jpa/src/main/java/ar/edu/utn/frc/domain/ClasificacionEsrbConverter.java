package ar.edu.utn.frc.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ClasificacionEsrbConverter implements AttributeConverter<ClasificacionEsrb, String> {
    @Override public String convertToDatabaseColumn(ClasificacionEsrb attribute) {
        return attribute == null ? null : attribute.code();
    }
    @Override public ClasificacionEsrb convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ClasificacionEsrb.fromCode(dbData);
    }
}