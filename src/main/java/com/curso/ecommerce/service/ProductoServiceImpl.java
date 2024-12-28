package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.repository.ProductoRepository;

//El @Service hace que Spring identifique a la clase como un Bean, para que luego con Autowired se agreguen estas implementaciones al objeto especificado despues
@Service 
//recuerda q aca se usa "implements" porque se esta heredando de un Interface
public class ProductoServiceImpl implements ProductoService{

	@Autowired //se   utiliza para realizar la inyección de dependencias automáticamente.
	private ProductoRepository productoRepository;
	
	
	@Override
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> get(Integer id) {
		return productoRepository.findById(id);
	}

	@Override
	public void update(Producto producto) {
		productoRepository.save(producto); 
		//el metodo save es el mismo q del primer metodo
		//pero si nota que ya existe el objeto en la bd, lo actualiza en funcion a su id
	}

	@Override
	public void delete(Integer id) {
		productoRepository.deleteById(id);
		
	}

	@Override
	public List<Producto> findAll() {
		
		return productoRepository.findAll();
	}

}
