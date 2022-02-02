package Retail.services;

import Retail.dtos.CategoriaDTO;
import Retail.entities.Proveedor;
import Retail.repositories.*;
import Retail.entities.Categoria;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDTO obtenerCategoria(Integer id) throws InvocationTargetException, IllegalAccessException {
        CategoriaDTO categoriaDTO = convertirCategoriaADTO(categoriaRepository.findById(id).get());
        return categoriaDTO;
    }

    public List<CategoriaDTO> obtenerCategorias() throws InvocationTargetException, IllegalAccessException {
        List<CategoriaDTO> listaCategoriasDTO = convertirCategoriasADTO(categoriaRepository.findAll());
        return listaCategoriasDTO;
    }
    public void createCategoria(CategoriaDTO CategoriaDTO) throws InvocationTargetException, IllegalAccessException {
        categoriaRepository.save(convertirDTOACategoria(CategoriaDTO));
    }

    public Categoria convertirDTOACategoria(CategoriaDTO pDTO) throws InvocationTargetException, IllegalAccessException {
        Categoria categoria = new Categoria();
        BeanUtils.copyProperties(categoria, pDTO);

        return categoria;
    }

    public CategoriaDTO convertirCategoriaADTO(Categoria c) throws InvocationTargetException, IllegalAccessException {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        BeanUtils.copyProperties(categoriaDTO, c);

        return categoriaDTO;
    }

    public List<CategoriaDTO> convertirCategoriasADTO(List<Categoria> list) throws InvocationTargetException, IllegalAccessException {
        List<CategoriaDTO> listaDTO = new ArrayList<>();

        for (Categoria o : list) {
            listaDTO.add(convertirCategoriaADTO(o));
        }
        return listaDTO;
    }

    public void eliminarCategoria(int id) {
        categoriaRepository.deleteById(id);
    }

}
