package com.spring.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.osworks.domain.entity.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
