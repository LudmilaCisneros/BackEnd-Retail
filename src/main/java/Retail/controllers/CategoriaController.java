package Retail.controllers;

import Retail.dtos.CategoriaDTO;
import Retail.services.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/categorias")
@RestController
@CrossOrigin( origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class CategoriaController {
    private static final Logger logger = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = {"/"})
    public ResponseEntity<?> getCategorias() throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.obtenerCategorias());
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontraron categorias.\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getCategoria(@PathVariable Integer id) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.obtenerCategoria(id));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró categoria " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<?> postCategoria(@RequestBody CategoriaDTO categoriaDTO) throws Exception {
        try {
            categoriaService.createCategoria(categoriaDTO);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> deleteCategoria(@PathVariable Integer id) {
        try {
            categoriaService.eliminarCategoria(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró la categoria " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
