package com.curso.ecommerce.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

	@Autowired //el contenedor de spring se encargará de q esta variable este instanciada
	private ProductoService productoService;
	
	@Autowired
	private UploadFileService upload;
	
	
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
	public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		LOGGER.info("Este es el objeto producto {}", producto); //ese objeto lo muestra como toString
		
		Usuario u= new Usuario(1, "", "", "", "", "", "", "");
		producto.setUsuario(u);
		
		//imagen
		if (producto.getId()==null) { //cuando se crea un producto
			String nombreImagen= upload.saveImage(file);
			producto.setImagen(nombreImagen);
		
		}else {
			
		}
		
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
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		
		
		
		Producto p = new Producto();
		p=productoService.get(producto.getId()).get();
		
		if(file.isEmpty()) { //editamos el producto pero no cambiamos la imagen
			
			producto.setImagen(p.getImagen());
			
		}else { //cambia la imagen
	
			//eliminar cuando no sea la imagen por defecto
			if (!p.getImagen().equals("default.jpg")) {
				upload.deleteImage(p.getImagen());
				
			}
			
			String nombreImagen= upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		
		producto.setUsuario(p.getUsuario());
		productoService.update(producto);
		return "redirect:/productos";
	}
	
		//@PathVariable es de spring. Mapea el id en la url y la pasa en la variable que está continua (o sea, rellena el parametro id)

	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		Producto p = new Producto();
		p=productoService.get(id).get();
		
		//eliminar cuando no sea la imagen por defecto
		if (!(p.getImagen().equals("default.jpg"))) {
			upload.deleteImage(p.getImagen());
			
		}
		
		
		productoService.delete(id);
		return "redirect:/productos";
	}

}
