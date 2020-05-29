package com.leadingsoft.ds.controllers;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dm.common.exception.DataNotExistException;
import com.leadingsoft.ds.converters.SvcConverter;
import com.leadingsoft.ds.dto.SqlMetaData;
import com.leadingsoft.ds.dto.SvcDto;
import com.leadingsoft.ds.entities.Svc;
import com.leadingsoft.ds.services.SvcService;

@RestController
@RequestMapping("/svc")
public class SvcController {

    @Autowired
    private SvcService svcService;

    @Autowired
    private SvcConverter svcConverter;

    @PostMapping
    public SvcDto save(@RequestBody SvcDto svc) {
        Svc svc_ = svcService.save(svc);
        return svcConverter.toDto(svc_);
    }

    @GetMapping("{id}")
    public SvcDto get(@PathVariable("id") Long id) {
        Optional<Svc> svc = svcService.get(id);
        return svc.map(svcConverter::toDto).orElseThrow(DataNotExistException::new);
    }

    @PutMapping("{id}")
    public SvcDto update(@PathVariable("id") Long id, @RequestBody SvcDto _svc) {
        Svc svc = svcService.update(id, _svc);
        return svcConverter.toDto(svc);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        svcService.delete(id);
    }

    @GetMapping(params = { "draw" })
    public Page<SvcDto> list(
            @RequestParam("draw") Long draw,
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault Pageable pageable) {
        Page<Svc> results = svcService.list(search, pageable);
        return results.map(svcConverter::toTableResultDto);
    }

    @PostMapping("meta")
    public SqlMetaData metas(@RequestBody SvcDto svc) throws SQLException {
        return svcService.getMeta(svc);
    }

    @GetMapping("meta/{id}")
    public SqlMetaData metas(@PathVariable("id") Svc svc) {
        try {
            return svcService.getMeta(svc.getConnection(), svc.getSql());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
