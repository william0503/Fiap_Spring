package br.com.fiap.cervejaria.service.impl;

import br.com.fiap.cervejaria.dto.CervejaDTO;
import br.com.fiap.cervejaria.dto.CreateCervejaDTO;
import br.com.fiap.cervejaria.dto.PrecoCervejaDTO;
import br.com.fiap.cervejaria.dto.Tipo;
import br.com.fiap.cervejaria.entity.Cerveja;
import br.com.fiap.cervejaria.repository.CervejaRepository;
import br.com.fiap.cervejaria.service.CervejaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.fiap.cervejaria.dto.Tipo.*;

@Service
public class CervejaServiceImpl implements CervejaService {


    private CervejaRepository cervejaRepository;

    public CervejaServiceImpl(CervejaRepository cervejaRepository) {
        this.cervejaRepository = cervejaRepository;
    }

    @Override
    public List<CervejaDTO> findAll(Tipo tipo) {
        if(tipo == null){
            return cervejaRepository.findAll()
                    .stream()
                    .map(CervejaDTO::new)
                    .collect(Collectors.toList());
        }

        return cervejaRepository.findAllByTipo(tipo)
                .stream()
                .map(CervejaDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public Page<CervejaDTO> findAll(Integer size, Integer page, Tipo tipo) {
        Pageable pageable = PageRequest.of(page, size);
        if(tipo == null){
            return cervejaRepository.findAll(pageable)
                    .map(CervejaDTO::new);
        }

        return cervejaRepository.findAllByTipo(pageable, tipo)
                .map(CervejaDTO::new);
    }

    @Override
    public CervejaDTO findById(Integer id) {
        return cervejaRepository.findById(id)
                .map(CervejaDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public CervejaDTO create(CreateCervejaDTO createCervejaDTO) {
        Cerveja cerveja = new Cerveja(createCervejaDTO);
        Cerveja savedCerveja = cervejaRepository.save(cerveja);

        return new CervejaDTO(savedCerveja);
    }

    @Override
    public CervejaDTO update(Integer id, CreateCervejaDTO createCervejaDTO) {
        Optional<Cerveja> cervejaOp = cervejaRepository.findById(id);

        if(!cervejaOp.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Cerveja cerveja = cervejaOp.get();
        cerveja.setMarca(createCervejaDTO.getMarca());
        cerveja.setPreco(createCervejaDTO.getPreco());
        cerveja.setTeorAlcoolico(createCervejaDTO.getTeorAlcoolico());
        cerveja.setDataLancamento(createCervejaDTO.getDataLancamento());
        cerveja.setTipo(createCervejaDTO.getTipo());

        Cerveja savedCerveja = cervejaRepository.save(cerveja);

        return new CervejaDTO(savedCerveja);

    }

    @Override
    public CervejaDTO update(Integer id, PrecoCervejaDTO precoCervejaDTO) {
        Optional<Cerveja> cervejaOptional = cervejaRepository.findById(id);

        if(!cervejaOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Cerveja cerveja = cervejaOptional.get();
        cerveja.setPreco(precoCervejaDTO.getPreco());

        Cerveja savedCerveja = cervejaRepository.save(cerveja);

        return new CervejaDTO(savedCerveja);
    }

    @Override
    public void delete(Integer id) {
        Optional<Cerveja> cervejaOptional = cervejaRepository.findById(id);

        if(!cervejaOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Cerveja cerveja = cervejaOptional.get();

        cervejaRepository.delete(cerveja);
    }
}
