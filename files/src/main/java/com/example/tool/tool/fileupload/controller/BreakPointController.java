package com.example.tool.tool.fileupload.controller;

import com.example.tool.tool.fileupload.constants.Constants;
import com.example.tool.tool.fileupload.dto.ResultVo;
import com.example.tool.tool.fileupload.enums.ResultStatus;
import com.example.tool.tool.fileupload.util.MultipartFileParam;
import com.example.tool.tool.fileupload.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 断点续传上传大文件类
 */
@Api(tags = "断点续传上传大文件类")
@Controller
@RequestMapping(value = "/break")
public class BreakPointController {

    private Logger logger = LoggerFactory.getLogger(BreakPointController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private StorageService storageService;

    @ApiOperation(value = "跳转到文件上传页面")
    @GetMapping(value = "/uploadPage")
    public String fileUploadPage() {
        return "webupload/uploadPage";
    }

    /**
     * 秒传判断，断点判断
     *
     * @return
     */
    @ApiOperation(value = "秒传判断，断点判断")
    @RequestMapping(value = "checkFileMd5", method = RequestMethod.POST)
    @ResponseBody
    public Object checkFileMd5(String md5) throws IOException {
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = stringRedisTemplate.opsForHash();
        Object processingObj = stringObjectObjectHashOperations.get(Constants.FILE_UPLOAD_STATUS, md5);
        if (processingObj == null) {
            return new ResultVo(ResultStatus.NO_HAVE);
        }
        String processingStr = processingObj.toString();
        boolean processing = Boolean.parseBoolean(processingStr);
        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
        if (processing) {
            return new ResultVo(ResultStatus.IS_HAVE, value);
        } else {
            File confFile = new File(value);
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
            return new ResultVo<>(ResultStatus.ING_HAVE, missChunkList);
        }
    }

    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity fileUpload(MultipartFileParam param, HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            logger.info("上传文件start。");
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                storageService.uploadFileByMappedByteBuffer(param);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("文件上传失败。{}", param.toString());
            }
            logger.info("上传文件end。");
        }
        return ResponseEntity.ok().body("上传成功。");
    }

}