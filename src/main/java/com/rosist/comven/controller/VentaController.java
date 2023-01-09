package com.rosist.comven.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rosist.comven.exception.ModelNotFoundException;
import com.rosist.comven.model.Venta;
import com.rosist.comven.repo.IVentaRepo;
import com.rosist.comven.service.IVentaService;

@RestController
@RequestMapping("/venta")
public class VentaController {

	@Autowired
	private IVentaService service;
	@Autowired
	private IVentaRepo repo;

	private static Logger logger = LoggerFactory.getLogger(VentaController.class);

	@GetMapping
	public ResponseEntity<List<Venta>> listar() throws Exception {
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}

	@GetMapping("/pageable")
	public ResponseEntity<Map<String, Object>> getAllVenta(
			@RequestParam(value = "idEmpresa", defaultValue = "0") Integer idEmpresa,
			@RequestParam(value = "anno", defaultValue = "0") Integer anno,
			@RequestParam(value = "mes", defaultValue = "0") Integer mes,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {

		anno = (anno == null ? LocalDate.now().getYear() : anno);
		mes = (mes == null ? LocalDate.now().getMonthValue() : mes);
		
		List<Venta> content;
		Integer totalReg = repo.listaVentasCount(idEmpresa, anno, mes);
		content = service.listaVentas(idEmpresa, anno, mes, page, size);
		
		long totalPages = (size > 0 ? (totalReg - 1) / size + 1 : 0);
		
		Map<String, Object> response = new HashMap<>();
		response.put("content", content); // compra
		response.put("number", page);
		response.put("totalElements", totalReg);
		response.put("totalPages", totalPages);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Venta> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Venta obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

//	@GetMapping("/ruc/{ruc}")
//	public ResponseEntity<Venta> listarPorRuc(@PathVariable("ruc") String ruc) throws Exception{
//		Venta obj = service.buscaPorRUC(ruc);
////		if(obj == null) {
////			throw new ModelNotFoundException("ID NO ENCONTRADO " + ruc);
////		}
//		return new ResponseEntity<>(obj, HttpStatus.OK);		
//	}

	@PostMapping
	public ResponseEntity<Venta> registrar(@Valid @RequestBody Venta venta) throws Exception {
//		logger.info("ventacontroller.registrar");
//		String cError = "";
//
//		validate(venta, cError);
//		if (!cError.isEmpty()) {
//			throw new ModelNotFoundException(cError);
//		}
		Venta obj = service.registraTransaccion(venta);
		// localhost:8080/medicos/1
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdVenta())
				.toUri();
		return ResponseEntity.created(location).build();
//		return new ResponseEntity<>(service.registrar(paciente), HttpStatus.CREATED);		// 201
	}

	private void validate(@Valid Venta venta, String cError) {
		if (venta == null) {
			cError += "Venta no encontrada ";
		}
		/* validando periodo */
		Integer anno = venta.getAnno();
		Integer mes = venta.getMes();
		String cPeriodo = venta.getPeriodo();
		String perInformado = String.format("%1$04d", anno) + String.format("%1$02d", mes);
		String cEstado = venta.getEstado();
		LocalDate fFecha = venta.getFecha();

		if (!(mes >= 1 && mes <= 12)) {
			cError += "Mes de Periodo Errado.";
		}
		if (!(cPeriodo.compareTo(perInformado) <= 0)) {
			cError += "Periodo no debe ser mayor al periodo informado.";
		}
		if (cPeriodo.compareTo(perInformado) == 0) {
			if (!(cEstado.equals("0") || cEstado.equals("1"))) {
				cError += "Periodo no Coincide con el estado.";
			}
		} else if (cPeriodo.compareTo(perInformado) <= 0) {
			if (!(cEstado.equals("6") || cEstado.equals("7") || cEstado.equals("9"))) {
				cError += "Periodo no Coincide con el estado.";
			}
		}
		/* validando fecha */
		String _fecha = String.format("%1$04d", fFecha.getYear()) + String.format("%1$02d", fFecha.getMonth()) + "00";
		String _fechaPago = String.format("%1$04d", venta.getFecpag().getYear())
				+ String.format("%1$02d", venta.getFecpag().getMonth()) + "00";
		Integer mesAnt = venta.getMes();
		Integer anoAnt = venta.getAnno() - 1;
		String perAnt = String.format("%1$04d", anoAnt) + String.format("%1$02d", mesAnt) + "00";

		if (_fecha.compareTo(perInformado) <= 0) {
			if (perAnt.compareTo(_fecha) >= 0) {
				if (!cEstado.equals("6")) {
					cError += "Estado no coincide con Fecha de emision.";
				}
			} else {
				if (!cEstado.equals("7")) {
					cError += "Estado no coincide con Fecha de emision.";
				}
			}
		} else {
			cError += "Fecha no es menor al Periodo Informado.";
		}
		/* validando fecha de Pago */
		if (venta.getTipcom() == "14") {
			if (venta.getFecpag() == null) {
				cError += "Fecha de Pago no debe ser estar vacia.";
			}
		} else {
			if (venta.getFecpag() != null) {
				if (_fechaPago.compareTo(perInformado) > 0)
					cError += "Fecha de Pago errada.";
				if (_fecha.compareTo(_fechaPago) > 0) {
					cError += "Fecha de Pago errada.";
				}
			}
		}
		/* validando tipo de comprobante de Pago */
		if (venta.getTipcom() == "91" || venta.getTipcom() == "97"
				|| venta.getTipcom() == "98") {
			{
				cError += "Tipo de Comprobante Errado.";
			}
		}
		/* validando serie y comprobante */
		if (venta.getTipcom() == "03" || venta.getTipcom() == "04") {
			if (venta.getSerie().length() != 4) {
				cError += "Longitud de Comprobante de Pago Errado.";
			}
			if (venta.getCodcom().length() != 8) {
				cError += "Longitud de Comprobante de Pago Errado.";
			}
		}
		/* validando comprobante final */
		if (venta.getCodcomFinal().isEmpty()) {
			if (!(venta.getTipcom() == "00" || venta.getTipcom() == "03"
					|| venta.getTipcom() == "14")) {
				cError += "Error en codigo de comprobante final.";
			}

		}
		if (venta.getCliente().getTipdoc() == "06") {
			cError += validaRuc(venta.getCliente().getDoccli());
			if (venta.getCliente().getNombre().isBlank()) {
				cError += "Error en Nombre del Proveedor.";
			}
		}
		double _suma = venta.getBasimp() + venta.getIgv() + venta.getIcbp() + venta.getOtros();
		if (_suma != venta.getImporteTotal()) {
			cError += "Importes mal calculados.";
		}
		/* validando tipo de comprobante modificado */
		if (venta.getTipcom().equals("07")||venta.getTipcom().equals("08")) {
			if(venta.getTipcomModificado().isBlank()) cError = "Tipo de Comprobante Modificado vacia";
			if(venta.getSerieComModificado().isBlank()) cError = "Serie Comprobante Modificado vacia";
			if(venta.getCodigoComModificado().isBlank()) cError = "Codigo Comprobante Modificado vacia";
			if(venta.getFeccomModificado()==null) cError = "Codigo Comprobante Modificado vacia";
		}
	}

	private String validaRuc(String ruc) {
		String sRpta = "";
		if (ruc == null) {
			sRpta = "ruc no definido";
		} else if (ruc.trim().length() < 11) {
			sRpta = "ruc debe tener 11 digitos";
		}
		int[] digito = new int[11];
		double suma = 0.0, resto = 0.0, res = 0.0;

		if (sRpta.isEmpty()) {
			try {
				digito[0] = Integer.valueOf(ruc.substring(0, 1));
				digito[1] = Integer.valueOf(ruc.substring(1, 2));
				digito[2] = Integer.valueOf(ruc.substring(2, 3));
				digito[3] = Integer.valueOf(ruc.substring(3, 4));
				digito[4] = Integer.valueOf(ruc.substring(4, 5));
				digito[5] = Integer.valueOf(ruc.substring(5, 6));
				digito[6] = Integer.valueOf(ruc.substring(6, 7));
				digito[7] = Integer.valueOf(ruc.substring(7, 8));
				digito[8] = Integer.valueOf(ruc.substring(8, 9));
				digito[9] = Integer.valueOf(ruc.substring(9, 10));
				digito[10] = Integer.valueOf(ruc.substring(10, 11));
				suma = digito[0] * 5 + digito[1] * 4 + digito[2] * 3 + digito[3] * 2 + digito[4] * 7 + digito[5] * 6
						+ digito[6] * 5 + digito[7] * 4 + digito[8] * 3 + digito[9] * 2;
				resto = suma % 11;
				res = 11 - resto;
				if (res >= 10) {
					res -= 10;
				}
				if (res == digito[10]) {
					sRpta = "ok";
				} else {
					sRpta = "el ruc no es valido";
				}
			} catch (Exception ex) {
				sRpta = "error de definicion de ruc";
//              System.out.println("error de definicion de ruc ");
//              ex.getMessage();
			}
		}
		return sRpta;
	}

	@PutMapping
	public ResponseEntity<Venta> modificar(@Valid @RequestBody Venta venta) throws Exception {
//		logger.info("midificar...venta,.> " + venta);
		String cError = "";

		validate(venta, cError);
		if (!cError.isEmpty()) {
			throw new ModelNotFoundException(cError);
		}
		return new ResponseEntity<>(service.modificaTransaccion(venta), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<Venta> listarHateoasPorId(@PathVariable("id") Integer id) throws Exception {
		Venta obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO " + id);
		}

		EntityModel<Venta> recurso = EntityModel.of(obj);
		// localhost:8080/medicos/hateoas/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		recurso.add(link1.withRel("Paciente-recurso1"));
		recurso.add(link2.withRel("Paciente-recurso2"));

		return recurso;

	}

}
