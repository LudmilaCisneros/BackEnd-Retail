package Retail.services;

import Retail.dtos.AdminDTO;
import Retail.repositories.*;
import Retail.entities.Admin;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public AdminDTO obtenerAdmin(Integer id) throws InvocationTargetException, IllegalAccessException {
        AdminDTO adminDTO = convertirAdminADTO(adminRepository.findById(id).get());
        return adminDTO;
    }

    public List<AdminDTO> obtenerAdmins() throws InvocationTargetException, IllegalAccessException {
        List<AdminDTO> listaAdminsDTO = convertirAdminsADTO(adminRepository.findAll());
        return listaAdminsDTO;
    }
    public void createAdmin(AdminDTO adminDTO) throws InvocationTargetException, IllegalAccessException {
        adminRepository.save(convertirDTOAAdmin(adminDTO));
    }

    public Admin convertirDTOAAdmin(AdminDTO pDTO) throws InvocationTargetException, IllegalAccessException {
        Admin Admin = new Admin();
        BeanUtils.copyProperties(Admin, pDTO);

        return Admin;
    }

    public AdminDTO convertirAdminADTO(Admin c) throws InvocationTargetException, IllegalAccessException {
        AdminDTO AdminDTO = new AdminDTO();
        BeanUtils.copyProperties(AdminDTO, c);

        return AdminDTO;
    }

    public List<AdminDTO> convertirAdminsADTO(List<Admin> list) throws InvocationTargetException, IllegalAccessException {
        List<AdminDTO> listaDTO = new ArrayList<>();

        for (Admin o : list) {
            listaDTO.add(convertirAdminADTO(o));
        }
        return listaDTO;
    }

    public void eliminarAdmin(int id) {
        adminRepository.deleteById(id);
    }

}

