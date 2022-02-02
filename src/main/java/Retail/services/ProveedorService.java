package Retail.services;

import Retail.dtos.ProveedorDTO;
import Retail.entities.Categoria;
import Retail.repositories.*;
import Retail.entities.Proveedor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private CategoriaService categoriaService;

    public ProveedorDTO obtenerProveedor(Integer id) throws InvocationTargetException, IllegalAccessException {
        ProveedorDTO ProveedorDTO = convertirProveedorADTO(proveedorRepository.findById(id).get());
        return ProveedorDTO;
    }

    public List<ProveedorDTO> obtenerProveedores() throws InvocationTargetException, IllegalAccessException {
        List<ProveedorDTO> listaProveedoresDTO = convertirProveedorsADTO(proveedorRepository.findAll());
        return listaProveedoresDTO;
    }
    public void createProveedor(ProveedorDTO ProveedorDTO) throws InvocationTargetException, IllegalAccessException {
        proveedorRepository.save(convertirDTOAProveedor(ProveedorDTO));
    }

    public Proveedor convertirDTOAProveedor(ProveedorDTO pDTO) throws InvocationTargetException, IllegalAccessException {
        Proveedor proveedor = new Proveedor();
        BeanUtils.copyProperties(proveedor, pDTO);
        Categoria categoria = categoriaService.convertirDTOACategoria(categoriaService.obtenerCategoria(pDTO.getIdCategoria()));
        proveedor.setCategoria(categoria);
        return proveedor;
    }

    public ProveedorDTO convertirProveedorADTO(Proveedor c) throws InvocationTargetException, IllegalAccessException {
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        BeanUtils.copyProperties(proveedorDTO, c);
        proveedorDTO.setIdCategoria(c.getCategoria().getId());

        return proveedorDTO;
    }

    public List<ProveedorDTO> convertirProveedorsADTO(List<Proveedor> list) throws InvocationTargetException, IllegalAccessException {
        List<ProveedorDTO> listaDTO = new ArrayList<>();

        for (Proveedor v : list) {
            listaDTO.add(convertirProveedorADTO(v));
        }
        return listaDTO;
    }

    public void eliminarProveedor(int id) {
        proveedorRepository.deleteById(id);
    }
}
