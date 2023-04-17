package br.com.ufape.bcc.taskhive.comunicacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufape.bcc.taskhive.negocio.basicas.RegistroStatus;
import br.com.ufape.bcc.taskhive.negocio.cadastro.TarefaNaoExisteException;
import br.com.ufape.bcc.taskhive.negocio.fachada.Fachada;

@CrossOrigin
@RestController
public class RegistroStatusController {
    @Autowired
    private Fachada fachada;

    @PostMapping (value = "/registrostatus")
    public ResponseEntity<String> cadastrarRegistroStatus(@RequestBody RegistroStatus registroStatus) {
		fachada.salvarRegistro(registroStatus);
		return ResponseEntity.ok("Status registrado com sucesso!");
	}

    @GetMapping (value = "/registrostatus/{registroId}")
	public ResponseEntity<RegistroStatus> procurarRegistroId(@PathVariable Long registroId) {
		return ResponseEntity.ok(fachada.procurarRegitroId(registroId));
	}

    @GetMapping (value = "/registrostatus/status/{registroId}")
	public ResponseEntity<RegistroStatus> procurarRegistroStatus(@PathVariable Long registroId) {
		return ResponseEntity.ok(fachada.procurarRegitroId(registroId));
	}

    @GetMapping (value = "/registrostatus/tarefa/{tarefaId}")
	public ResponseEntity<RegistroStatus> procurarRegistroStatusTarefa(@PathVariable Long tarefaId) {
		try {
			return ResponseEntity.ok(fachada.procurarRegistroTarefa(tarefaId));
		} catch (TarefaNaoExisteException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

    @GetMapping (value = "/registrostatus")
	public ResponseEntity<List<RegistroStatus>> carregarRegistroStatuss() {
		return ResponseEntity.ok(fachada.listarRegistro());
	}

    @PatchMapping (value = "/registrostatus/{registroId}")
	public ResponseEntity<String> atualizarRegistroStatuss(@RequestBody RegistroStatus registrostatus, @PathVariable Long registroId) {
        // AJUSTAR
        fachada.atualizarRegistro(registrostatus);
		return ResponseEntity.ok("Registro de Status Atualizada com sucesso");
	}
    
}
