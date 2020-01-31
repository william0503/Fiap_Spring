package br.com.fiap.cervejaria.controller;

import br.com.fiap.cervejaria.dto.CervejaDTO;
import br.com.fiap.cervejaria.dto.CreateCervejaDTO;
import br.com.fiap.cervejaria.dto.PrecoCervejaDTO;
import br.com.fiap.cervejaria.dto.Tipo;
import br.com.fiap.cervejaria.service.CervejaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cervejas")
public class CervejaController {

    private CervejaService service;

    public CervejaController(CervejaService cervejaService) {
        this.service = cervejaService;
    }

    @GetMapping()
    public List<CervejaDTO> getAll(@RequestParam(required = false) Tipo tipo) {
        return service.findAll(tipo);
    }

    @GetMapping("pagina")
    public Page<CervejaDTO> getAll(@RequestParam()Integer size, @RequestParam()Integer page, @RequestParam(required = false) Tipo tipo) {
        return service.findAll(size, page, tipo);
    }

    @GetMapping("{id}")
    public CervejaDTO findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CervejaDTO create(@RequestBody @Valid CreateCervejaDTO createCervejaDTO) {
        return service.create(createCervejaDTO);
    }

    @PutMapping("{id}")
    public CervejaDTO update(@PathVariable Integer id, @RequestBody CreateCervejaDTO createCervejaDTO) {
        return service.update(id, createCervejaDTO);
    }

    @PatchMapping("{id}")
    public CervejaDTO update(@PathVariable Integer id, @RequestBody PrecoCervejaDTO precoCervejaDTO) {
      return service.update(id, precoCervejaDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
