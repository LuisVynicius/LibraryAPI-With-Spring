package com.mevy.libraryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mevy.libraryapi.entities.Refund;
import com.mevy.libraryapi.entities.dto.RefundCreateDTO;
import com.mevy.libraryapi.entities.dto.RefundUpdateDTO;
import com.mevy.libraryapi.repositories.RefundRepository;
import com.mevy.libraryapi.services.exceptions.DataBindingViolationException;
import com.mevy.libraryapi.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RefundService {
    
    @Autowired
    private RefundRepository refundRepository;

    public Refund findById(Long id){
        return refundRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Refund.class, id));
    }

    @Transactional
    public Refund create(Refund refund){
        refund.setId(null);
        refund = refundRepository.save(refund);
        return refund;
    }

    public void deleteById(Long id){
        findById(id);
        try {
            refundRepository.deleteById(id);
        } catch(DataIntegrityViolationException e){
            throw new DataBindingViolationException();
        }
    }

    @Transactional
    public void update(Refund refund){
        try {
            Refund oldRefund = refundRepository.getReferenceById(refund.getId());
            updateData(oldRefund, refund);
        } catch(EntityNotFoundException e){
            new ResourceNotFoundException(Refund.class, refund.getId());
        }
    }

    private void updateData(Refund oldRefund, Refund refund){
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
