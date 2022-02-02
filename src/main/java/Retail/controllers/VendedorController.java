package Retail.controllers;

import Retail.dtos.VendedorDTO;
import Retail.services.VendedorService;

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

@RequestMapping("/vendedores")
@RestController
@CrossOrigin( origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class VendedorController {
    private static final Logger logger = LoggerFactory.getLogger(VendedorController.class);

    @Autowired
    private VendedorService vendedorService;

    @GetMapping(value = {"/"})
    public ResponseEntity<?> getVendedores() throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(vendedorService.obtenerVendedores());
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontraron vendedores.\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getVendedor(@PathVariable Integer id) throws Exception{

        try {
            return ResponseEntity.status(HttpStatus.OK).body(vendedorService.obtenerVendedor(id));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró el vendedor " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<?> postVendedor(@RequestBody VendedorDTO VendedorDTO) throws Exception {
        try {
            vendedorService.createVendedor(VendedorDTO);;
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> deleteVendedor(@PathVariable Integer id) throws Exception{
        try {
            vendedorService.eliminarVendedor(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró el vendedor " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
