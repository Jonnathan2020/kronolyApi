package com.kronoly.Controller;

import com.kronoly.Entity.Horario;
import com.kronoly.Service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public List<Horario> consultarHorarios(){
        return horarioService.consultarHorarios();
    }

    @GetMapping("/{id}")
    public Horario consultarHorarioPorId(@PathVariable int id){
        return horarioService.consultarHorarioPorId(id);

    }
}
