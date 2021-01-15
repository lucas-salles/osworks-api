package com.spring.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.osworks.domain.entity.Cliente;
import com.spring.osworks.domain.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public List<Cliente> index() {
		return clienteService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable("id") Long id) {
		Optional<Cliente> opCliente = clienteService.findById(id);
		
		if(opCliente.isPresent()) {
			return ResponseEntity.ok(opCliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente store(@Valid @RequestBody Cliente cliente) {
		return clienteService.save(cliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update(@PathVariable("id") Long id, @Valid @RequestBody Cliente cliente) {
		if(!clienteService.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(id);
		cliente = clienteService.save(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if(!clienteService.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		clienteService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
