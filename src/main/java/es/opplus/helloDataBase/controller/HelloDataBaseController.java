package es.opplus.helloDataBase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.opplus.helloDataBase.dto.UsuarioDto;
import es.opplus.helloDataBase.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Hello DataBase API")
@RestController
@RequestMapping("/Usuario")
public class HelloDataBaseController {

	
	private Logger logger = LoggerFactory.getLogger(HelloDataBaseController.class);


	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(path = "/")
	@Operation(summary = "Hello", tags = "Saludo params")
	public String greeting(@RequestParam(value = "name", defaultValue = "world") String name) {
		return String.format("hello %s", name);
	}

	@PostMapping("/")
	@Operation(summary = "Create Usuario", tags = "Create Usuario")	
	public ResponseEntity<UsuarioDto> createUsuario(@RequestBody UsuarioDto usuarioCreate) {
		try {
			return new ResponseEntity<UsuarioDto>(this.usuarioService.createUsuario(usuarioCreate), HttpStatus.CREATED);
		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Usuario", tags = "Delete Usuario")
	public ResponseEntity<String> deleteUsuarioById(@PathVariable(required = true, name = "id") Long UsuarioId) {
		try {
			if (this.usuarioService.deleteUsuarioById(UsuarioId) > 0)
				return new ResponseEntity<>("Deleted Usuario by UsuarioId " + UsuarioId, HttpStatus.OK);
			else
				return new ResponseEntity<>("Usuario Delete Error", HttpStatus.BAD_REQUEST);

		} catch (ResponseStatusException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/")
	@Operation(summary = "Find all Usuarios", tags = "Find all Usuarios")
	public ResponseEntity<List<UsuarioDto>> findAll() {
		try {
			return ResponseEntity.ok(this.usuarioService.findAll());

		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}

	}

	@GetMapping("/{id}")
	@Operation(summary = "Find Usuario By id", tags = "Find Usuario By id")	
	public ResponseEntity<UsuarioDto> findUsuarioById(@PathVariable(required = true, name = "id") Long id)
			throws ResponseStatusException {

		return ResponseEntity.ok(this.usuarioService.findUsuarioById(id));

	}

	@PutMapping("/{id}")
	@Operation(summary = "Usuario Update", tags = "Usuario Update")	
	public ResponseEntity<UsuarioDto> updateUsuarioById(@RequestBody UsuarioDto UsuarioUpd,
			@PathVariable(required = true, name = "id") Long id) {

		return ResponseEntity.ok(this.usuarioService.updateUsuarioById(id, UsuarioUpd));

	}
	
}
