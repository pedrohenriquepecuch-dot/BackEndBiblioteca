package com.pedro.biblioteca.controller;

import com.pedro.biblioteca.model.Livro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

  private final List<Livro> livros = new ArrayList<>();
  private long proximoId = 1;

  // GET /livros  (com filtros opcionais e combináveis)
  // Ex: /livros?autor=tolkien&genero=fantasia&anoMinimo=1950
  @GetMapping
  public ResponseEntity<List<Livro>> listar(
      @RequestParam(required = false) String autor,
      @RequestParam(required = false) String genero,
      @RequestParam(required = false) Integer anoMinimo) {

    List<Livro> resultado = new ArrayList<>();

    for (Livro l : livros) {
      if (autor != null && (l.getAutor() == null
          || !l.getAutor().toLowerCase().contains(autor.toLowerCase()))) {
        continue;
      }
      if (genero != null && !genero.equalsIgnoreCase(l.getGenero())) {
        continue;
      }
      if (anoMinimo != null && (l.getAno() == null || l.getAno() < anoMinimo)) {
        continue;
      }
      resultado.add(l);
    }

    return ResponseEntity.ok(resultado);
  }

  // GET /livros/{id}
  @GetMapping("/{id}")
  public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
    Livro livro = encontrar(id);
    if (livro == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(livro);
  }

  // POST /livros
  @PostMapping
  public ResponseEntity<Livro> cadastrar(@RequestBody Livro livro) {
    livro.setId(proximoId++);
    livros.add(livro);
    return ResponseEntity.status(HttpStatus.CREATED).body(livro);
  }

  // PUT /livros/{id}
  @PutMapping("/{id}")
  public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro dados) {
    Livro livro = encontrar(id);
    if (livro == null) {
      return ResponseEntity.notFound().build();
    }
    livro.setTitulo(dados.getTitulo());
    livro.setAutor(dados.getAutor());
    livro.setGenero(dados.getGenero());
    livro.setAno(dados.getAno());
    return ResponseEntity.ok(livro);
  }

  // DELETE /livros/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    boolean removeu = livros.removeIf(l -> l.getId().equals(id));
    if (!removeu) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }

  private Livro encontrar(Long id) {
    for (Livro l : livros) {
      if (l.getId().equals(id)) {
        return l;
      }
    }
    return null;
  }
}