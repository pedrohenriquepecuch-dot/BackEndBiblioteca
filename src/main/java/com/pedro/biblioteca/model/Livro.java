package com.pedro.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
  private Long id;
  private String titulo;
  private String autor;
  private String genero;
  private Integer ano;
}
