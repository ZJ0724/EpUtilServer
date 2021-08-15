<template>
    <div v-loading="loading">
        <div class="panel">
            <ep-button style="width: 100%;" type="primary" @click="() => {
                exportDatabaseApi().then(() => {
                    alterUtil.success('完成');
                }).catch((m) => {
                    alterUtil.error(m);
                });
            }">导出数据库</ep-button>
        </div>

        <div class="panel" style="margin-top: 20px;">
            <ep-button @click="_importDatabaseEvent()" style="width: 100%;" type="primary">导入数据库</ep-button>
        </div>
    </div>
</template>

<script>
    import systemApi from "../api/systemApi.js";
    import alterUtil from "../util/alterUtil.js";
    import commonUtil from "../util/commonUtil.js";

    export default {
        name: "system.vue",

        data() {
            return {
                loading: false,

                importDatabase: {
                    database: null
                },

                alterUtil
            };
        },

        methods: {
            async exportDatabaseApi() {
                return await systemApi.exportDatabase().catch((m) => {
                    return Promise.reject(m);
                });
            },

            async importDatabaseApi() {
                return await systemApi.importDatabase(this.importDatabase).catch((m) => {
                    return Promise.reject(m);
                });
            },

            // 导入事件
            async _importDatabaseEvent() {
                this.loading = true;
                try {
                    await commonUtil.file.select().then(async (file) => {
                        this.importDatabase.database = file;
                        await this.importDatabaseApi().then(() => {
                            alterUtil.success("完成");
                        }).catch((m) => {
                            alterUtil.error(m);
                        });
                    });
                } finally {
                    this.loading = false;
                }
            }
        }
    }
</script>