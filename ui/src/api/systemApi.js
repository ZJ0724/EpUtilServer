import baseApi from "./baseApi.js";

const u = "/system";

export default {
    exportDatabase() {
        return baseApi.download({
            url: `${u}/exportDatabase`,
            type: "GET"
        });
    },

    importDatabase(data) {
        return baseApi.send({
            url: `${u}/importDatabase`,
            type: "POST",
            data: data
        });
    }
};