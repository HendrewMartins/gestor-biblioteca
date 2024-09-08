package br.hendrew.gestor_biblioteca.model;

import br.hendrew.gestor_biblioteca.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "emprestimo", schema = "public")
@SequenceGenerator(schema = "public", name = "seq_emprestimo", sequenceName = "seq_emprestimo", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo implements Serializable {

    @Id
    @Column(name = "emprestimoId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_emprestimo")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "livroId")
    private Livro livro;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @NotNull
    @Column(name = "dataEmprestimo", updatable = false)
    private LocalDate dataEmprestimo;

    @Column(name = "dataDevolucao", insertable = false)
    private LocalDate dataDevolucao;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;
}
