package Retail.services;

import Retail.dtos.VendedorDTO;
import Retail.repositories.*;
import Retail.entities.Vendedor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository VendedorRepository;
    @Autowired
    private OrdenRepository ordenRepository;


    public VendedorDTO obtenerVendedor(Integer id) throws InvocationTargetException, IllegalAccessException {
        VendedorDTO VendedorDTO = convertirVendedorADTO(VendedorRepository.findById(id).get());
        return VendedorDTO;
    }

    public List<VendedorDTO> obtenerVendedores() throws InvocationTargetException, IllegalAccessException {
        List<VendedorDTO> listaVendedoresDTO = convertirVendedorsADTO(VendedorRepository.findAll());
        return listaVendedoresDTO;
    }
    public void createVendedor(VendedorDTO VendedorDTO) throws InvocationTargetException, IllegalAccessException {
        VendedorRepository.save(convertirDTOAVendedor(VendedorDTO));
    }

    /** Convierte un DTO a Vendedor
     * @param pDTO
     * @return
     */
    public Vendedor convertirDTOAVendedor(VendedorDTO pDTO) throws InvocationTargetException, IllegalAccessException {
        Vendedor vendedor = new Vendedor();
        //Serv_Equipo sE = new Serv_Equipo();
        BeanUtils.copyProperties(vendedor, pDTO);

        return vendedor;
    }
    /** Convierte un Vendedor a DTO Vendedor
     * @param c
     * @return
     */
    public VendedorDTO convertirVendedorADTO(Vendedor c) throws InvocationTargetException, IllegalAccessException {
        VendedorDTO VendedorDTO = new VendedorDTO();
        BeanUtils.copyProperties(VendedorDTO, c);

        return VendedorDTO;
    }
    /** Convierte una lista de jugadores a dto
     * @param list
     * @return
     */
    public List<VendedorDTO> convertirVendedorsADTO(List<Vendedor> list) throws InvocationTargetException, IllegalAccessException {
        List<VendedorDTO> listaDTO = new ArrayList<>();

        for (Vendedor v : list) {
            listaDTO.add(convertirVendedorADTO(v));
        }
        return listaDTO;
    }

    public void eliminarVendedor(int id) {
        VendedorRepository.deleteById(id);
    }

}
