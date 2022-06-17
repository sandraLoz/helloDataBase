package es.opplus.helloDataBase.service;

import java.util.List;

import es.opplus.helloDataBase.dto.UsuarioDto;

public interface UsuarioService {

	List<UsuarioDto> findAll();

	UsuarioDto findUsuarioById(Long userId);

	UsuarioDto createUsuario(UsuarioDto userDto);

	Long deleteUsuarioById(Long userId);
	
	UsuarioDto updateUsuarioById(Long userId, UsuarioDto user);
}
