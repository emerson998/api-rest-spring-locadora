package br.com.magna.api.masterlocadora.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.magna.api.masterlocadora.dto.LocadoraDto;
import br.com.magna.api.masterlocadora.service.LocadoraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/locadora")

public class LocadoraController {
	// Locadora
	@Autowired
	private LocadoraService locadoraService;

	@ApiOperation(value = "Retorna Todos os Fornecedores")
	@GetMapping
	public Page<LocadoraDto> list(Pageable pageable) {
		try {
			return locadoraService.buscarTodos(pageable);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "Retorna Todos os Fornecedores Por Cnpj")
	@GetMapping("/{cnpj}")
	public ResponseEntity<LocadoraDto> listLogin(@PathVariable String cnpj) throws NotFoundException {
		try {
			return ResponseEntity.ok(locadoraService.getLogin(cnpj));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		} // Locadora locadora
	}

	@ApiOperation(value = "Salva os Locadora")
	@PostMapping
	public ResponseEntity<LocadoraDto> post(@RequestBody LocadoraDto locadoraDto) {
		try {
			LocadoraDto resultado = locadoraService.salvandoLocadoraDto(locadoraDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
		} catch (Exception ex) {
			ex.getMessage();
			return ResponseEntity.noContent().build();
		}

	}

	@ApiOperation(value = "Altera Clientes")
	@PutMapping("/{cnpj}")
	@Transactional
	public ResponseEntity<LocadoraDto> put(@PathVariable String cnpj, @RequestBody LocadoraDto locadoraDto)
			throws Exception {
		try {
			LocadoraDto locadoraDtoAltera = locadoraService.update(cnpj, locadoraDto);
			return ResponseEntity.ok(locadoraDtoAltera);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Deleta Clientes")
	@DeleteMapping("/{cnpj}")
	@Transactional
	public ResponseEntity<LocadoraDto> deletando(@PathVariable String cnpj) throws NotFoundException {
		try {
			locadoraService.delete(cnpj);
			return ResponseEntity.ok().build();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return null;
	}
}
