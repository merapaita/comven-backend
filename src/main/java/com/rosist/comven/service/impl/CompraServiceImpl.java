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

import com.rosist.comven.model.Compra;
import com.rosist.comven.model.Empresa;
import com.rosist.comven.model.Proveedor;
import com.rosist.comven.repo.ICompraRepo;
import com.rosist.comven.repo.IGenericRepo;
import com.rosist.comven.service.ICompraService;

@Service
public class CompraServiceImpl extends CRUDImpl<Compra, Integer> implements ICompraService {

	@Autowired
	private ICompraRepo repo;

	private static Logger logger = LoggerFactory.getLogger(CompraServiceImpl.class);

	@Override
	protected IGenericRepo<Compra, Integer> getRepo() {
		return repo;
	}

	@Override
	public Page<Compra> listarPageable(Pageable page) {
		return repo.findAll(page);
	}

	@Override
	public List<Compra> listaCompras(Integer idEmpresa, Integer anno, Integer mes, Integer page, Integer size) throws Exception {
		List<Compra> compras = new ArrayList<>();
		List<Object[]> registros = repo.listaCompras(idEmpresa, anno, mes, page, size);

		registros.forEach(reg -> {
			Empresa empresa = new Empresa();
			empresa.setIdEmpresa(Integer.parseInt(String.valueOf(reg[1])));
			empresa.setRuc(String.valueOf(reg[2]));
			empresa.setRazsoc(String.valueOf(reg[3]));
			empresa.setDireccion(String.valueOf(reg[4]));

			Proveedor proveedor = new Proveedor();
			proveedor.setIdProveedor(Integer.parseInt(String.valueOf(reg[17])));
			proveedor.setTipdoc(String.valueOf(reg[18]));
			proveedor.setDocprv(String.valueOf(reg[19]));
			proveedor.setNombre(String.valueOf(reg[20]));
			proveedor.setDireccion(String.valueOf(reg[21]));
			
			Compra compra = new Compra();
			compra.setIdCompra(Integer.parseInt(String.valueOf(reg[0])));
			compra.setAnno(Integer.parseInt(String.valueOf(reg[5])));
			compra.setMes(Integer.parseInt(String.valueOf(reg[6])));
			compra.setCorrel(Integer.parseInt(String.valueOf(reg[7])));
			compra.setPeriodo(String.valueOf(reg[8]));
			compra.setAsiento(String.valueOf(reg[9]));
			compra.setFecha(LocalDate.parse(String.valueOf(reg[10])));
			if (reg[11]!=null) {
				compra.setFechaPago(LocalDate.parse(String.valueOf(reg[11])));
			}
			compra.setTipoComprobante(String.valueOf(reg[12]));
			compra.setSerie(String.valueOf(reg[13]));
			compra.setCodcom(String.valueOf(reg[14]));
			compra.setCodcomFinal(String.valueOf(reg[15]));
			compra.setRestaurantes(Boolean.parseBoolean(String.valueOf(reg[16])));
			compra.setProveedor(proveedor);
			compra.setDesope(String.valueOf(reg[22]));
			compra.setTipadq(String.valueOf(reg[23]));
			compra.setBasimp(Double.parseDouble(String.valueOf(reg[24])));
			compra.setIgv(Double.parseDouble(String.valueOf(reg[25])));
			compra.setValorNograbado(Double.parseDouble(String.valueOf(reg[26])));
			compra.setIsc(Double.parseDouble(String.valueOf(reg[27])));
			compra.setIcbp(Double.parseDouble(String.valueOf(reg[28])));
			compra.setOtros(Double.parseDouble(String.valueOf(reg[29])));
			compra.setImporteTotal(Double.parseDouble(String.valueOf(reg[30])));
			compra.setMoneda(String.valueOf(reg[31]));
			compra.setTipoCambio(Double.parseDouble(String.valueOf(reg[32])));
			if (reg[33]!=null) {
				compra.setFechaComModificado(LocalDate.parse(String.valueOf(reg[33])));
				compra.setTipoComModificado(String.valueOf(reg[34]));
				compra.setSerieComModificado(String.valueOf(reg[35]));
				compra.setCodigoComModificado(String.valueOf(reg[36]));
			}
			compra.setEstado(String.valueOf(reg[37]));
			compra.setDocumReferencia(String.valueOf(reg[38]));

			compras.add(compra);
		});
		return compras;
	}
	
	@Transactional
	@Override
	public Compra registraTransaccion(Compra compra) throws SQLException, Exception {
        String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDateTime   dUser = LocalDateTime.now();
		logger.info("registrarTransaccion...grabada.-> " + compra);

		compra.setCorrel(repo.getNewCorrel(compra.getEmpresa().getIdEmpresa(), compra.getAnno(), compra.getMes()));
		compra.setEstado("00");
	    compra.setUserup(cUser);
	    compra.setDuserup(dUser);
	    
	    String cAsiento = "M" + String.format("%02d",compra.getMes());
	    compra.setAsiento(cAsiento);

		repo.save(compra);

		return compra;
	}

	@Transactional
	@Override
	public Compra modificaTransaccion(Compra compra) throws Exception {
		logger.info("modificando cliente...grabada.-> ");
//      String cUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		LocalDateTime   dUser = LocalDateTime.now();
//		proveedor.setUserup("cUser");
//		proveedor.setDusercr(dUser);
		repo.modificaCompra(
				compra.getEmpresa().getIdEmpresa(), 
				compra.getAnno(),
				compra.getMes(),
				compra.getCorrel(),
				compra.getPeriodo(),
				compra.getAsiento(),
				compra.getFecha(),
				compra.getFechaPago(),
				compra.getTipoComprobante(),
				compra.getSerie(),
				compra.getCodcom(),
				compra.getCodcomFinal(),
				compra.getProveedor().getIdProveedor(),
				compra.getDesope(),
				compra.getTipadq(),
				compra.getBasimp(),
				compra.getIgv(),
				compra.getValorNograbado(),
				compra.getIsc(),
				compra.getIcbp(),
				compra.getOtros(),
				compra.getImporteTotal(),
				compra.getMoneda(),
				compra.getTipoCambio(),
				compra.getFechaComModificado(),
				compra.getTipoComModificado(),
				compra.getSerieComModificado(),
				compra.getCodigoComModificado(),
				compra.getEstado(),
				compra.getDocumReferencia(),
				compra.getUsercr(),
				compra.getDusercr(),
				compra.getIdCompra() );
		return compra;
	}

}
