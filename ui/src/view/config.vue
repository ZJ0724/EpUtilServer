<template>
    <div v-loading="loading">
        <div class="panel">
            <ep-tabs v-model="selectGroup" layout="left">
                <ep-tab-item v-for="(item, key) in configs" :key="key" :name="item.group" :label="item.groupName">
                    <div style="padding: 10px;">
                        <div class="title">
                            {{item.groupName}}
                        </div>

                        <div v-for="(i, k) in item.codes" :key="k" style="margin-top: 20px;">
                            <div>
                                {{i.codeName}}
                            </div>
                            <div style="margin-top: 10px;">
                                <ep-input v-model="i.data"></ep-input>
                            </div>
                        </div>

                        <div style="margin-top: 20px;">
                            <ep-button @click="() => {
                                loading = true;
                                save = item.codes;
                                saveApi().then(() => {
                                    alterUtil.success('完成');
                                    getConfigApi();
                                }).catch((m) => {
                                    alterUtil.error(m);
                                }).finally(() => {
                                    loading = false;
                                });
                            }" style="width: 100%;" type="success">保存</ep-button>
                        </div>
                    </div>
                </ep-tab-item>
            </ep-tabs>
        </div>
    </div>
</template>

<script>
    import configApi from "../api/configApi.js";
    import alterUtil from "../util/alterUtil.js";

    export default {
        name: "config.vue",

        data() {
            return {
                loading: false,

                selectGroup: null,

                configs: [
                    // {
                    //     group: "",
                    //     groupName: "",
                    //     codes: []
                    // }
                ],

                save: [],

                alterUtil
            };
        },

        methods: {
            async getConfigApi() {
                await configApi.getAll().then((data) => {
                    this.configs = [];
                    for (let item of data) {
                        let group = {
                            group: item.group,
                            groupName: item.groupName,
                            codes: []
                        };
                        let isPush = true;
                        for (let c of this.configs) {
                            if (c.group === item.group) {
                                group = c;
                                isPush = false;
                                break;
                            }
                        }
                        group.codes.push(item);
                        if (isPush) {
                            this.configs.push(group);
                        }
                    }
                });
            },

            async saveApi() {
                return await configApi.save(this.save).catch((m) => {
                    return Promise.reject(m);
                });
            },

            saveAction() {
                this.configUpdatePopup.loading = true;
                this.saveApi().then(() => {
                    alterUtil.success("成功");
                    this.configUpdatePopup.show = false;
                    this.getConfigApi();
                }).catch((m) => {
                    alterUtil.error(m);
                }).finally(() => {
                    this.configUpdatePopup.loading = false;
                });
            }
        },

        async created() {
            this.loading = true;
            await this.getConfigApi();
            this.selectGroup = this.configs[0].group;
            this.loading = false;
        }
    }
</script>