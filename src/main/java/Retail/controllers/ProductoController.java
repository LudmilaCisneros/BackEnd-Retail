package Retail.controllers;

import Retail.dtos.ProductoDTO;

import Retail.entities.Producto;
import Retail.services.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/productos")
@RestController
@CrossOrigin( origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ProductoController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping(value = {"/"})
    public ResponseEntity<?> getProductos(Pageable pageable) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.obtenerProductos(pageable));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontraron productos.\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getProducto(@PathVariable Integer id) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.obtenerProducto(id));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró el producto " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<?> postProducto(@RequestBody ProductoDTO productoDTO){
        try{
            productoService.createProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Error. Contacte al admin.\"}");
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> deleteProducto(@PathVariable Integer id) {
        try {
            productoService.eliminarProducto(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró el producto " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
