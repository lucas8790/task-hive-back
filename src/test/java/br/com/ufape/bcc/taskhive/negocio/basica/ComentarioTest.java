package br.com.ufape.bcc.taskhive.negocio.basica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.ufape.bcc.taskhive.negocio.cadastro.InterfaceCadastroComentario;
import br.com.ufape.bcc.taskhive.negocio.basicas.Comentario;
import br.com.ufape.bcc.taskhive.negocio.cadastro.CadastroComentario;

@SpringBootTest
public class ComentarioTest{

    @Autowired
    private CadastroComentario cadastroComentario;

    @Test
    public void testAdicionarComentario() {
        Comentario comentario = new Comentario("Este é um comentário de teste");
        cadastroComentario.salvarComentario(comentario);
        Comentario comentarioSalvo = cadastroComentario.buscarComentarioPorId(comentario.getId());
        assertNotNull(comentarioSalvo);
        assertEquals(comentario.getMensagem(), comentarioSalvo.getMensagem());
    }

    @Test
    void testeSalvarComentario() {
        String mensagem = "Teste de atualização de comentário";
        Comentario comentario = new Comentario(mensagem);
        cadastroComentario.salvarComentario(comentario);
        Long id = comentario.getId();
        comentario.setMensagem("Comentário atualizado");
        cadastroComentario.salvarComentario(comentario);
        comentario = cadastroComentario.buscarComentarioPorId(id);
        Assertions.assertEquals("Comentário atualizado", comentario.getMensagem());
    }
}