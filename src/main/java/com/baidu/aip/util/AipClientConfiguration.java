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
package com.baidu.aip.util;


import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

public class AipClientConfiguration {

    // 连接超时设置
    private int connectionTimeoutMillis;
    private int socketTimeoutMillis;
    private Proxy proxy;

    public AipClientConfiguration() {
        this.connectionTimeoutMillis = 0;
        this.socketTimeoutMillis = 0;
        this.proxy = Proxy.NO_PROXY;
    }

    public AipClientConfiguration(int connectionTimeoutMillis, int socketTimeoutMillis, Proxy proxy) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
        this.socketTimeoutMillis = socketTimeoutMillis;
        this.proxy = proxy;
    }

    public int getConnectionTimeoutMillis() {
        return connectionTimeoutMillis;
    }

    public void setConnectionTimeoutMillis(int connectionTimeoutMillis) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
    }

    public int getSocketTimeoutMillis() {
        return socketTimeoutMillis;
    }

    public void setSocketTimeoutMillis(int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setProxy(String host, int port, Proxy.Type type) {
        SocketAddress addr = new InetSocketAddress(host, port);
        this.proxy = new Proxy(type, addr);
    }
}
