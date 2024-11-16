package com.example.Service;

import com.example.Model.Tarefa;
import com.example.Repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    //cria uma task
    public Tarefa insertTask(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }

    //cria varias tasks
    public List<Tarefa> insertTasks(List<Tarefa> tarefas) {
        return tarefaRepository.saveAll(tarefas);
    }

    //lista todas as tasks
    public List<Tarefa> selectAllTask(){
        return tarefaRepository.findAll();
    }

    // select * from tarefa where "id"=id
    public Tarefa selectTaskById(long id){
        Optional<Tarefa> tk= tarefaRepository.findById(id);
        if(tk.isPresent()){
            return tk.get();
        }else{
            throw new RuntimeException("Task não encontrada.");
        }
    }

    // status A fazer -> Em progresso -> Concluida
    public Tarefa moverTask(long id){
        Tarefa tk = selectTaskById(id);
        if (tk.getStatus().equals("A fazer")){
        tk.setStatus("Em progresso");
        } else {
            tk.setStatus("Task Concluída");
        }
        return tarefaRepository.save(tk);
    }

    // Editar Tarefa
    public Tarefa atualizarTarefa(long id, Tarefa tarefaUpdate) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            tarefa.setTitulo(tarefaUpdate.getTitulo());
            tarefa.setDescricao(tarefaUpdate.getDescricao());
            tarefa.setPrioridade(tarefaUpdate.getPrioridade());
            tarefa.setDatalimite(tarefaUpdate.getDatalimite());
            return tarefaRepository.save(tarefa);
        } else {
            throw new RuntimeException("Tarefa não encontrada com o ID: " + id);
        }
    }

    //deleta task
    public void deletarTask(long id){
        tarefaRepository.deleteById(id);
    }
}
