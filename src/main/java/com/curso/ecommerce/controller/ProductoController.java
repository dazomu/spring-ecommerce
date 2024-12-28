package com.curso.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	//te reenvia a la ruta para editar el producto en funcion a su id
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Producto producto = new Producto();
		
		//Llama al servicio para obtener el producto por su ID
		Optional<Producto> optionalProducto=productoService.get(id);
		
		//este get es de la clase Optional, es para extraer el valor guardado y q se almacene en la variable producto
		producto = optionalProducto.get(); 
		
		LOGGER.info("Producto buscado: {}", producto);
		
		//mandar el objeto a la vista html, el model es como un objeto request
		model.addAttribute("producto", producto);
		return "productos/edit";
	}
	
	//Envia los datos del formulario edit, guarda los datos en la bd y redirige a la lista de productos
	@PostMapping("/update")
	public String update(Producto producto) {
		productoService.update(producto);
		return "redirect:/productos";
	}
	
		//@PathVariable es de spring. Mapea el id en la url y la pasa en la variable que está continua (o sea, rellena el parametro id)

	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		productoService.delete(id);
		return "redirect:/productos";
	}

}
