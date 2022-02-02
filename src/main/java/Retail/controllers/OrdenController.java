package Retail.controllers;

import Retail.dtos.DetalleOrdenFrontDto;
import Retail.services.OrdenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenes")
@CrossOrigin( origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class OrdenController {
    private static final Logger logger = LoggerFactory.getLogger(OrdenController.class);

    @Autowired
    private OrdenService ordenService;

    @GetMapping(value = {"/"})
    public ResponseEntity<?> getOrdenes() throws Exception {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(ordenService.obtenerOrdenes());
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontraron ordenes.\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = {"/{id}"})//ok
    public ResponseEntity<?> getOrden(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ordenService.obtenerOrden(id));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró orden " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<?> postOrden(@RequestBody List<DetalleOrdenFrontDto> listOrden) throws Exception {
        try {
            ordenService.createOrdenFront(listOrden);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> deleteOrden(@PathVariable Integer id) {
        try {
            ordenService.eliminarOrden(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró orden " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
