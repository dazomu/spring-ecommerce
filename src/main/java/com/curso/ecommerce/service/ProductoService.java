package com.curso.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Producto;

//aqui se definen los metodos CRUD

public interface ProductoService {

	public Producto save(Producto producto);
	public Optional<Producto> get(Integer id); //el optional permite consultar si el objeto que se esta buscando en la bd existe o no
	public void update(Producto producto);
	public void delete(Integer id);
	public List<Producto> findAll();
}
