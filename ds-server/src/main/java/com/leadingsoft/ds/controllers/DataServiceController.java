package com.leadingsoft.ds.controllers;

import java.util.Map;

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

    @GetMapping(value = "{serviceName}")
    public Page<Map<String, Object>> query(
            @PathVariable("serviceName") String service,
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam MultiValueMap<String, String> params) {
        params = remove(params);
        try {
            return dataService.queryData(service, params, pageable);
        } catch (Exception e) {
            throw e;
        }
    }

    private MultiValueMap<String, String> remove(MultiValueMap<String, String> params) {
        params.remove("draw");
        params.remove("token");
        params.remove("page");
        params.remove("rows");
        params.remove("size");
        return params;
    }

}
