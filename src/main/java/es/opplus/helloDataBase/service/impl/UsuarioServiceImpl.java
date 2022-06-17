package es.opplus.helloDataBase.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.opplus.helloDataBase.domain.UsuarioEntity;
import es.opplus.helloDataBase.dto.UsuarioDto;
import es.opplus.helloDataBase.mapper.UsuarioMapper;
import es.opplus.helloDataBase.repository.UsuarioRepository;
import es.opplus.helloDataBase.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioRepository userRepository;

	private UsuarioMapper userMapper = new UsuarioMapper();

	@Override
	public List<UsuarioDto> findAll() {
		this.logger.info("Find All Users");
		return userMapper.toDto((List<UsuarioEntity>) this.userRepository.findAll());
	}

	@Override
	public UsuarioDto findUsuarioById(Long userId) {
		this.logger.info("Find User By Id {}", userId);
		Optional<UsuarioEntity> result = this.userRepository.findById(userId);
		if (result.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found", userId));
		else
			return userMapper.toDto(result.get());
	}

	@Override
	public UsuarioDto createUsuario(UsuarioDto userDto) {
		this.logger.info("Create User {}", userDto);

		UsuarioEntity newUser = userMapper.toEntity(userDto);
		newUser = this.userRepository.save(newUser);
		this.logger.info("Created User with id {}", newUser.getId());
		return userMapper.toDto(newUser);

	}

	@Override
	public Long deleteUsuarioById(Long userId) {
		this.logger.info("Delete User by userId {}", userId);
		Optional<UsuarioEntity> userOrig = this.userRepository.findById(userId);
		if (userOrig.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found", userId));
		else {
			this.logger.info("Deleted User by userId {}", userId);
			this.userRepository.deleteById(userId);
			return Long.valueOf(1);
		}
	}

	@Override
	public UsuarioDto updateUsuarioById(Long userId, UsuarioDto user) {
		this.logger.info("Update User by userId {} with {}", userId, user);

		// Recuperar el user por el id especificado
		Optional<UsuarioEntity> userOrig = this.userRepository.findById(userId);
		if (userOrig.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found", userId));
		else {
			UsuarioEntity userUpd = userMapper.toEntity(user);
			if (userUpd.getNombre() == null || userUpd.getNombre().isBlank())
				userUpd.setNombre(userOrig.get().getNombre());
			if (userUpd.getApellido1() == null || userUpd.getApellido1().isBlank())
				userUpd.setApellido1(userOrig.get().getApellido1());
			if (userUpd.getApellido2() == null || userUpd.getApellido2().isBlank())
				userUpd.setApellido2(userOrig.get().getApellido2());

			userUpd.setId(userId);
			try {
				return userMapper.toDto(this.userRepository.save(userUpd));
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
						String.format("User %s not updated", userId));
			}

		}

	}
}
