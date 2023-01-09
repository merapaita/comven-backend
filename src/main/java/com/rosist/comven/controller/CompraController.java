package com.rosist.comven.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.rosist.comven.model.Compra;
import com.rosist.comven.repo.ICompraRepo;
import com.rosist.comven.service.ICompraService;

@RestController
@RequestMapping("/compra")
public class CompraController {

	@Autowired
	private ICompraService service;
	@Autowired
	private ICompraRepo repo;

	private static Logger logger = LoggerFactory.getLogger(CompraController.class);

	@GetMapping
	public ResponseEntity<List<Compra>> listar() throws Exception {
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}

	@GetMapping("/pageable")
	public ResponseEntity<Map<String, Object>> getAllCompra(
			@RequestParam(value = "idEmpresa", defaultValue = "0") Integer idEmpresa,
			@RequestParam(value = "anno", defaultValue = "0") Integer anno,
			@RequestParam(value = "mes", defaultValue = "0") Integer mes,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {

		anno = (anno == null ? LocalDate.now().getYear() : anno);
		mes = (mes == null ? LocalDate.now().getMonthValue() : mes);
		
		List<Compra> content;
		Integer totalReg = repo.listaComprasCount(idEmpresa, anno, mes);
		content = service.listaCompras(idEmpresa, anno, mes, page, size);
		
		long totalPages = (size > 0 ? (totalReg - 1) / size + 1 : 0);
		
		Map<String, Object> response = new HashMap<>();
		response.put("content", content); // compra
		response.put("number", page);
		response.put("totalElements", totalReg);
		response.put("totalPages", totalPages);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Compra> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Compra obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO " + id);
		}

		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

//	@GetMapping("/ruc/{ruc}")
//	public ResponseEntity<Compra> listarPorRuc(@PathVariable("ruc") String ruc) throws Exception{
//		Compra obj = service.buscaPorRUC(ruc);
////		if(obj == null) {
////			throw new ModelNotFoundException("ID NO ENCONTRADO " + ruc);
////		}
//		return new ResponseEntity<>(obj, HttpStatus.OK);		
//	}

	@PostMapping
	public ResponseEntity<Compra> registrar(@Valid @RequestBody Compra compra) throws Exception {
//		logger.info("compracontroller.registrar");
		String cError = "";

		cError = validate(compra);
		logger.info("cError: " + cError);
		if (!cError.isBlank()) {
			throw new ModelNotFoundException(cError);
		}
		
		Compra obj = service.registraTransaccion(compra);
		// localhost:8080/medicos/1
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdCompra())
				.toUri();
		return ResponseEntity.created(location).build();
//		return new ResponseEntity<>(service.registrar(paciente), HttpStatus.CREATED);		// 201
	}

	private String validate(Compra compra) {
		String cError = "";
		if (compra == null) {
			cError += "Compra no encontrada ";
		}
		/* validando periodo */
		Integer anno = compra.getAnno();
		Integer mes = compra.getMes();
		String perInformado = String.format("%1$04d", anno) + String.format("%1$02d", mes) + "00";
		String periodo = compra.getPeriodo();
		String cEstado = compra.getEstado();
		LocalDate fFecha = compra.getFecha();

		if (!(mes >= 1 && mes <= 12)) {
			cError += "Mes de Periodo Errado.";
		}
		logger.info("periodo:" + periodo + " perInformado:" + perInformado);
		if ((periodo.compareTo(perInformado) > 0)) {
			cError += "Periodo no debe ser mayor al periodo informado.";
		}
		/* validando fecha */
		String _fechaInformada = String.format("%1$04d", fFecha.getYear())
				+ String.format("%1$02d", fFecha.getMonthValue()) + "00";
		
		String _fechaPago = null;
		if (compra.getFechaPago()!=null) {
			_fechaPago = String.format("%1$04d", compra.getFechaPago().getYear())
					+ String.format("%1$02d", compra.getFechaPago().getMonthValue()) + "00";
		}
		Integer mesAnt = compra.getMes();
		Integer anoAnt = compra.getAnno() - 1;
		String perAnt = String.format("%1$04d", anoAnt) + String.format("%1$02d", mesAnt) + "00";

		logger.info("_fechaInformada:" + _fechaInformada + " perInformado:" + perInformado);
		if (_fechaInformada.compareTo(perInformado) > 0) {
			cError += "Fecha errdada fuera del periodo informado.";
		} else if (_fechaInformada.compareTo(perInformado) == 0) {
			if (!(cEstado.equals("0") || cEstado.equals("1"))) {
				cError += "Estado no Corresponde con fecha informada.";
			}
		} else if (_fechaInformada.compareTo(perInformado) < 0) {
			logger.info("perAnt:" + perAnt + " _fechaInformada:" + _fechaInformada);
			if (perAnt.compareTo(_fechaInformada) <= 0) {
				if (!cEstado.equals("6")) {
					cError += "Estado no coincide con Fecha de emision.";
				}
			} else {
				if (!cEstado.equals("7")) {
					cError += "Estado no coincide con Fecha de emision.";
				}
			}
		}

		/* validando fecha y fecha de pago */
		if (compra.getTipoComprobante() == "14") {
			if (compra.getFechaPago() == null) {
				cError += "Fecha de Pago no debe ser estar vacia.";
			}
		}
		if (compra.getFechaPago() != null) {
			if (!(compra.getFecha().compareTo(compra.getFechaPago()) <= 0)) {
				cError += "Fecha de Pago no debe ser menor a fecha de emision.";
			}
		}

		/* validando tipo de comprobante de Pago */
		if (compra.getTipoComprobante() == "91" || compra.getTipoComprobante() == "97"
				|| compra.getTipoComprobante() == "98") {
			{
				cError += "Tipo de Comprobante Errado.";
			}
		}
		/* validando serie y comprobante */
		if (compra.getTipoComprobante() == "01" || compra.getTipoComprobante() == "03"
				|| compra.getTipoComprobante() == "07" || compra.getTipoComprobante() == "08") {
			if (compra.getSerie().length() != 4) {
				cError += "Longitud de Comprobante de Pago Errado.";
			}
			if (!(compra.getCodcom().length() == 7 || compra.getCodcom().length() == 8)) {
				cError += "Longitud de Comprobante de Pago Errado.";
			}
			if ((compra.getTipoComprobante() == "01" || compra.getTipoComprobante() == "07"
					|| compra.getTipoComprobante() == "08")
					&& !(compra.getCodcom().substring(0, 1).equals("0")
							|| compra.getCodcom().substring(0, 1).equals("F")
							|| compra.getCodcom().substring(0, 1).equals("E"))) {
				cError += "Serie de Factura incorrecta.";
			}
			if ((compra.getTipoComprobante() == "03" || compra.getTipoComprobante() == "07"
					|| compra.getTipoComprobante() == "08")
					&& !(compra.getCodcom().substring(0, 1).equals("0")
							|| compra.getCodcom().substring(0, 1).equals("B")
							|| compra.getCodcom().substring(0, 2).equals("EB"))) {
				cError += "Serie de Boleta de Venta incorrecta.";
			}
		}

		if (compra.getTipoComprobante() == "14") {
			if (compra.getCodcom().length() == 0) {
				cError += "Codigo de Comprobante de pago no especificado.";
			}
		}

		if (compra.getTipoComprobante() == "05"||compra.getTipoComprobante() == "15"||compra.getTipoComprobante() == "16") {
			// en veremos
			if ((compra.getSerie().length() == 0 && compra.getCodcom().length() == 0)) {
				cError += "Serie y/o codigo de Comprobante de pago no especificado.";
			}
		}

		/* validando comprobante final */
//		if (compra.getCodcomFinal().isEmpty()) {
//			if (!(compra.getTipoComprobante() == "00" || compra.getTipoComprobante() == "03"
//					|| compra.getTipoComprobante() == "14")) {
//				cError += "Error en codigo de comprobante final.";
//			}
//		}
		
		if (compra.getProveedor().getTipdoc().length() == 0) {
			cError += "Tipo de Documento del Proveedor no definido.";
		}
		if (compra.getProveedor().getDocprv().length() == 0) {
			cError += "Documento del Proveedor no definido.";
		}
		if (compra.getProveedor().getNombre().length() == 0) {
			cError += "Nombre o razon rocial del Proveedor no definido.";
		}
		
		if (compra.getProveedor().getTipdoc() == "06") {
			cError += validaRuc(compra.getProveedor().getDocprv());
			if (compra.getProveedor().getNombre().isBlank()) {
				cError += "Error en Nombre del Proveedor.";
			}
		} else if (compra.getProveedor().getTipdoc() == "01") {
			if (compra.getProveedor().getDocprv().length()!=8) {
				cError += "Error de longitud en DNI del Proveedor.";
			}
		}
		
		double _suma = compra.getBasimp() + compra.getIgv() + compra.getIcbp() + compra.getOtros();
//		if (_suma != compra.getImporteTotal()) {
//			cError += "Importes mal calculados.";
//		}
		/* validando tipo de comprobante modificado */
		if (compra.getTipoComprobante().equals("07") || compra.getTipoComprobante().equals("08")) {
			if (compra.getTipoComModificado().isBlank())
				cError = "Tipo de Comprobante Modificado vacia";
			if (compra.getSerieComModificado().isBlank())
				cError = "Serie Comprobante Modificado vacia";
			if (compra.getCodigoComModificado().isBlank())
				cError = "Codigo Comprobante Modificado vacia";
			if (compra.getFechaComModificado() == null)
				cError = "Codigo Comprobante Modificado vacia";
		}
		return cError;

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
	public ResponseEntity<Compra> modificar(@Valid @RequestBody Compra compra) throws Exception {
//		logger.info("midificar...compra,.> " + compra);
		String cError = "";

		cError = validate(compra);
		if (!cError.isBlank()) {
			throw new ModelNotFoundException(cError);
		}
		return new ResponseEntity<>(service.modificaTransaccion(compra), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<Compra> listarHateoasPorId(@PathVariable("id") Integer id) throws Exception {
		Compra obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO " + id);
		}

		EntityModel<Compra> recurso = EntityModel.of(obj);
		// localhost:8080/medicos/hateoas/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		recurso.add(link1.withRel("Paciente-recurso1"));
		recurso.add(link2.withRel("Paciente-recurso2"));

		return recurso;

	}

}
