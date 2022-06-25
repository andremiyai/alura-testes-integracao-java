import br.com.alura.leilao.dao.UsuarioDao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsuarioDaoTest {

    private UsuarioDao dao;

    private EntityManager em;

    @Test
    public void deveriaEncontrarUsuarioCadastrado(){
        criarUsuario();
        Usuario usuario2 = this.dao.buscarPorUsername("fulano");
        assertNotNull(usuario2);
    }

    @Test
    public void NaoDeveriaEncontrarUsuarioCadastrado(){
        criarUsuario();
        assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
    }

    @Test
    public void deveriaRemoverUsuario(){
        Usuario usuario = criarUsuario();
        dao.deletar(usuario);
        assertThrows(NoResultException.class, ()-> this.dao.buscarPorUsername("fulano"));
    }

    @BeforeEach
    private void inicializar(){
        em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
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
