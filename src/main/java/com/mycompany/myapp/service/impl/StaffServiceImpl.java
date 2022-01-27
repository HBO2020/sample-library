package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Staff;
import com.mycompany.myapp.repository.StaffRepository;
import com.mycompany.myapp.service.StaffService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Staff}.
 */
@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    private final Logger log = LoggerFactory.getLogger(StaffServiceImpl.class);

    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff save(Staff staff) {
        log.debug("Request to save Staff : {}", staff);
        return staffRepository.save(staff);
    }

    @Override
    public Optional<Staff> partialUpdate(Staff staff) {
        log.debug("Request to partially update Staff : {}", staff);

        return staffRepository
            .findById(staff.getId())
            .map(existingStaff -> {
                if (staff.getIdStaff() != null) {
                    existingStaff.setIdStaff(staff.getIdStaff());
                }
                if (staff.getPassword() != null) {
                    existingStaff.setPassword(staff.getPassword());
                }

                return existingStaff;
            })
            .map(staffRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> findAll() {
        log.debug("Request to get all Staff");
        return staffRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Staff> findOne(Long id) {
        log.debug("Request to get Staff : {}", id);
        return staffRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Staff : {}", id);
        staffRepository.deleteById(id);
    }
}
