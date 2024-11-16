package com.example.Model;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TarefaSpecification {

    // Filtro por status (coluna do Kanban)
    public static Specification<Tarefa> hasStatus(String status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }

    // Filtro por prioridade
    public static Specification<Tarefa> hasPrioridade(String prioridade) {
        return (root, query, builder) -> builder.equal(root.get("prioridade"), prioridade);
    }

    //filtro por data limite
    public static Specification<Tarefa> hasLimite(String datalimite){
        return (root, query, builder) -> builder.equal(root.get("datalimite"), datalimite);
    }

    //filtro por data limite e prioridade
    public static Specification<Tarefa> hasLimiteAndPrioridade(String datalimite, String prioridade){
        return Specification.where(hasLimite(datalimite)).and(hasPrioridade(prioridade));
    }
    // Filtro por status e prioridade
    public static Specification<Tarefa> hasStatusAndPrioridade(String status, String prioridade) {
        return Specification.where(hasStatus(status)).and(hasPrioridade(prioridade));
    }

    // Filtro por data limite (tarefas atrasadas)
    public static Specification<Tarefa> isAtrasada() {
        return (root, query, builder) -> builder.and(
                builder.lessThan(root.get("datalimite"), LocalDateTime.now()), // Data limite passada
                builder.notEqual(root.get("status"), "Concluído") // Status não "Concluído"
        );
    }
}
