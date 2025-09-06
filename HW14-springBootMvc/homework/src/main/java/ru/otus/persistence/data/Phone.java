package ru.otus.persistence.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "phone")
public record Phone(@Id Long id, String phone, @Column("client_id") Long clientId, Integer ord) {}
