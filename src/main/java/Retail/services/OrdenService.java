package Retail.services;

import Retail.dtos.DetalleOrdenFrontDto;
import Retail.dtos.OrdenDTO;
import Retail.entities.*;
import Retail.repositories.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private DetalleOrdenService detalleOrdenService;
    @Autowired
    private OrdenCriteria ordenCriteria;

    public OrdenDTO obtenerOrden(Integer id) throws InvocationTargetException, IllegalAccessException {
        OrdenDTO ordenDTO = convertirOrdenADTO(ordenCriteria.buscar(id));

        return ordenDTO;
    }
    public List<OrdenDTO> obtenerOrdenesPorIdCliente(Integer id) throws InvocationTargetException, IllegalAccessException {
        List<OrdenDTO> ordenesDto = convertirOrdenesADTO(ordenCriteria.buscarPorCliente(id));
        return ordenesDto;
    }

    public List<OrdenDTO> obtenerOrdenes() throws InvocationTargetException, IllegalAccessException {
        List<OrdenDTO> listaOrdensDTO = convertirOrdenesADTO(ordenCriteria.buscarAll());
        return listaOrdensDTO;
    }
    public void createOrden(OrdenDTO OrdenDTO) throws InvocationTargetException, IllegalAccessException {
        ordenRepository.save(convertirDTOAOrden(OrdenDTO));
    }
    public void createOrdenFront(List<DetalleOrdenFrontDto> list) throws InvocationTargetException, IllegalAccessException {
        Boolean ret = false;
        Orden orden = new Orden();
        orden.setVendedor(vendedorService.convertirDTOAVendedor(vendedorService.obtenerVendedor(0)));
        orden.setCliente(clienteService.convertirDTOACliente(clienteService.obtenerCliente(10)));
        Orden ordenAux = ordenRepository.saveAndFlush(orden);
        List<DetalleOrden> listDetalles = detalleOrdenService.convertirDetalleOrdenDTOFrontADetalleOrden(list, ordenAux.getId());//
        detalleOrdenService.createDetallesOrdenes(listDetalles);
        ordenAux.setDetallesOrden(listDetalles);
        ordenRepository.save(ordenAux);
    }

    public Orden convertirDTOAOrden(OrdenDTO pDTO) throws InvocationTargetException, IllegalAccessException {
        Orden orden = new Orden();
        BeanUtils.copyProperties(orden, pDTO);

        Cliente cliente = clienteService.convertirDTOACliente((clienteService.obtenerCliente(pDTO.getIdCliente())));
        orden.setCliente(cliente);

        Vendedor vendedor = vendedorService.convertirDTOAVendedor(vendedorService.obtenerVendedor(pDTO.getIdVendedor()));
        orden.setVendedor(vendedor);

        return orden;
    }

    public OrdenDTO convertirOrdenADTO(Orden o) throws InvocationTargetException, IllegalAccessException {
        OrdenDTO ordenDTO = new OrdenDTO();
        BeanUtils.copyProperties(ordenDTO, o);
        ordenDTO.setIdCliente(o.getCliente().getId());
        ordenDTO.setIdVendedor(o.getVendedor().getId());
        ordenDTO.setDetalles(detalleOrdenService.convertirDetalleOrdenesADTO(o.getDetallesOrden()));
        return ordenDTO;
    }

    public List<OrdenDTO> convertirOrdenesADTO(List<Orden> list) throws InvocationTargetException, IllegalAccessException {
        List<OrdenDTO> listaDTO = new ArrayList<>();

        for (Orden o : list) {
            listaDTO.add(convertirOrdenADTO(o));
        }
        return listaDTO;
    }

    public List<Orden> convertirDTOAOrdenes(List<OrdenDTO> listDto) throws InvocationTargetException, IllegalAccessException {
        List<Orden> lista = new ArrayList<>();

        for (OrdenDTO o : listDto) {
            lista.add(convertirDTOAOrden(o));
        }
        return lista;
    }

    public void eliminarOrden(int id) {
        ordenCriteria.eliminar(id);
    }

}
