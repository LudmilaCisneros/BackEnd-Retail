package Retail.controllers;

import Retail.dtos.AdminDTO;
import Retail.services.AdminService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/admins")
@RestController
@CrossOrigin( origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @GetMapping(value = {"/"})
    public List<AdminDTO> getAdmins() throws ChangeSetPersister.NotFoundException, InvocationTargetException, IllegalAccessException {
        List<AdminDTO> list = new ArrayList<>();

        try {
            list = adminService.obtenerAdmins();
        } catch (RuntimeException e) {
            throw new ChangeSetPersister.NotFoundException();
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @GetMapping(value = {"/{id}"})
    public AdminDTO getAdmin(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException, InvocationTargetException, IllegalAccessException {
        AdminDTO adminDTO = new AdminDTO();

        if (id != null) {
            try {
                adminDTO = adminService.obtenerAdmin(id);
            } catch (RuntimeException e) {
                throw new ChangeSetPersister.NotFoundException();
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return adminDTO;
    }

    @PostMapping(value = {"/"})
    public void postAdmin(@RequestBody AdminDTO AdminDTO) throws InvocationTargetException, IllegalAccessException {
        adminService.createAdmin(AdminDTO);
    }

    @DeleteMapping(value = {"/{id}"})
    public void deleteAdmin(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        if(id != null){
            try {
                adminService.eliminarAdmin(id);
            }catch (RuntimeException e){
                throw new ChangeSetPersister.NotFoundException();
            }
        }
    }
}
