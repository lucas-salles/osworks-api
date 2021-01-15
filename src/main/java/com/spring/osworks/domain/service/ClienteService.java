package com.spring.osworks.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.osworks.domain.entity.Cliente;
import com.spring.osworks.domain.exception.DomainException;
import com.spring.osworks.domain.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public Cliente save(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new DomainException("Já existe um cliente cadastrado com este e-mail.");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Transactional
	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
	}
	
	public Boolean existsById(Long id) {
		return clienteRepository.existsById(id);
	}
}
