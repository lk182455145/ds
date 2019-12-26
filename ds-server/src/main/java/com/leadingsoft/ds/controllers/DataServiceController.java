package com.leadingsoft.ds.controllers;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leadingsoft.ds.entities.Application;
import com.leadingsoft.ds.services.ApplicationService;
import com.leadingsoft.ds.services.DataService;

/**
 * 数据集服务的相应请求<br />
 * 数据服务接受两种格式的响应请求，一种是Spring
 * MVC的标准分页请求，该请求以page,size作为分页参数，page最小是0,size是每页大小,参见{@link Pageable} <br />
 * 另外一种是以page,rows作为分页参数，使用该参数组合时，page最小是1,rows是每页大小<br />
 * 无论使用以上两种组合中的哪一种，所有分页参数都不是必须的<br/>
 * 接口会根据请求参数中是否有rows参数决定使用哪种组合。<br />
 * 意思就是，当我的请求参数中不带rows参数时，会默认使用page,size作为分页参数,<br />
 * 在该接口模式下，page的最小值是0,即page=0获取第一页,page=2获取第三页<br />
 * 
 * size和rows的默认大小是10,最大值不能超过1000
 * 
 * @author LiDong
 *
 * @see Pageable
 * @see PageabelDefault
 * 
 */
@RestController
@RequestMapping(value = { "dcds/service", "service", "/s/dcds/service", "/ds/s/dcds/service" })
public class DataServiceController {

	@Autowired
	private DataService dataService;

	@Autowired
	private ApplicationService appService;

	@GetMapping(value = "{serviceName}")
	public Page<Map<String, Object>> query(
			@PathVariable("serviceName") String service,
			@RequestParam(value = "draw", required = false) String draw,
			@RequestParam("token") String token,
			@PageableDefault(page = 0, size = 10) Pageable pageable,
			@RequestParam MultiValueMap<String, String> params) throws Exception {
//		params = remove(params);
		if (pageable.getPageSize() > 1000) {
			throw new Exception("分页大小不能超所1000条");
		}
		if (!checnAuth(token)) {
			throw new Exception("Token参数错误");
		}
		try {
			return query(draw, service, params, pageable);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

//	@GetMapping(value = "{serviceName}", params = { "rows" })
//	public ResultDto<QueryResult> query(
//			@PathVariable("serviceName") String service,
//			@RequestParam(value = "draw", required = false) String draw,
//			@RequestParam("token") String token,
//			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
//			@RequestParam(value = "rows", required = false, defaultValue = "10") int rows,
//			@RequestParam MultiValueMap<String, String> params) {
//		params = remove(params);
//
//		if (rows > 1000) {
//			return ResultDto.failure("The rows param is to large");
//		}
//		if (!checnAuth(token)) {
//			return ResultDto.failure("The Token param is to error");
//		}
//		Pageable pageable = PageRequest.of(page - 1, rows);
//		try {
//			return query(draw, service, params, pageable);
//		} catch (Exception e) {
//			return ResultDto.failure(e.getMessage());
//		}
//	}

	private Page<Map<String, Object>> query(String draw, String service, MultiValueMap<String, String> params,
			Pageable pageable) {
		return dataService.queryData(service, params, pageable);
//		QueryResult result = dataService.queryData(service, params, pageable);
//		result.setDraw(draw);
//		return ResultDto.success(result);
	}

//	private MultiValueMap<String, String> remove(MultiValueMap<String, String> params) {
//		params.remove("draw");
//		params.remove("token");
//		params.remove("page");
//		params.remove("rows");
//		params.remove("size");
//		return params;
//	}

	// 检测应用是否授权
	private boolean checnAuth(String token) {
		List<Application> apps = appService.listAll();
		for (Application app : apps) {
			if (StringUtils.equalsIgnoreCase(app.getId(), token)) {
				return true;
			}
		}
		return false;
	}
}
