<template>
    <div class="panel" v-loading="loading">
        <!-- 模块 -->
        <div v-if="variable.isEmpty(module) && !loading">
            <div>
                <ep-button @click="saveApiModulePopup.open(null)" type="success">新增模块</ep-button>
            </div>
            <ep-divider></ep-divider>

            <!-- 模块列表 -->
            <div v-for="(item, key) in apiModules" :key="key">
                <div style="display: flex;">
                    <div @click="_moduleAction(item.name)" class="title module" style="display: flex;align-items: center;">
                        {{item.name}}
                    </div>
                    <div style="width: 100%;display: flex;flex-direction: row-reverse;">
                        <div>
                            <ep-button @click="saveApiModulePopup.open(item)" type="text">编辑</ep-button>
                            <ep-button @click="apiVariablePopup.open(item.id)" style="color: #E7963B;" type="text">变量维护</ep-button>
                            <ep-popconfirm @confirm="() => {
                            deleteApiModule.id = item.id;
                            deleteApiModuleApi().then(() => {
                                alterUtil.success('完成');
                                init();
                            }).catch((m) => {
                                alterUtil.error(m);
                            });
                        }" title="确定删除？">
                                <ep-button style="color: #FF4D4F;" slot="reference" type="text">删除</ep-button>
                            </ep-popconfirm>
                        </div>
                    </div>
                </div>

                <div style="margin-top: 20px;display: flex;">
                    <div>
                        <ep-tag v-if="item.apiType === 'POST'" type="primary">POST</ep-tag>
                        <ep-tag v-if="item.apiType === 'GET'" type="success">GET</ep-tag>
                        <ep-tag v-if="item.apiType === 'PULL'" type="warning">PULL</ep-tag>
                        <ep-tag v-if="item.apiType === 'DELETE'" type="danger">DELETE</ep-tag>
                    </div>
                    <div style="display: flex;align-items: center;margin-left: 10px;">
                        {{item.apiUrl}}
                    </div>
                </div>

                <ep-divider v-if="key !== apiModules.length - 1"></ep-divider>
            </div>
        </div>

        <!-- 用例 -->
        <div v-if="!variable.isEmpty(module) && !loading">
            <api-test-case :module="module"></api-test-case>
        </div>

        <!-- 保存弹框 -->
        <ep-modal :title="saveApiModule.id === null ? '新增' : '编辑'" v-model="saveApiModulePopup.show" width="600px" :close-on-press-escape="false" :wrap-close="false">
            <div>
                <input-label name="名称" v-model="saveApiModule.name"></input-label>

                <input-label style="margin-top: 20px;" name="接口地址" v-model="saveApiModule.apiUrl"></input-label>

                <input-label style="margin-top: 20px;" name="请求方式">
                    <div>
                        <ep-select placeholder="请选择" v-model="saveApiModule.apiType">
                            <ep-select-item index="POST" label="POST">
                                <ep-tag type="primary">POST</ep-tag>
                            </ep-select-item>
                            <ep-select-item index="GET" label="GET">
                                <ep-tag type="success">GET</ep-tag>
                            </ep-select-item>
                            <ep-select-item index="PULL" label="PULL">
                                <ep-tag type="warning">PULL</ep-tag>
                            </ep-select-item>
                            <ep-select-item index="DELETE" label="DELETE">
                                <ep-tag type="danger">DELETE</ep-tag>
                            </ep-select-item>
                        </ep-select>
                    </div>
                </input-label>

                <ep-button @click="() => {
                    saveApiModuleApi().then(() => {
                        alterUtil.success('完成');
                        saveApiModulePopup.close();
                        init();
                    }).catch((m) => {
                        alterUtil.error(m);
                    });
                }" style="width: 100%;margin-top: 20px;" type="primary">确定</ep-button>
            </div>
        </ep-modal>

        <!-- 变量维护弹框 -->
        <ep-modal title="变量维护" v-model="apiVariablePopup.show" width="600px" :close-on-press-escape="false" :wrap-close="false">
            <div v-loading="apiVariablePopup.loading" style="max-height: 700px;overflow: auto;" class="scrollbar">
                <div>
                    <ep-button @click="() => {
                        saveApiVariable.data.push({
                            code: '',
                            data: ''
                        });
                    }" type="success" size="small">新增</ep-button>
                </div>

                <div style="display: flex;width: 100%;margin-top: 20px;margin-bottom: 10px;">
                    <div style="width: 45%;padding: 0 5px;display: flex;align-items: center;">
                        code
                    </div>
                    <div style="width: 45%;padding: 0 5px;display: flex;align-items: center;">
                        data
                    </div>
                    <div style="width: 10%;padding: 0 5px;display: flex;align-items: center;"></div>
                </div>

                <div v-for="(item, key) in saveApiVariable.data" :key="key" style="display: flex;width: 100%;">
                    <div style="width: 45%;padding: 0 5px;display: flex;align-items: center;">
                        <ep-input v-model="item.code" size="small"></ep-input>
                    </div>
                    <div style="width: 45%;padding: 0 5px;display: flex;align-items: center;">
                        <ep-input v-model="item.data" size="small"></ep-input>
                    </div>
                    <div style="width: 10%;padding: 0 5px;display: flex;align-items: center;">
                        <ep-button @click="() => {
                            saveApiVariable.data.splice(key, 1);
                        }" type="text" style="color: #FF4D4F;">删除</ep-button>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button @click="() => {
                        saveApiVariableApi().then(() => {
                            alterUtil.success('完成');
                            apiVariablePopup.show = false;
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    }" type="primary" size="small" style="width: 100%;">保存</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import ApiModuleApi from "../api/ApiModuleApi.js";
    import alterUtil from "../util/alterUtil.js";
    import apiTestCase from "./apiTest/apiTestCase.vue";
    import apiVariableApi from "../api/apiVariableApi.js";
    import commonUtil from "zj0724-common/src/index.js";

    export default {
        name: "apiTest.vue",

        data() {
            let current = this;

            return {
                loading: false,

                module: "",

                apiModules: [],

                saveApiModule: {
                    id: null,
                    name: null,
                    apiUrl: null,
                    apiType: null
                },

                deleteApiModule: {
                    id: null
                },

                // 查询变量
                queryApiVariable: {
                    filter: {
                        apiModuleId: null
                    }
                },

                // 保存变量
                saveApiVariable: {
                    urlData: {
                        apiModuleId: null,
                    },
                    data: []
                },

                saveApiModulePopup: {
                    show: false,

                    open(data) {
                        this.show = true;
                        if (data === null) {
                            commonUtil.variable.clean(current.saveApiModule);
                        } else {
                            commonUtil.variable.assignment(current.saveApiModule, data);
                        }
                    },

                    close() {
                        this.show = false;
                    }
                },

                apiVariablePopup: {
                    show: false,

                    loading: false,

                    async open(apiModuleId) {
                        this.show = true;
                        this.loading = true;
                        current.queryApiVariable.filter.apiModuleId = apiModuleId;
                        current.saveApiVariable.urlData.apiModuleId = apiModuleId;
                        await current.queryApiVariablesApi();
                        this.loading = false;
                    }
                },

                alterUtil,

                variable: commonUtil.variable
            };
        },

        methods: {
            async init() {
                this.loading = true;
                await this.getApiModulesApi();
                this.module = this.$route.query.module;
                this.loading = false;
            },

            async getApiModulesApi() {
                await ApiModuleApi.getAll().then((data) => {
                    this.apiModules = data;
                });
            },

            async saveApiModuleApi() {
                return await ApiModuleApi.save(this.saveApiModule).catch((m) => {
                    return Promise.reject(m);
                });
            },

            async deleteApiModuleApi() {
                return await ApiModuleApi.delete(this.deleteApiModule).catch((m) => {
                    return Promise.reject(m);
                });
            },

            async queryApiVariablesApi() {
                await apiVariableApi.query(this.queryApiVariable).then((data) => {
                    this.saveApiVariable.data = data.data;
                });
            },

            async saveApiVariableApi() {
                return await apiVariableApi.save(this.saveApiVariable.urlData, this.saveApiVariable.data).catch((m) => {
                    return Promise.reject(m);
                });
            },

            _moduleAction(module) {
                try {
                    this.$router.push({
                        query: {
                            module: module
                        }
                    });
                    this.init();
                } catch (e) {}
            }
        },

        created() {
            this.init();
        },

        components: {
            apiTestCase
        }
    }
</script>

<style scoped>
    .module:hover {
        text-decoration: underline;
        cursor: pointer;
    }
</style>