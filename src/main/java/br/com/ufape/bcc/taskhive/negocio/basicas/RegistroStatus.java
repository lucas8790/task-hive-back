package br.com.ufape.bcc.taskhive.negocio.basicas;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class RegistroStatus {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	private List<Tarefa> tarefa;

	@OneToMany
	private List<TarefaLista> tarefa_lista;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date data_registro;

	@OneToMany
	private List<Status> status;

	public RegistroStatus() {
		this.data_registro = new Date();
	}

	public RegistroStatus(List<Tarefa> tarefa, List<Tarefa> tarefa_lista, List<Status> status) {
		this.tarefa = tarefa;
		this.status = status;
		this.data_registro = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData_registro() {
		return data_registro;
	}

	public void setData_registro(Date data_registro) {
		this.data_registro = data_registro;
	}

	public List<Status> getStatus() {
		return status;
	}

	public void setStatus(List<Status> status) {
		this.status = status;
	}

	public List<Tarefa> getTarefa() {
		return tarefa;
	}

	public void setTarefa(List<Tarefa> tarefa) {
		this.tarefa = tarefa;
	}

	public List<TarefaLista> getTarefa_lista() {
		return tarefa_lista;
	}

	public void setTarefa_lista(List<TarefaLista> tarefa_lista) {
		this.tarefa_lista = tarefa_lista;
	}
	
}
