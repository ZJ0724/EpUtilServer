<template>
    <div v-loading="loading">
        <div class="panel">
            <div class="title-2">
                企业内部编号
                <ep-input v-model="upload.copSeqNo" style="margin-top: 20px;"></ep-input>
            </div>

            <div style="display: flex;margin-top: 50px;">
                <div class="title-2" style="display: flex;align-items: center;">
                    通讯回执
                </div>
                <div style="margin-left: 10px;">
                    <ep-checkbox v-model="tongXunDisplay"></ep-checkbox>
                </div>
            </div>

            <div style="margin-top: 20px;">
                code
                <ep-select :disabled="!tongXunDisplay" style="margin-top: 10px;" v-model="upload.tongXun.code">
                    <ep-select-item index="0" label="0 [成功]"></ep-select-item>
                    <ep-select-item index="1" label="1 [失败]"></ep-select-item>
                </ep-select>
            </div>

            <div style="margin-top: 20px;">
                note
                <ep-input :disabled="!tongXunDisplay" v-model="upload.tongXun.note" style="margin-top: 10px;"></ep-input>
            </div>

            <div style="display: flex;margin-top: 50px;">
                <div class="title-2" style="display: flex;align-items: center;">
                    业务回执
                </div>
                <div style="margin-left: 10px;">
                    <ep-checkbox v-model="yeWuDisplay"></ep-checkbox>
                </div>
            </div>

            <div style="margin-top: 20px;">
                code
                <ep-input :disabled="!yeWuDisplay" v-model="upload.yeWu.code" style="margin-top: 10px;"></ep-input>
            </div>

            <div style="margin-top: 20px;">
                note
                <ep-input :disabled="!yeWuDisplay" v-model="upload.yeWu.note" style="margin-top: 10px;"></ep-input>
            </div>

            <div style="margin-top: 50px;">
                <ep-button style="width: 100%;" @click="() => {
                    loading = true;
                    uploadTransResultApi().then(() => {
                        alterUtil.success('完成');
                    }).catch((m) => {
                        alterUtil.error(m);
                    }).finally(() => {
                        loading = false;
                    });
                }" type="primary">上传</ep-button>
            </div>
        </div>
    </div>
</template>

<script>
    import cusResultApi from "../api/cusResultApi.js";
    import {variable} from "../util/zj0724-common-1.0.0.js";
    import alterUtil from "../util/alterUtil.js";

    export default {
        name: "transResult.vue",

        data() {
            return {
                loading: false,

                // 上传
                upload: {
                    copSeqNo: "",
                    tongXun: {
                        code: "",
                        note: ""
                    },
                    yeWu: {
                        code: "",
                        note: ""
                    }
                },

                // 通讯回执监控
                tongXunDisplay: true,

                // 业务回执监控
                yeWuDisplay: true,

                alterUtil
            };
        },

        methods: {
            async uploadTransResultApi() {
                let req = variable.clone(this.upload);
                if (!this.tongXunDisplay) {
                    req.tongXun = null;
                }
                if (!this.yeWuDisplay) {
                    req.yeWu = null;
                }
                return await cusResultApi.uploadTransResult(req).catch((m) => {
                    return Promise.reject(m);
                });
            }
        }
    }
</script>