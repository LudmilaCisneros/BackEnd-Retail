package Retail.services;

import Retail.dtos.ProductoDTO;
import Retail.entities.Categoria;
import Retail.entities.Producto;
import Retail.entities.Proveedor;
import Retail.repositories.ProductoRepository;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private CategoriaService categoriaService;

    public ProductoDTO obtenerProducto(Integer id) throws InvocationTargetException, IllegalAccessException {
        ProductoDTO productoDTO = convertirProductoADTO(productoRepo.findById(id).get());
        return productoDTO;
    }

    public List<ProductoDTO> obtenerProductos() throws InvocationTargetException, IllegalAccessException {
        List<ProductoDTO> listaProductosDTO = convertirProductosADTO(productoRepo.findAll());
        return listaProductosDTO;
    }

    public Page<ProductoDTO> obtenerProductos(Pageable pageable) {
        Page<Producto>  productos = productoRepo.findAll(pageable);
        Page<ProductoDTO> listaProductosDTO = productos.map(ProductoDTO::new);

        return listaProductosDTO;
    }

    public void createProducto(ProductoDTO productoDto) throws InvocationTargetException, IllegalAccessException {
        productoRepo.save(convertirDTOAProducto(productoDto));
    }

    public Producto convertirDTOAProducto(ProductoDTO pDTO) throws InvocationTargetException, IllegalAccessException {
        Producto producto = new Producto();
        BeanUtils.copyProperties(producto, pDTO);

        Categoria categoria = categoriaService.convertirDTOACategoria(categoriaService.obtenerCategoria(pDTO.getIdCategoria()));
        producto.setCategoria(categoria);

        Proveedor proveedor = proveedorService.convertirDTOAProveedor(proveedorService.obtenerProveedor(pDTO.getIdProveedor()));
        producto.setProveedor(proveedor);

        return producto;
    }

    public ProductoDTO convertirProductoADTO(Producto p) throws InvocationTargetException, IllegalAccessException {
        ProductoDTO productoDto = new ProductoDTO();
        BeanUtils.copyProperties(productoDto, p);
        productoDto.setIdProveedor(p.getProveedor().getId());
        productoDto.setIdCategoria(p.getCategoria().getId());

        return productoDto;
    }

    public List<ProductoDTO> convertirProductosADTO(List<Producto> list) throws InvocationTargetException, IllegalAccessException {
        List<ProductoDTO> listaDTO = new ArrayList<>();

        for (Producto p : list) {
            listaDTO.add(convertirProductoADTO(p));
        }
        return listaDTO;
    }

    public void eliminarProducto(int id) {
        productoRepo.deleteById(id);
    }

    public void restarStock(Producto producto){
        producto.setUnidadesStock(producto.getUnidadesStock()-1);
        productoRepo.save(producto);
    }
}
