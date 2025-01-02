package com.curso.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileService {

	private String folder = "images//";
	
	
	//La clase del parametro es el tipo file
	public String saveImage(MultipartFile file) throws IOException {
		
		if(!file.isEmpty()) {
			//Pasar la imagen a bytes
			byte [] bytes=file.getBytes();
			//Asignar ruta y nombre del file
			Path path = Paths.get(folder+file.getOriginalFilename());
			//Pasar el file convertido a bytes en la ruta (path)
			Files.write(path, bytes);
			return file.getOriginalFilename();
			
		}
		
		return "default.jpg";
	}
	
	
	//recibe el nombre de la imagen
	public void deleteImage(String nombre) {
		String ruta = "images//";
		File file = new File(ruta+nombre);
		
		
	}
	
}
