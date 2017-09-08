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
import java.util.Map;

public class AipKnowledgeGraphic extends BaseClient {

    public AipKnowledgeGraphic(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    /**
     * 获取用户所有任务.
     * @param options
     * <p>
     *     可填入key列表:.
     *     id: 任务id.
     *     name: 任务名字，中缀模糊匹配,abc可以匹配abc,aaabc,abcde等.
     *     status: 要筛选的任务状态，包括CREATED/READY/WAITING/RUNNING/SUCCESS/KILLED/FAILED.
     *     page: 页码.
     *     per_page: 每页数量.
     * </p>
     * @return json对象，返回任务列表
     */
    public JSONObject getUserTasks(HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(KnowledgeGraphicConsts.KG_GET_ALL_TASKS_URL);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 获取单个任务详情
     * @param taskId 任务ID
     * @return json对象，返回任务详情
     */
    public JSONObject getTaskInfo(int taskId) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("id", taskId);
        request.setUri(KnowledgeGraphicConsts.KG_GET_TASK_URL);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 创建一个新的信息抽取任务
     * @param name 任务名称
     * @param tplStr json string 解析模板内容
     * @param inputMapping 抓取结果映射文件的路默认值mapping_file
     * @param outputFile 输出文件名字
     * @param urlPattern url pattern
     * @param options 可选参数
     * <p>可填入key:
     *      limit_count: 限制解析数量limit_count为0时进行全量任务，limit_count>0时只解析limit_count数量的页面
     * </p>
     * @return json对象，返回任务id
     */
    public JSONObject createTask(String name,
                                 String tplStr,
                                 String inputMapping,
                                 String outputFile,
                                 String urlPattern,
                                 HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);

        request.addBody("name", name);
        request.addBody("template_content", tplStr);
        request.addBody("input_mapping_file", inputMapping);
        request.addBody("url_pattern", urlPattern);
        request.addBody("output_file", outputFile);
        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(KnowledgeGraphicConsts.KG_CREATE_TASK_URL);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 更新任务配置，在任务重新启动后生效
     * @param id 任务id
     * @param options 可选参数
     * <p>
     *     可填入key: <br>
     *     name: 任务名称.
     *     template_content: json string 解析模板内容.
     *     input_mapping_file: 抓取结果映射文件的路径.
     *     url_pattern: url pattern.
     *     output_file: 输出文件名
     * </p>
     * @return json对象，返回log_id
     */
    public JSONObject updateTask(int id, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("id", id);
        if (options != null) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                request.addBody(entry.getKey(), entry.getValue());
            }
        }
        request.setUri(KnowledgeGraphicConsts.KG_UPDATE_TASK_URL);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 启动一个已经创建的信息抽取任务.
     * @param taskId 任务ID
     * @return json对象
     */
    public JSONObject startTask(int taskId) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("id", taskId);
        request.setUri(KnowledgeGraphicConsts.KG_START_TASK_URL);
        postOperation(request);
        return requestServer(request);
    }

    /**
     * 查询指定的任务的最新执行状态
     * @param taskId 任务ID
     * @return json对象
     */
    public JSONObject getTaskStatus(int taskId) {
        AipRequest request = new AipRequest();
        preOperation(request);
        request.addBody("id", taskId);
        request.setUri(KnowledgeGraphicConsts.KG_TASK_STATUS_URL);
        postOperation(request);
        return requestServer(request);
    }
}
