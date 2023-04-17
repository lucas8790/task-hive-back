package br.com.ufape.bcc.taskhive.negocio.fachada;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufape.bcc.taskhive.negocio.basicas.Comentario;
import br.com.ufape.bcc.taskhive.negocio.basicas.ItemLista;
import br.com.ufape.bcc.taskhive.negocio.basicas.RegistroStatus;
import br.com.ufape.bcc.taskhive.negocio.basicas.Status;
import br.com.ufape.bcc.taskhive.negocio.basicas.Categoria;
import br.com.ufape.bcc.taskhive.negocio.basicas.Lembrete;
import br.com.ufape.bcc.taskhive.negocio.basicas.Tarefa;
import br.com.ufape.bcc.taskhive.negocio.basicas.TarefaLista;
import br.com.ufape.bcc.taskhive.negocio.basicas.Usuario;
import br.com.ufape.bcc.taskhive.negocio.basicas.Papel;
import br.com.ufape.bcc.taskhive.negocio.basicas.Projeto;

import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroItemLista;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroRegistroStatus;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroTarefa;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroTarefaLista;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroPapel;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroProjeto;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroUsuario;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroStatus;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroCategoria;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroComentario;
import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroLembrete;

import br.com.ufape.bcc.taskhive.negocio.cadastro.TarefaNaoExisteException;
import br.com.ufape.bcc.taskhive.negocio.cadastro.UsuarioSemTarefaException;
import br.com.ufape.bcc.taskhive.negocio.cadastro.DoisStatusException;
import br.com.ufape.bcc.taskhive.negocio.cadastro.EmailRepetidoException;
import br.com.ufape.bcc.taskhive.negocio.cadastro.UsuarioNaoExisteException;

@Service
public class Fachada {

    @Autowired
	private InterfaceCadastroTarefa negocioTarefa;
	@Autowired
	private InterfaceCadastroTarefaLista negocioTarefaLista;
	@Autowired
	private InterfaceCadastroItemLista negocioItemLista;
	@Autowired
	private InterfaceCadastroRegistroStatus negocioRegistroStatus;

	@Autowired
	private InterfaceCadastroStatus negocioStatus;
	@Autowired
	private InterfaceCadastroCategoria negocioCategoria;
	@Autowired
	private InterfaceCadastroComentario negocioComentario;
	@Autowired
	private InterfaceCadastroLembrete negocioLembrete;

	//Parte de Ricardo - classes Usuario, UsuarioTarefa, Papel e Projeto
	@Autowired
	private InterfaceCadastroUsuario cadastroUsuario;
	@Autowired
	private InterfaceCadastroProjeto cadastroProjeto;
	@Autowired
	private InterfaceCadastroPapel cadastroPapel;

	public Usuario salvarUsuario(Usuario entity) throws EmailRepetidoException{
		return cadastroUsuario.salvarUsuario(entity);
	}

    public Usuario procurarUsuarioId(long id) {
        return cadastroUsuario.procurarUsuarioId(id);
    }

	public Usuario logarComEmailAndSenha(String email, String senha) throws UsuarioNaoExisteException {
		return cadastroUsuario.logarComEmailAndSenha(email, senha);
	}

	public Projeto salvarProjeto(Projeto entity) {
		return cadastroProjeto.salvarProjeto(entity);
	}

	public Projeto procurarProjetoId(long projetoId) {
		return cadastroProjeto.procurarProjetoId(projetoId);
	}

    public List<Projeto> listarProjetos() {
        return cadastroProjeto.listarProjetos();
    }

	public List<Tarefa> listarTarefasProjeto(long projetoId) {
		long id = cadastroProjeto.procurarProjetoId(projetoId).getId();
		List<Tarefa> tarefas = negocioTarefa.listarTarefas();
		List<Tarefa> tarefasUser = new ArrayList<>();
		for (Tarefa t : tarefas) {
			if (t.getProjeto().getId() == id) {
				tarefasUser.add(t);
			}
		}
		return tarefasUser;
	}

	public List<TarefaLista> listarTarefasListaProjeto(long projetoId) {
		long id = cadastroProjeto.procurarProjetoId(projetoId).getId();
		List<TarefaLista> tarefas = negocioTarefaLista.listarTarefas();
		List<TarefaLista> tarefasUser = new ArrayList<>();
		for (TarefaLista t : tarefas) {
			if (t.getProjeto().getId() == id) {
				tarefasUser.add(t);
			}
		}
		return tarefasUser;
	}

	public Papel salvarPapel(Papel p) {
		return cadastroPapel.salvarPapel(p);
	}

    public Papel procurarPapelId(long papelId) {
        return cadastroPapel.procurarPapelId(papelId);
    }

	public List<Papel> listarPapeis() {
        return cadastroPapel.listarPapeis();
    }

	public List<Projeto> listarProjetosNoPapel(long idPapel) {
        return cadastroPapel.procurarPapelId(idPapel).getProjetos();
    }

    /*
	 * Cadastro Tarefa
	 */
	
	// métodos de leitura

	public List<Tarefa> listarTarefa(){
		return negocioTarefa.listarTarefas();
	}

	public Tarefa procurarTarefa(String titulo) throws TarefaNaoExisteException {
		return negocioTarefa.procurarTarefa(titulo);
	}

	public Tarefa procurarTarefaId(Long id) throws TarefaNaoExisteException {
		return negocioTarefa.procurarTarefaId(id);
	}

	public Tarefa procurarTarefaUsuario (Usuario user) {
		return negocioTarefa.listarTarefasUsuario(user);
	}

	// métodos de escrita

	public Tarefa salvarTarefa(Tarefa entity){
		return negocioTarefa.salvarTarefa(entity);
	}

	public Tarefa atualizarTarefa(Tarefa entity) throws TarefaNaoExisteException{
		Tarefa salva = negocioTarefa.procurarTarefaId(entity.getId());
		entity.setComentario(salva.getComentario());
		return negocioTarefa.salvarTarefa(entity);
	}

	public void deletarTarefaId(Long id){
		negocioTarefa.deletarTarefaId(id);
	}

	public void deletarTarefa(Tarefa entity){
		negocioTarefa.deletarTarefa(entity);
	}

	/*
	 * Cadastro Tarefa Lista
	 */

	 public List<TarefaLista> listarTarefaLista(){
		return negocioTarefaLista.listarTarefas();
	}

	public TarefaLista procurarTarefaLista(String titulo) throws TarefaNaoExisteException {
		return negocioTarefaLista.procurarTarefa(titulo);
	}

	public TarefaLista procurarTarefaListaId(Long id) throws TarefaNaoExisteException {
		return negocioTarefaLista.procurarTarefaId(id);
	}

	public List<TarefaLista> procurarTarefaListaUsuario (Usuario user) throws UsuarioSemTarefaException {
		return negocioTarefaLista.listarTarefasUsuario(user);
	}

	// métodos de escrita

	public TarefaLista salvarTarefaLista(TarefaLista entity){

		/* List<ItemLista> subtarefas = entity.getSubtarefas();

		for (ItemLista subtarefa : subtarefas) {
			negocioItemLista.salvarItemLista(subtarefa);
		} */

		return negocioTarefaLista.salvarTarefa(entity);
	}

	public void atualizarTarefaLista(TarefaLista entity) throws TarefaNaoExisteException{
		TarefaLista salva = negocioTarefaLista.procurarTarefaId(entity.getId());
		entity.setSubtarefas(salva.getSubtarefas());
		negocioTarefaLista.salvarTarefa(entity);
	}

	public void deletarTarefaListaId(Long id){
		negocioTarefaLista.deletarTarefaId(id);
	}

	public void deletarTarefaLista(TarefaLista entity){
		negocioTarefaLista.deletarTarefa(entity);
	}


	/*
	 * Cadastro Item Lista
	 */

	public ItemLista procurarItemLista(String titulo) {
		return negocioItemLista.procurarItemLista(titulo);
	}

	public ItemLista procurarItemListaId(Long id) {
		return negocioItemLista.procurarItemListaId(id);
	}

	public ItemLista procurarItemChecked(boolean checked) {
		return negocioItemLista.procurarItemChecked(checked);
	}

	public List<ItemLista> listarItensLista() {
		return negocioItemLista.listarItensLista();
	}

	// métodos de escrita

	public void deletarItemListaId(Long id) {
		negocioItemLista.deletarItemListaId(id);
	}

	public void salvarItemLista(ItemLista entity) {
		negocioItemLista.deletarItemLista(entity);
	}

	public void atualizarItemLista(ItemLista entity) {
		negocioItemLista.deletarItemLista(entity);
	}

	public void deletarItemLista(ItemLista entity) {
		negocioItemLista.deletarItemLista(entity);
	}

	/*
	 * Cadastro Resgitro Status
	 */

	public RegistroStatus procurarRegitroId(Long id){
		return negocioRegistroStatus.procurarRegitroId(id);
	}
    
	public RegistroStatus procurarRegistroTarefa(Long tarefaId) throws TarefaNaoExisteException{
		Tarefa tarefa = negocioTarefa.procurarTarefaId(tarefaId);
		return negocioRegistroStatus.procurarRegistroTarefa(tarefa);
	}
 
	public RegistroStatus procurarRegitroStatus(Status status){
		return negocioRegistroStatus.procurarRegitroStatus(status);
	}
 
	public List<RegistroStatus> listarRegistro(){
		return negocioRegistroStatus.listarRegistro();
	}
 
	public void salvarRegistro(RegistroStatus entity){
		negocioRegistroStatus.salvarRegistro(entity);
	}
 
	public void atualizarRegistro(RegistroStatus entity){
		negocioRegistroStatus.salvarRegistro(entity);
	}

	/* Cadastro Status */

	public Object getStatus() {
		return null;
	}

	public void salvarStatus(Status entity){
		negocioStatus.salvarStatus(entity);
	}

	public void deletarStatus(Long id){
		negocioStatus.deletarStatus(id);
	}

	public void adicionarStatus(String nome) throws DoisStatusException{
		negocioStatus.adicionarStatus(nome);
	}

	public Status buscarStatusPorId(Long id) {
        return negocioStatus.buscarStatusPorId(id);
    }

	public List<Status> listarStatus(){
		return negocioStatus.listarStatus();
	}

	/* Cadastro Categoria */

	public void salvarCategoria(Categoria entity){
		negocioCategoria.salvarCategoria(entity);
	}

	public void deletarCategoria(Long id){
		negocioCategoria.deletarCategoria(id);
	}

	public void adicionarCategoria(String nome){
		negocioCategoria.adicionarCategoria(nome);
	}

	public Categoria buscarCategoriaPorId(Long id) {
        return negocioCategoria.buscarCategoriaPorId(id);
    }

	public List<Categoria> listarCategoria(){
		return negocioCategoria.listarCategoria();
	}

	/* Cadastro Comentario */

	public Comentario salvarComentario(Comentario entity){
		return negocioComentario.salvarComentario(entity);
	}

	public void deletarComentario(Long id){
		negocioComentario.deletarComentario(id);
	}
	
	public void adicionarComentario(Comentario comentario){
		negocioComentario.adicionarComentario(comentario);
	}

	public Comentario buscarComentarioPorMensagem(String mensagem) {
        return negocioComentario.buscarComentarioPorMensagem(mensagem);
    }

	public Comentario buscarComentarioPorId(Long id) {
        return negocioComentario.buscarComentarioPorId(id);
    }

	/* Cadastro Lembrete */

	public void salvarLembrete(Lembrete entity){
		negocioLembrete.salvarLembrete(entity);
	}

	public void deletarLembrete(Long id){
		negocioLembrete.deleterLembrete(id);
	}

	public void adicionarLembrete(String titulo){
		negocioLembrete.adicionarLembrete(titulo);
	}

	public Lembrete buscarLembretePorId(Long id) {
        return negocioLembrete.buscarLembretePorId(id);
    }
	
	/* 
	 * Adicionando Chaves Estrangeiras
	 */
	 
	public ItemLista addItemTarefaLista (Long tarefaId, ItemLista item) throws TarefaNaoExisteException {
		TarefaLista lista = negocioTarefaLista.procurarTarefaId(tarefaId);
		item = negocioItemLista.salvarItemLista(item);
		lista.getSubtarefas().add(item);
		negocioTarefaLista.salvarTarefa(lista);
		return item;
	}
	 
	public Usuario addUsuarioTarefa (Long tarefaId, Long userId) throws TarefaNaoExisteException {
		TarefaLista lista = negocioTarefaLista.procurarTarefaId(tarefaId);
		Usuario user = cadastroUsuario.procurarUsuarioId(userId);
		lista.getUser().add(user);
		negocioTarefaLista.salvarTarefa(lista);
		return user;
	}
	 
	public Comentario addComentarioTarefa (Long tarefaId, Comentario coment) throws TarefaNaoExisteException {
		TarefaLista lista = negocioTarefaLista.procurarTarefaId(tarefaId);
		coment = negocioComentario.salvarComentario(coment);
		lista.getComentario().add(coment);
		negocioTarefaLista.salvarTarefa(lista);
		return coment;
	}

	/*
	 * Rotas de lixeira e arquivo de tarefa - tarefa lista
	 * tipo = 1 -> Tarefa
	 * tipo = 0 -> Tarefa Lista
	 */

	 public void enviarTarefaLixeira(Long idTarefa, boolean tipo) throws TarefaNaoExisteException, TarefaExisteLixeiraException {
		if (tipo) {
			Tarefa salva = negocioTarefa.procurarTarefaId(idTarefa);
			if (salva.isExcluido()) {
				throw new TarefaExisteLixeiraException();
			} else {
				salva.setExcluido(true);
				negocioTarefa.salvarTarefa(salva);
			}
		} else {
			TarefaLista salva = negocioTarefaLista.procurarTarefaId(idTarefa);
			if (salva.isExcluido()) {
				throw new TarefaExisteLixeiraException();
			} else {
				salva.setExcluido(true);
				negocioTarefaLista.salvarTarefa(salva);
			}
		}
	}

	public void retirarTarefaLixeira(Long idTarefa, boolean tipo) throws TarefaNaoExisteException {
		if (tipo) {
			Tarefa salva = negocioTarefa.procurarTarefaId(idTarefa);
			if (salva.isExcluido()) {
				salva.setExcluido(false);
				negocioTarefa.salvarTarefa(salva);	
			}
		} else {
			TarefaLista salva = negocioTarefaLista.procurarTarefaId(idTarefa);
			if (salva.isExcluido()) {
				salva.setExcluido(false);
				negocioTarefaLista.salvarTarefa(salva);	
			}
		}
	}

	public void enviarTarefaArquivo(Long idTarefa, boolean tipo) throws TarefaNaoExisteException {
		if (tipo) {
			Tarefa salva = negocioTarefa.procurarTarefaId(idTarefa);
			if (!salva.isArquivado()) {
				salva.setArquivado(true);
				negocioTarefa.salvarTarefa(salva);
			}			
		} else {
			TarefaLista salva = negocioTarefaLista.procurarTarefaId(idTarefa);
			if (!salva.isArquivado()) {
				salva.setArquivado(true);
				negocioTarefaLista.salvarTarefa(salva);
			}
		}
	}

	public void retirarTarefaArquivo(Long idTarefa, boolean tipo) throws TarefaNaoExisteException {
		if (tipo) {
			Tarefa salva = negocioTarefa.procurarTarefaId(idTarefa);
			if (salva.isArquivado()) {
				salva.setArquivado(false);
				negocioTarefa.salvarTarefa(salva);
			}
		} else {
			TarefaLista salva = negocioTarefaLista.procurarTarefaId(idTarefa);
			if (salva.isArquivado()) {
				salva.setArquivado(false);
				negocioTarefaLista.salvarTarefa(salva);
			}
		}
	}

}
