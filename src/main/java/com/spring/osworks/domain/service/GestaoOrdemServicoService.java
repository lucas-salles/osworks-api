package com.spring.osworks.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.osworks.domain.entity.Cliente;
import com.spring.osworks.domain.entity.Comentario;
import com.spring.osworks.domain.entity.OrdemServico;
import com.spring.osworks.domain.entity.StatusOrdemServico;
import com.spring.osworks.domain.exception.DomainException;
import com.spring.osworks.domain.exception.EntityNotFoundException;
import com.spring.osworks.domain.repository.ClienteRepository;
import com.spring.osworks.domain.repository.ComentarioRepository;
import com.spring.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Transactional
	public OrdemServico save(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new DomainException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	public void cancelar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.cancelar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario addComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada"));
	}
	
	public Optional<OrdemServico> findById(Long id) {
		return ordemServicoRepository.findById(id);
	}

	public List<OrdemServico> findAll() {
		return ordemServicoRepository.findAll();
	}

	@Transactional
	public void deleteById(Long id) {
		ordemServicoRepository.deleteById(id);
	}
	
	public Boolean existsById(Long id) {
		return ordemServicoRepository.existsById(id);
	}
}
