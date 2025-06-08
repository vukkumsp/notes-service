package com.vukkumsp.notes_service.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("note")
@Data
public class Note {
    @Id
    private Long id;
    private String content;
}
