package com.leadingsoft.ds.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dm.common.exception.DataNotExistException;
import com.leadingsoft.ds.converters.DbConnectionConverter;
import com.leadingsoft.ds.dto.DbConnectionDto;
import com.leadingsoft.ds.entities.DbConnection;
import com.leadingsoft.ds.services.DataSourceService;
import com.leadingsoft.ds.services.DbConnectionService;

@RestController
@RequestMapping("/cnn")
public class ConnectionController {

	@Autowired
	private DbConnectionService cnnService;

	@Autowired
	private DbConnectionConverter cnnConverter;

	@Autowired
	private DataSourceService dataSourceService;

	/**
	 * 新增一条连接信息
	 * 
	 * @param connection
	 * @return
	 */
	@PostMapping
	public DbConnectionDto save(@RequestBody DbConnectionDto connection) {
		DbConnection cnn = cnnService.save(connection);
		return cnnConverter.toDto(cnn);
	}

	@PutMapping("{id}")
	public DbConnectionDto update(@PathVariable("id") Long id, @RequestBody DbConnectionDto connection) {
		DbConnection cnn = cnnService.update(id, connection);
		return cnnConverter.toDto(cnn);
	}

	@GetMapping("{id}")
	public DbConnectionDto get(@PathVariable("id") Long id) {
		Optional<DbConnection> cnn = cnnService.get(id);
		return cnn.map(cnnConverter::toDto).orElseThrow(DataNotExistException::new);
	}

	@GetMapping(params = { "draw" })
	public Page<DbConnectionDto> list(
			@RequestParam("draw") Long draw,
			@PageableDefault Pageable pageable) {
			Page<DbConnection> results = cnnService.list(pageable);
			return results.map(cnnConverter::toDto);
	}

	@GetMapping
	public List<DbConnectionDto> list() {
		List<DbConnection> cnns = cnnService.listAll();
		return cnnConverter.toDto(cnns);
	}

	@GetMapping("{cnn}/state")
	public Boolean state(@PathVariable("cnn") Long id) throws SQLException {
		Optional<DbConnection> cnn = cnnService.get(id);
		return dataSourceService.checkState(cnn.get());
	}

	@GetMapping(value = "state")
	public Boolean state(@ModelAttribute DbConnectionDto cnn) throws SQLException {
		return dataSourceService.checkState(cnn);
	}

}
