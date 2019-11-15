package ${package.Controller};





import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hcop.core.common.annotation.Token;
import com.hcop.core.common.validate.ValidatedGroup;
import com.hcop.core.config.BaseConstant;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import com.hcop.core.config.BaseConstant;
import com.hcop.core.modular.base.common.BaseJsonDTO;
import com.hcop.core.modular.base.datatables.DataTablesPageDTO;
import com.hcop.core.modular.base.datatables.DataTablesReqesutDTO;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceImplName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * ${table.comment} 控制器
 * </p>
 *
 * @author ${author}
 * @date ${date}
 */
@Api(description = "${table.comment}控制器")
@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?uncap_first}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    private static final String PREFIX = "/${package.ModuleName}/${entity?uncap_first}/";

    @Autowired
    private ${table.serviceImplName} ${table.serviceImplName?uncap_first};



    /**
     * 获取分页数据（datatables）
     * @param request
     * @return
     */
    @ApiOperation("获取分页数据（datatables）")
    @PostMapping(value = "/page")
    @ResponseBody
    public DataTablesPageDTO<${entity}> flowPage(HttpServletRequest request) {

        DataTablesReqesutDTO reqesutDTO = buildDataTablesReqesut(request);
        Page page = buildPage(reqesutDTO);
        EntityWrapper<${entity}> entityWrapper = buildEntityWrapper(reqesutDTO, ${entity}.class, new EntityWrapper(), "");

        page = ${table.serviceImplName?uncap_first}.selectPage(page, entityWrapper);

        return new DataTablesPageDTO<>(page);
    }

    /**
     * 跳转到列表页面
     *
     * @param model
     * @return
     */
    @ApiOperation("跳转到列表页面")
    @GetMapping(value = {"/index"})
    public String node(Model model) {
        return PREFIX + "${entity?uncap_first}";
    }

    /**
     * 跳转到新增页面
     * @param model
     * @return
     */
    @ApiOperation("跳转到新增页面")
    @Token(save = true)
    @GetMapping(value = "/${entity?uncap_first}-add")
    public String ${entity?uncap_first}Add(Model model) {
        model.addAttribute(BaseConstant.MODEL_ATTR_ACTION, "add");
        model.addAttribute(BaseConstant.MODEL_ATTR_OBJECT, new ${entity}());
        return PREFIX + "${entity?uncap_first}-form";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @ApiOperation("跳转到修改页面")
    @Token(save = true)
    @GetMapping(value = "/${entity?uncap_first}-update/{id}")
    public String ${entity?uncap_first}Update(@PathVariable(name = "id") String id, Model model) {
        model.addAttribute(BaseConstant.MODEL_ATTR_ACTION, "update");
        model.addAttribute(BaseConstant.MODEL_ATTR_OBJECT, ${table.serviceImplName?uncap_first}.selectById(id));
        return PREFIX + "${entity?uncap_first}-form";
    }

    /**
     * 跳转到详情页面
     * @param id
     * @param model
     * @return
     */
    @ApiOperation("跳转到详情页面")
    @GetMapping(value = "/${entity?uncap_first}-detail/{id}")
    public String detail(@PathVariable(name = "id") String id, Model model) {
        model.addAttribute(BaseConstant.MODEL_ATTR_OBJECT, ${table.serviceImplName?uncap_first}.selectById(id));
        return PREFIX + "${entity?uncap_first}-detail";
    }

    /**
     * 新增${table.comment}
     */
    @ApiOperation("新增${table.comment}")
    @Token(remove = true)
    @RequiresPermissions("${package.ModuleName}:${entity?uncap_first}:add")
    @PostMapping(value = "/add")
    @ResponseBody
    public Object add(${entity} ${entity?uncap_first}) {
        return BaseJsonDTO.init(${table.serviceImplName?uncap_first}.insert(${entity?uncap_first}));
    }


    /**
     * 修改${table.comment}
     */
    @ApiOperation("修改${table.comment}")
    @PostMapping(value = "/update")
    @RequiresPermissions("${package.ModuleName}:${entity?uncap_first}:update")
    @ResponseBody
    public Object update(${entity} ${entity?uncap_first}) {
        String id = ${entity?uncap_first}.getId();
        ${entity} data = ${table.serviceImplName?uncap_first}.selectById(id);
        String[] notNullPropertyNames = ReflectUtils.getNotNullPropertyNames(${entity?uncap_first});
        BeanUtils.copyProperties(data, ${entity?uncap_first}, notNullPropertyNames);

        return BaseJsonDTO.init(${table.serviceImplName?uncap_first}.updateAllColumnById(${entity?uncap_first}));
    }

    /**
     * 删除${table.comment}
     * @param id
     * @return
     */
    @ApiOperation("删除${table.comment}")
    @PostMapping(value = "/delete/{id}")
    @RequiresPermissions("${package.ModuleName}:${entity?uncap_first}:delete")
    @ResponseBody
    public Object delete(@PathVariable(name = "id") String id) {
        return BaseJsonDTO.init(${table.serviceImplName?uncap_first}.deleteById(id));
    }
}
</#if>
