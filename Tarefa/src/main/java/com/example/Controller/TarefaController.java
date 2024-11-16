package com.example.Controller;

import com.example.Model.Tarefa;
import com.example.Model.TarefaSpecification;
import com.example.Repository.TarefaRepository;
import com.example.Service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;
    @Autowired
    private TarefaRepository tarefaRepository;

    //cria varias task
    @PostMapping("/varias")
    public List<Tarefa> criarTasks(@RequestBody List<Tarefa> tarefas){
        return tarefaService.insertTasks(tarefas);
    }

    //cria task
    @PostMapping
    public Tarefa criarTask(@RequestBody Tarefa tarefa){
        return tarefaService.insertTask(tarefa);
    }

    //consulta tasks
    @GetMapping
    public List<Tarefa> listarTask(){
        return tarefaService.selectAllTask();
    }

    //move de uma coluna pra outra
    @PutMapping("/{id}/mover")
    public Tarefa moverTask(@PathVariable long id){
        return tarefaService.moverTask(id);
    }

    //edita a task
    @PutMapping("/{id}")
    public Tarefa atualizarTarefa(@PathVariable long id, @RequestBody Tarefa tarefaUpdate) {
        return tarefaService.atualizarTarefa(id, tarefaUpdate);
    }

    //consulta uma task em especifico
    @GetMapping("/{id}")
    public Tarefa buscarById(@PathVariable long id){
        return tarefaService.selectTaskById(id);
    }

    //deleta task
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable long id){
        tarefaService.deletarTask(id);
    }

    //filtrar tarefas por status, prioridade e data limite
    @GetMapping("/filtro")
    public List<Tarefa> getTarefas(@RequestParam(required = false) String status,
                                   @RequestParam(required = false) String prioridade,
                                   @RequestParam(required = false) String datalimite) {

        // Inicializa a Specification com o filtro de status (caso o status seja fornecido)
        Specification<Tarefa> spec = Specification.where(null);

        if (status != null && !status.isEmpty()) {
            spec = spec.and(TarefaSpecification.hasStatus(status));
        }

        if (prioridade != null && !prioridade.isEmpty()) {
            spec = spec.and(TarefaSpecification.hasPrioridade(prioridade));
        }

        if (datalimite != null && !datalimite.isEmpty()) {
            // Conversão de datalimite, caso seja uma string no formato DD-MM-YYYY
            LocalDate dataLimiteConvertida = LocalDate.parse(datalimite);
        }

        // Retorna as tarefas filtradas
        return tarefaRepository.findAll(spec);

        //PAra usar o filtro deve utilizar o query param
        //exemplo
        //GET http://localhost:8090/tasks/filtro?prioridade=alta
    }

    @GetMapping("/relatorio")
    public Map<String, List<Tarefa>> gerarRelatorio() {

        Specification<Tarefa> spec = Specification.where(TarefaSpecification.isAtrasada());

        List<Tarefa> tarefasAtrasadas = tarefaRepository.findAll(spec);

        return tarefasAtrasadas.stream()
                .collect(Collectors.groupingBy(Tarefa::getStatus));
    }
}
