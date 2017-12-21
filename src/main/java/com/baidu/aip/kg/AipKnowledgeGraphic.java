/*
 * Copyright 2017 Baidu, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.baidu.aip.kg;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.http.AipRequest;
import org.json.JSONObject;

import java.util.HashMap;

public class AipKnowledgeGraphic extends BaseClient {

    public AipKnowledgeGraphic(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 创建任务接口   
     * 创建一个新的信息抽取任务
     *
     * @param name - 任务名字
     * @param templateContent - json string 解析模板内容
     * @param inputMappingFile - 抓取结果映射文件的路径
     * @param outputFile - 输出文件名字
     * @param urlPattern - url pattern
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   limit_count 限制解析数量limit_count为0时进行全量任务，limit_count&gt;0时只解析limit_count数量的页面
     * @return JSONObject
     */
    public JSONObject createTask(String name, String templateContent, String inputMappingFile, String outputFile, String urlPattern, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("name", name);
        
        request.addBody("template_content", templateContent);
        
        request.addBody("input_mapping_file", inputMappingFile);
        
        request.addBody("output_file", outputFile);
        
        request.addBody("url_pattern", urlPattern);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(KnowledgeGraphicConsts.CREATE_TASK);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 更新任务接口   
     * 更新任务配置，在任务重新启动后生效
     *
     * @param id - 任务ID
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   name 任务名字
     *   template_content json string 解析模板内容
     *   input_mapping_file 抓取结果映射文件的路径
     *   url_pattern url pattern
     *   output_file 输出文件名字
     * @return JSONObject
     */
    public JSONObject updateTask(int id, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("id", id);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(KnowledgeGraphicConsts.UPDATE_TASK);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 获取任务详情接口   
     * 根据任务id获取单个任务的详细信息
     *
     * @param id - 任务ID
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject getTaskInfo(int id, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("id", id);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(KnowledgeGraphicConsts.TASK_INFO);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 以分页的方式查询当前用户所有的任务信息接口   
     * 该请求用于菜品识别。即对于输入的一张图片（可正常解码，且长宽比适宜），输出图片的菜品名称、卡路里信息、置信度。
     *
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     *   id 任务ID，精确匹配
     *   name 中缀模糊匹配,abc可以匹配abc,aaabc,abcde等
     *   status 要筛选的任务状态
     *   page 页码
     *   per_page 页码
     * @return JSONObject
     */
    public JSONObject getUserTasks(HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(KnowledgeGraphicConsts.TASK_QUERY);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 启动任务接口   
     * 启动一个已经创建的信息抽取任务
     *
     * @param id - 任务ID
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject startTask(int id, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("id", id);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(KnowledgeGraphicConsts.TASK_START);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 查询任务状态接口   
     * 查询指定的任务的最新执行状态
     *
     * @param id - 任务ID
     * @param options - 可选参数对象，key: value都为string类型
     * options - options列表:
     * @return JSONObject
     */
    public JSONObject getTaskStatus(int id, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        
        request.addBody("id", id);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(KnowledgeGraphicConsts.TASK_STATUS);
        postOperation(request);
        return requestServer(request);
    }

}