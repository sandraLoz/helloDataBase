package es.opplus.helloDataBase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.opplus.helloDataBase.domain.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

}
