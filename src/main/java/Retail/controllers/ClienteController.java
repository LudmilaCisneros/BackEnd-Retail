package Retail.controllers;

import Retail.dtos.ClienteDTO;
import Retail.services.ClienteService;
import Retail.services.OrdenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/clientes")
@RestController
@CrossOrigin( origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ClienteController {
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private OrdenService ordenService;

    @GetMapping(value = {"/"})
    public ResponseEntity<?> getClientes() throws Exception {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.obtenerClientes());
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontraron clientes.\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getCliente(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.obtenerCliente(id));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró el cliente " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}/ordenes")
    public ResponseEntity<?> getOrdenesByCliente(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ordenService.obtenerOrdenesPorIdCliente(id));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontraron ordenes del cliente " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<?> postCliente(@RequestBody ClienteDTO clienteDTO) throws Exception{
        try {
            clienteService.createCliente(clienteDTO);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> deleteCliente(@PathVariable Integer id) throws Exception{
        try {
            clienteService.eliminarCliente(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró el cliente " + id + ".\"}");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
