package com.kronoly.Service;

import com.kronoly.Entity.Horarios;
import com.kronoly.Repository.HorariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorariosService {

    @Autowired
    private HorariosRepository horariosRepository;

    public Horarios cadastrarHorarios(){





        return new Horarios();
    }
}
