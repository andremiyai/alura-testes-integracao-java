import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LeilaoDaoTest {

    private LeilaoDao dao;

    private EntityManager em;

    @Test
    public void deveriaCadastrarUmLeilao(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@email.com")
                .comSenha("12345678")
                .criar();

        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Mochila")
                .comValorInicial("500")
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .criar();

        leilao = dao.salvar(leilao);

        Leilao leilaoPesquisado = dao.buscarPorId(leilao.getId());
        assertNotNull(leilaoPesquisado);
    }

    @Test
    public void deveriaAtualizarUmLeilao(){
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@email.com")
                .comSenha("12345678")
                .criar();

        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Mochila")
                .comValorInicial("500")
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .criar();

        leilao = dao.salvar(leilao);

        leilao.setNome("Celular");
        leilao.setValorInicial(new BigDecimal("80"));

        Leilao leilaoPesquisado = dao.buscarPorId(leilao.getId());
        assertEquals(new BigDecimal("80"),leilaoPesquisado.getValorInicial());
        assertEquals("Celular",leilaoPesquisado.getNome());
    }


    @BeforeEach
    private void inicializar(){
        em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    private void finalizar(){
        em.getTransaction().rollback();
    }

    private Usuario criarUsuario(){
        Usuario usuario = new Usuario("fulano", "teste@teste.com","12345");
        em.persist(usuario);
        return usuario;
    }
}
