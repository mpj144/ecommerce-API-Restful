package com.residencia.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.ecommerce.entities.ProdutoPedido;
import com.residencia.ecommerce.services.ProdutoPedidoService;

@RestController
@RequestMapping("/produtoPedido")
public class ProdutoPedidoController {

	@Autowired
	ProdutoPedidoService produtoPedidoService;

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoPedido> findById(@PathVariable Integer id) {
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(produtoPedidoService.findById(id), headers, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<ProdutoPedido>> findAll() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(produtoPedidoService.findAll(), headers, HttpStatus.OK);
	}

	@GetMapping("/count")
	public Long count() {
		return produtoPedidoService.count();
	}

	@PostMapping
	public ResponseEntity<ProdutoPedido> save(@RequestBody ProdutoPedido produtoPedido) {

		HttpHeaders headers = new HttpHeaders();

		ProdutoPedido novoProdutoPedido = produtoPedidoService.save(produtoPedido);

		if (null != novoProdutoPedido)
			return new ResponseEntity<>(novoProdutoPedido, headers, HttpStatus.OK);
		else
			return new ResponseEntity<>(novoProdutoPedido, headers, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/{id}")
	public ProdutoPedido update(@RequestBody ProdutoPedido produtoPedido, Integer id) {
		return produtoPedidoService.update(produtoPedido, id);
	}

	@DeleteMapping("/{id}")
	public void DeleteById(@PathVariable Integer id) {
		produtoPedidoService.delete(id);
	}

}