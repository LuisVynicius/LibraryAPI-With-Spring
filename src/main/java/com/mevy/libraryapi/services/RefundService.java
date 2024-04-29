package com.mevy.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.Refund;
import com.mevy.libraryapi.entities.dto.RefundCreateDTO;
import com.mevy.libraryapi.entities.dto.RefundUpdateDTO;
import com.mevy.libraryapi.repositories.RefundRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RefundService {
    
    @Autowired
    private RefundRepository refundRepository;

    public Refund findById(Long id){
        return refundRepository.findById(id).orElseThrow(() -> new RuntimeException("Refund not found. id: " + id));
    }

    @Transactional
    public void create(Refund refund){
        refund.setId(null);
        refundRepository.save(refund);
    }

    public void deleteById(Long id){
        try {
            refundRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new RuntimeException("Data integrity exception. ");
        }
    }

    @Transactional
    public void update(Refund refund){
        try {
            Refund oldRefund = refundRepository.getReferenceById(refund.getId());
            updateData(oldRefund, refund);
        } catch(EntityNotFoundException e){
            new RuntimeException("Refund not found. id: " + refund.getId());
        }
    }

    public void updateData(Refund oldRefund, Refund refund){
        oldRefund.setReason(refund.getReason());
    }

    public Refund fromDTO(RefundCreateDTO refundDTO){
        Refund refund = new Refund();
        refund.setMoment(refundDTO.getMoment());
        refund.setReason(refundDTO.getReason());
        refund.setOrder(refundDTO.getOrder());
        return refund;
    }

    public Refund fromDTO(RefundUpdateDTO refundDTO){
        Refund refund = new Refund();
        refund.setId(refundDTO.getId());
        refund.setReason(refundDTO.getReason());
        return refund;
    }
}
