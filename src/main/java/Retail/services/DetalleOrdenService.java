package Retail.services;

import Retail.dtos.DetalleOrdenDTO;
import Retail.dtos.DetalleOrdenFrontDto;
import Retail.repositories.*;
import Retail.entities.DetalleOrden;
import Retail.entities.Producto;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private OrdenRepository ordenRepository;

    public DetalleOrdenDTO obtenerDetalleOrden(Integer id) throws InvocationTargetException, IllegalAccessException {
        DetalleOrdenDTO detalleOrdenDTO = convertirDetalleOrdenADTO(detalleOrdenRepository.findById(id).get());
        return detalleOrdenDTO;
    }

    public List<DetalleOrdenDTO> obtenerOrdenes() throws InvocationTargetException, IllegalAccessException {
        List<DetalleOrdenDTO> listaOrdensDTO = convertirDetalleOrdenesADTO(detalleOrdenRepository.findAll());
        return listaOrdensDTO;
    }
    public void createDetallesOrdenes(List<DetalleOrden> listDetalleOrden) throws InvocationTargetException, IllegalAccessException {
        for (DetalleOrden d:listDetalleOrden) {
            detalleOrdenRepository.save(d);
        }
    }
    public void createDetalleOrden(DetalleOrdenDTO detalleOrdenDTO) throws InvocationTargetException, IllegalAccessException {
        detalleOrdenRepository.save(convertirDTOADetalleOrden(detalleOrdenDTO));
    }

    public List<DetalleOrden> convertirDetalleOrdenDTOFrontADetalleOrden(List<DetalleOrdenFrontDto> list, Integer idOrden) throws InvocationTargetException, IllegalAccessException {
        List<DetalleOrden> listDetalles= new ArrayList<>();

        for (DetalleOrdenFrontDto dtodFront: list) {
            DetalleOrden d = new DetalleOrden();
            Producto producto = productoService.convertirDTOAProducto(productoService.obtenerProducto(dtodFront.getId()));
            d.setCantidad(dtodFront.getQty());
            d.setProducto(producto);
            d.setOrden(ordenRepository.findById(idOrden).get());
            productoService.restarStock(producto);
            listDetalles.add(d);
        }
        return listDetalles;
    }

    public List<DetalleOrdenDTO> convertirDetalleOrdenDTOFrontADetalleOrdenDto(List<DetalleOrdenFrontDto> list, Integer idOrden) throws InvocationTargetException, IllegalAccessException {
        List<DetalleOrdenDTO> listDetalles= new ArrayList<>();

        for (DetalleOrdenFrontDto dtodFront: list) {
            DetalleOrdenDTO d = new DetalleOrdenDTO();
            d.setIdProducto(dtodFront.getId());
            d.setCantidad(dtodFront.getQty());
            d.setDescripcion(productoService.obtenerProducto(d.getIdProducto()).getDescripcion());
            d.setPrecioUnitario(productoService.obtenerProducto(d.getIdProducto()).getPrecioUnitario().doubleValue());
            d.setIdOrden(idOrden);
        }
        return listDetalles;
    }

    public DetalleOrden convertirDTOADetalleOrden(DetalleOrdenDTO pDTO) throws InvocationTargetException, IllegalAccessException {
        DetalleOrden dorden = new DetalleOrden();
        BeanUtils.copyProperties(dorden, pDTO);
        dorden.setPrecioTotal(pDTO.getPrecioUnitario());
        Producto producto = productoService.convertirDTOAProducto(productoService.obtenerProducto(pDTO.getIdProducto()));
        dorden.setProducto(producto);

        return dorden;
    }

    public DetalleOrdenDTO convertirDetalleOrdenADTO(DetalleOrden d) {
        DetalleOrdenDTO detalleOrdenDTO = new DetalleOrdenDTO();
        detalleOrdenDTO.setId(d.getId());
        detalleOrdenDTO.setIdProducto(d.getProducto().getId());
        detalleOrdenDTO.setIdOrden(d.getOrden().getId());
        detalleOrdenDTO.setPrecioUnitario(d.getProducto().getPrecioUnitario());
        detalleOrdenDTO.setDescripcion(d.getProducto().getDescripcion());
        detalleOrdenDTO.setCantidad(d.getCantidad());

        return detalleOrdenDTO;
    }

    public List<DetalleOrdenDTO> convertirDetalleOrdenesADTO(List<DetalleOrden> list) {
        List<DetalleOrdenDTO> listaDTO = new ArrayList<>();

        for (DetalleOrden o : list) {
            listaDTO.add(convertirDetalleOrdenADTO(o));
        }
        return listaDTO;
    }

    public void eliminarDetalleOrden(int id) {
        detalleOrdenRepository.deleteById(id);
    }
}
