package br.com.alura.forumapi.controller;

import br.com.alura.forumapi.controller.dto.TopicoDTO;
import br.com.alura.forumapi.controller.dto.TopicoDetalhadoDTO;
import br.com.alura.forumapi.controller.form.AtualizaTopicoForm;
import br.com.alura.forumapi.controller.form.TopicoForm;
import br.com.alura.forumapi.model.Topico;
import br.com.alura.forumapi.repository.CursoRepository;
import br.com.alura.forumapi.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Cacheable(value = "listaTopicos")
    public Page<TopicoDTO> lista(
            @RequestParam(required = false) String nomeCurso,
            @PageableDefault(page = 0, size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable paginacao) {

        Page<Topico> topicos = null;

        if(nomeCurso == null)
            topicos = topicoRepository.findAll(paginacao);
        else
            topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);

        return TopicoDTO.converter(topicos);

    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {

        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));

    }

    @GetMapping("/{topicoId}")
    public ResponseEntity<TopicoDetalhadoDTO> detalhar(@PathVariable Long topicoId) {

        Optional<Topico> topico = topicoRepository.findById(topicoId);

        if(topico.isPresent())
            return ResponseEntity.ok(new TopicoDetalhadoDTO(topico.get()));

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{topicoId}")
    @Transactional
    @CacheEvict(value = "listaTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long topicoId, @RequestBody AtualizaTopicoForm topicoForm) {

        Optional<Topico> optional = topicoRepository.findById(topicoId);

        if(optional.isPresent()) {
            Topico topico = topicoForm.atualizar(optional.get());
            return ResponseEntity.ok(new TopicoDTO(optional.get()));
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{topicoId}")
    @Transactional
    @CacheEvict(value = "listaTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable Long topicoId) {

        Optional<Topico> optional = topicoRepository.findById(topicoId);

        if(optional.isPresent()) {
            topicoRepository.deleteById(topicoId);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }

}
