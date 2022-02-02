package Retail.services;

import Retail.dtos.ClienteDTO;
import Retail.repositories.*;
import Retail.entities.Cliente;
import Retail.entities.Orden;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private OrdenRepository ordenRepository;

    public ClienteDTO obtenerCliente(Integer id) throws InvocationTargetException, IllegalAccessException {
        ClienteDTO clienteDTO = convertirClienteADTO(clienteRepository.findById(id).get());
        return clienteDTO;
    }

    public List<ClienteDTO> obtenerClientes() throws InvocationTargetException, IllegalAccessException {
        List<ClienteDTO> listaClientesDTO = convertirClientesADTO(clienteRepository.findAll());
        return listaClientesDTO;
    }
    public void createCliente(ClienteDTO ClienteDTO) throws InvocationTargetException, IllegalAccessException {
        clienteRepository.save(convertirDTOACliente(ClienteDTO));
    }

    public Cliente convertirDTOACliente(ClienteDTO pDTO) throws InvocationTargetException, IllegalAccessException {
        Cliente cliente = new Cliente();

        BeanUtils.copyProperties(cliente, pDTO);
        //System.out.println(ordenRepository.findByCliente(pDTO.getId()));
        List<Orden> listaOrden = ordenRepository.findByCliente(pDTO.getId());
        cliente.setOrdenes(listaOrden);

        return cliente;
    }

    public ClienteDTO convertirClienteADTO(Cliente c) throws InvocationTargetException, IllegalAccessException {
        ClienteDTO ClienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(ClienteDTO, c);

        return ClienteDTO;
    }

    public List<ClienteDTO> convertirClientesADTO(List<Cliente> list) throws InvocationTargetException, IllegalAccessException {
        List<ClienteDTO> listaDTO = new ArrayList<>();

        for (Cliente o : list) {
            listaDTO.add(convertirClienteADTO(o));
        }
        return listaDTO;
    }

    public void eliminarCliente(int id) {
        clienteRepository.deleteById(id);
    }

}
