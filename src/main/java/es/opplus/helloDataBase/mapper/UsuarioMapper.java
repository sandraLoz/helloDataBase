package es.opplus.helloDataBase.mapper;

import java.util.List;
import java.util.stream.Collectors;

import es.opplus.helloDataBase.domain.UsuarioEntity;
import es.opplus.helloDataBase.dto.UsuarioDto;


public class UsuarioMapper implements EntityMapper<UsuarioDto, UsuarioEntity> {

	@Override
	public UsuarioEntity toEntity(UsuarioDto dto) {
		UsuarioEntity entity = new UsuarioEntity();
		entity.setNombre(dto.getNombre());
		entity.setApellido1(dto.getApellido1());
		entity.setApellido2(dto.getApellido2());
		return entity;
	}

	@Override
	public UsuarioDto toDto(UsuarioEntity entity) {
		return new UsuarioDto(entity.getNombre(), entity.getApellido1(), entity.getApellido2());
	}

	@Override
	public List<UsuarioEntity> toEntity(List<UsuarioDto> dtoList) {

		return dtoList.stream().map(d -> new UsuarioEntity(d.getNombre(), d.getApellido1(), d.getApellido2()))
				.collect(Collectors.toList());

	}

	@Override
	public List<UsuarioDto> toDto(List<UsuarioEntity> entityList) {
		return entityList.stream().map(e -> new UsuarioDto(e.getNombre(), e.getApellido1(), e.getApellido2()))
				.collect(Collectors.toList());
	}

}
