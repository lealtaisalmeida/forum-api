package br.com.alura.forumapi.repository;

import br.com.alura.forumapi.model.Curso;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void deveCarregarCursoAoBuscarPorNomeExistente() {

        String nomeCurso = "Spring Boot";

        Curso cursoSpringBoot = new Curso();
        cursoSpringBoot.setNome(nomeCurso);
        cursoSpringBoot.setCategoria("Programação");
        em.persist(cursoSpringBoot);

        Curso curso = cursoRepository.findByNome(nomeCurso);
        assertNotNull(curso);
        assertEquals(nomeCurso, curso.getNome());

    }

    @Test
    public void naoDeveCarregarCursoAoBuscarPorNomeInexistente() {

        String nomeCurso = "XSLT 4 noobs";
        Curso curso = cursoRepository.findByNome(nomeCurso);
        assertNull(curso);

    }

}