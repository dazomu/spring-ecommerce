package com.curso.ecommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

	@Autowired //el contenedor de spring se encargará de q esta variable este instanciada
	private ProductoService productoService;
	
	@GetMapping("") //cadena vacia para que se mapee a "productos" 
	public String show(Model model){ // El objeto Model envía datos del backend a la vista
		
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	
	@PostMapping("/save") //
	public String save(Producto producto) {
		LOGGER.info("Este es el objeto producto {}", producto); //ese objeto lo muestra como toString
		Usuario u= new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		
		productoService.save(producto);
		return "redirect:/productos"; //es una peticion al controlador productos
	}
	
}
