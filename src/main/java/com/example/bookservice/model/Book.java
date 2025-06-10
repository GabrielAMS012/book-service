package com.example.bookservice.model;

import com.example.bookservice.model.enums.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Entidade que representa um livro.
 * Utiliza um Enum para o campo 'status' para maior segurança de tipos.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O título não pode estar em branco.")
    private String titulo;

    @NotBlank(message = "O autor não pode estar em branco.")
    private String autor;

    @NotNull(message = "O status não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    private BookStatus status;
}