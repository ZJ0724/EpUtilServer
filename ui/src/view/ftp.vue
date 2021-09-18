<template>
    <div v-loading="loading">
        <div style="display: flex;">
            <div style="width: 100%;">
                <ep-select v-model="ftpConnect.id" placeholder="请选择" @open="getFtpConfigApi()">
                    <ep-select-item v-for="(item, key) in ftpConfig" :key="key" :index="item.id" :label="item.name + ' [' + item.host + ']'"></ep-select-item>
                </ep-select>
            </div>
            <div style="margin-left: 10px;display: flex;">
                <ep-button @click="connectFtpApi().then(() => {
                    alterUtil.success('成功');
                    getLsApi();
                }).catch((m) => {
                    alterUtil.error(m);
                })" style="width: 100px;" type="success">连接</ep-button>
                <ep-button @click="ftpConfigPopup.open()" style="width: 100px;">维护</ep-button>
            </div>
        </div>

        <div v-if="ftpConnect.path !== null" style="margin-top: 50px;">
            <div style="display: flex;">
                <ep-button @click="_back()" icon="arrow-left-a" style="width: 60px;"></ep-button>
                <ep-button @click="getLsApi()" icon="refresh" style="width: 60px;margin-left: 0;"></ep-button>
                <ep-input v-model="ftpConnect.path"></ep-input>
                <ep-button @click="ftpPathConfigPopup.open()" icon="navicon-round" style="width: 60px;"></ep-button>
            </div>

            <div style="margin-top: 5px;" class="panel">
                <ep-table :data="ftpConnect.files" :height="550" size="small" @row-dblclick="(event, row) => {
                    if (!row.file) {
                        if (ftpConnect.path === '/') {
                            ftpConnect.path = ftpConnect.path + row.name;
                        } else {
                            ftpConnect.path = ftpConnect.path + '/' + row.name;
                        }
                        getLsApi();
                    } else {
                        alterUtil.confirm('确认下载？').then(() => {
                            ftpConnect.name = row.name;
                            download().then(() => {
                                alterUtil.success('成功');
                            }).catch((m) => {
                                alterUtil.error(m);
                            });
                        }).catch(() => {});
                    }
                }">
                    <ep-table-item width="20">
                        <template slot-scope="props">
                            <div>
                                <img style="width: 40px;height: 40px;" v-if="props.row.file" src="../img/file.png" alt="" />
                                <img style="width: 40px;height: 40px;" v-if="!props.row.file" src="../img/dir.png" alt="" />
                            </div>
                        </template>
                    </ep-table-item>
                    <ep-table-item sortable column="name" title="名称"></ep-table-item>
                    <ep-table-item sortable column="updateTime" title="更新时间"></ep-table-item>
                    <ep-table-item column="size" title="大小"></ep-table-item>
                </ep-table>
            </div>
        </div>

        <!-- 维护弹框 -->
        <ep-modal title="维护" v-model="ftpConfigPopup.display" width="1000px" :close-on-press-escape="false" :wrap-close	="false">
            <div>
                <div>
                    <ep-button @click="() => {
                        ftpConfig.push({});
                    }" size="small" type="primary">新增</ep-button>
                </div>

                <ep-table style="margin-top: 20px;" size="small" :data="ftpConfig" can-edit>
                    <ep-table-item column="name" title="名称"></ep-table-item>
                    <ep-table-item column="type" title="类型"></ep-table-item>
                    <ep-table-item column="host" title="地址"></ep-table-item>
                    <ep-table-item column="port" title="端口"></ep-table-item>
                    <ep-table-item column="username" title="用户名"></ep-table-item>
                    <ep-table-item column="password" title="密码"></ep-table-item>
                    <ep-table-item column="action" title="操作">
                        <template slot-scope="props">
                            <div style="display: flex;">
                                <ep-button @click="ftpConfigPopup.save(props.row)" type="text">保存</ep-button>
                                <ep-button @click="ftpConfigPopup.delete(props.row)" style="color: #FF4D4F;" type="text">删除</ep-button>
                            </div>
                        </template>
                    </ep-table-item>
                </ep-table>
            </div>
        </ep-modal>

        <!-- ftpPath弹框 -->
        <ep-modal title="路径" v-model="ftpPathConfigPopup.display" width="1000px" :close-on-press-escape="false" :wrap-close	="false">
            <div>
                <div>
                    <ep-button @click="() => {
                        ftpPathConfig.push({});
                    }" size="small" type="primary">新增</ep-button>
                </div>

                <ep-table style="margin-top: 20px;" size="small" :data="ftpPathConfig" can-edit>
                    <ep-table-item column="name" title="名称"></ep-table-item>
                    <ep-table-item column="path" title="路径"></ep-table-item>
                    <ep-table-item column="action" title="操作">
                        <template slot-scope="props">
                            <div style="display: flex;">
                                <ep-button @click="() => {
                                    ftpConnect.path = props.row.path;
                                    getLsApi();
                                    ftpPathConfigPopup.display = false;
                                }" type="text" style="color: #27AE60">进入</ep-button>
                                <ep-button @click="() => {
                                    saveFtpPathConfig = props.row;
                                    saveFtpPathConfigApi().then(() => {
                                        alterUtil.success('成功');
                                    }).catch((m) => {
                                        alterUtil.error(m);
                                    });
                                }" type="text">保存</ep-button>
                                <ep-button @click="() => {
                                    deleteFtpPathConfig = props.row;
                                    deleteFtpPathConfigApi().then(() => {
                                        alterUtil.success('成功');
                                    }).catch((m) => {
                                        alterUtil.error(m);
                                    });
                                }" style="color: #FF4D4F;" type="text">删除</ep-button>
                            </div>
                        </template>
                    </ep-table-item>
                </ep-table>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import ftpConfigApi from "../api/ftpConfigApi.js";
    import alterUtil from "../util/alterUtil.js";
    import zj0724Common from "zj0724-common";
    import ftpPathConfigApi from "../api/ftpPathConfigApi.js";

    export default {
        name: "ftp.vue",

        data() {
            let current = this;

            return {
                loading: false,

                ftpConfig: [],

                saveFtpConfig: {
                    id: null,
                    type: null,
                    name: null,
                    host: null,
                    port: null,
                    username: null,
                    password: null
                },

                deleteFtpConfig: null,

                ftpConnect: {
                    id: null,
                    path: null,
                    name: null,
                    files: []
                },

                // ftpPathConfig
                ftpPathConfig: [],

                saveFtpPathConfig: {},

                deleteFtpPathConfig: {},

                ftpConfigPopup: {
                    display: false,

                    open() {
                        this.display = true;
                        current.getFtpConfigApi();
                    },

                    save(data) {
                        current.saveFtpConfig = data;
                        current.saveFtpConfigApi().then(() => {
                            alterUtil.success("成功");
                            current.getFtpConfigApi();
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    },

                    delete(data) {
                        current.deleteFtpConfig = data;
                        current.deleteFtpConfigApi().then(() => {
                            alterUtil.success("成功");
                            current.getFtpConfigApi();
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }
                },

                ftpPathConfigPopup: {
                    display: false,

                    open() {
                        this.display = true;
                        current.queryFtpPathConfigApi();
                    }
                },

                alterUtil
            };
        },

        methods: {
            getFtpConfigApi() {
                ftpConfigApi.getAll().then((data) => {
                    this.ftpConfig = data;
                });
            },

            async saveFtpConfigApi() {
                return await ftpConfigApi.save(this.saveFtpConfig).catch((m) => {
                    return Promise.reject(m);
                });
            },

            async deleteFtpConfigApi() {
                return await ftpConfigApi.delete(this.deleteFtpConfig).catch((m) => {
                    return Promise.reject(m);
                });
            },

            async connectFtpApi() {
                this.loading = true;
                return await ftpConfigApi.connect({
                    id: this.ftpConnect.id
                }).then(() => {
                    this.ftpConnect.path = "/";
                }).catch((m) => {
                    zj0724Common.variable.clean(this.ftpConnect);
                    return Promise.reject(m);
                }).finally(() => {
                    this.loading = false;
                });
            },

            async getLsApi() {
                this.loading = true;
                return await ftpConfigApi.ls({
                    id: this.ftpConnect.id,
                    path: this.ftpConnect.path
                }).then((data) => {
                    this.ftpConnect.files = data;
                }).catch((m) => {
                    zj0724Common.variable.clean(this.ftpConnect);
                    this.ftpConnect.id = null;
                    alterUtil.error(m);
                }).finally(() => {
                    this.loading = false;
                });
            },

            async download() {
                this.loading = true;
                return await ftpConfigApi.download({
                    id: this.ftpConnect.id,
                    path: this.ftpConnect.path,
                    name: this.ftpConnect.name
                }).catch((m) => {
                    return Promise.reject(m);
                }).finally(() => {
                    this.loading = false;
                });
            },

            queryFtpPathConfigApi() {
                ftpPathConfigApi.query({
                    filter: {
                        ftpConfigId: this.ftpConnect.id
                    }
                }).then((data) => {
                    this.ftpPathConfig = data.data;
                });
            },

            async saveFtpPathConfigApi() {
                this.saveFtpPathConfig.ftpConfigId = this.ftpConnect.id;
                return await ftpPathConfigApi.save(this.saveFtpPathConfig).then(() => {
                    this.queryFtpPathConfigApi();
                }).catch((m) => {
                    return Promise.reject(m);
                });
            },

            async deleteFtpPathConfigApi() {
                return await ftpPathConfigApi.delete(this.deleteFtpPathConfig).then(() => {
                    this.queryFtpPathConfigApi();
                }).catch((m) => {
                    return Promise.reject(m);
                });
            },

            _back() {
                if (this.ftpConnect.path !== "/") {
                    let split = this.ftpConnect.path.split("/");
                    let newPath = "";
                    for (let i = 0; i < split.length - 1; i++) {
                        if (split[i] === "") {
                            continue;
                        }
                        newPath = newPath + "/" + split[i];
                    }
                    if (newPath === "") {
                        newPath = "/";
                    }
                    this.ftpConnect.path = newPath;
                    this.getLsApi();
                }
            }
        }
    }
</script>