package com.rosist.comven.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rosist.comven.model.Venta;
import com.rosist.comven.model.Cliente;
import com.rosist.comven.model.Empresa;
import com.rosist.comven.model.Proveedor;
import com.rosist.comven.model.Venta;
import com.rosist.comven.repo.IVentaRepo;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.repo.IVentaRepo;
import com.rosist.comven.service.IVentaService;
import com.rosist.comven.service.IVentaService;

@Service
public class VentaServiceImpl extends CRUDImpl<Venta, Integer> implements IVentaService {

	@Autowired
	private IVentaRepo repo;
	
    private static Logger logger = LoggerFactory.getLogger(VentaServiceImpl.class);
    
	@Override
	protected IGenericRepo<Venta, Integer> getRepo() {
		return repo;
	}
	
	@Override
	public Page<Venta> listarPageable(Pageable page) {
		return repo.findAll(page);
	}

	@Transactional
	@Override
	public Venta registraTransaccion(Venta venta) throws SQLException, Exception {
        String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDateTime   dUser = LocalDateTime.now();
		logger.info("registrarTransaccion...grabada.-> " + venta);

		venta.setCorrel(repo.getNewCorrel(venta.getEmpresa().getIdEmpresa(), venta.getAnno(), venta.getMes()));
		venta.setEstado("00");
	    venta.setUserup(cUser);
	    venta.setDuserup(dUser);
	    
	    String cAsiento = "M" + String.format("%02d",venta.getMes());
	    venta.setAsiento(cAsiento);

		repo.save(venta);

		return venta;
	}

	@Transactional
	@Override
	public Venta modificaTransaccion(Venta venta) throws Exception {
		logger.info("modificando cliente...grabada.-> ");
//      String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		LocalDateTime   dUser = LocalDateTime.now();
//		proveedor.setUserup("cUser");
//		proveedor.setDusercr(dUser);
		repo.modificaVenta(
				venta.getEmpresa().getIdEmpresa(), 
				venta.getAnno(),
				venta.getMes(),
				venta.getCorrel(),
				venta.getPeriodo(),
				venta.getAsiento(),
				venta.getFecha(),
				venta.getFecpag(),
				venta.getTipcom(),
				venta.getSerie(),
				venta.getCodcom(),
				venta.getCodcomFinal(),
				venta.getCliente().getIdCliente(),
				venta.getDesope(),
				venta.getTipope(),
				venta.getBasimp(),
				venta.getIgv(),
				venta.getIsc(),
				venta.getIcbp(),
				venta.getOtros(),
				venta.getImporteTotal(),
				venta.getMoneda(),
				venta.getTipoCambio(),
				venta.getFeccomModificado(),
				venta.getTipcomModificado(),
				venta.getSerieComModificado(),
				venta.getCodigoComModificado(),
				venta.getEstado(),
				venta.getDocumReferencia(),
				venta.getUsercr(),
				venta.getDusercr(),
				venta.getIdVenta() );
		return venta;
	}

	@Override
	public List<Venta> listaVentas(Integer idEmpresa, Integer anno, Integer mes, Integer page, Integer size) throws Exception {
		List<Venta> ventas = new ArrayList<>();
		List<Object[]> registros = repo.listaVentas(idEmpresa, anno, mes, page, size);
		
		registros.forEach(reg -> {
			Empresa empresa = new Empresa();
			empresa.setIdEmpresa(Integer.parseInt(String.valueOf(reg[1])));
			empresa.setRuc(String.valueOf(reg[2]));
			empresa.setRazsoc(String.valueOf(reg[3]));
			empresa.setDireccion(String.valueOf(reg[4]));
			
			Cliente cliente = new Cliente();
			cliente.setIdCliente(Integer.parseInt(String.valueOf(reg[16])));
			cliente.setTipdoc(String.valueOf(reg[17]));
			cliente.setDoccli(String.valueOf(reg[18]));
			cliente.setNombre(String.valueOf(reg[19]));
			cliente.setDireccion(String.valueOf(reg[20]));
			
			Venta venta = new Venta();
			venta.setIdVenta(Integer.parseInt(String.valueOf(reg[0])));
			venta.setAnno(Integer.parseInt(String.valueOf(reg[5])));
			venta.setMes(Integer.parseInt(String.valueOf(reg[6])));
			venta.setCorrel(Integer.parseInt(String.valueOf(reg[7])));
			venta.setPeriodo(String.valueOf(reg[8]));
			venta.setAsiento(String.valueOf(reg[9]));
			venta.setFecha(LocalDate.parse(String.valueOf(reg[10])));
			if (reg[11]!=null) {
				venta.setFecpag(LocalDate.parse(String.valueOf(reg[11])));
			}
			venta.setTipcom(String.valueOf(reg[12]));
			venta.setSerie(String.valueOf(reg[13]));
			venta.setCodcom(String.valueOf(reg[14]));
			venta.setCodcomFinal(String.valueOf(reg[15]));
			venta.setCliente(cliente);
			venta.setDesope(String.valueOf(reg[21]));
			venta.setTipope(String.valueOf(reg[22]));
			venta.setBasimp(Double.parseDouble(String.valueOf(reg[23])));
			venta.setIgv(Double.parseDouble(String.valueOf(reg[24])));
			venta.setIsc(Double.parseDouble(String.valueOf(reg[25])));
			venta.setIcbp(Double.parseDouble(String.valueOf(reg[26])));
			venta.setOtros(Double.parseDouble(String.valueOf(reg[27])));
			venta.setImporteTotal(Double.parseDouble(String.valueOf(reg[28])));
			venta.setMoneda(String.valueOf(reg[29]));
			venta.setTipoCambio(Double.parseDouble(String.valueOf(reg[30])));
			if (reg[31]!=null) {
				venta.setFeccomModificado(LocalDate.parse(String.valueOf(reg[31])));
				venta.setTipcomModificado(String.valueOf(reg[32]));
				venta.setSerieComModificado(String.valueOf(reg[33]));
				venta.setCodigoComModificado(String.valueOf(reg[34]));
			}
			venta.setEstado(String.valueOf(reg[35]));
			venta.setDocumReferencia(String.valueOf(reg[36]));

			ventas.add(venta);
		});
		return ventas;
	}

}
