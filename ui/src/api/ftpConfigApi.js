import baseApi from "./baseApi.js";

const u = "/ftpConfig";

export default {
    getAll() {
        return baseApi.send({
            url: `${u}/getAll`,
            type: "GET"
        });
    },

    save(data) {
        return baseApi.send({
            url: `${u}/save`,
            type: "POST",
            data
        });
    },

    delete(data) {
        return baseApi.send({
            url: `${u}/delete`,
            type: "POST",
            data
        });
    },

    connect(data) {
        return baseApi.send({
            url: `${u}/connect`,
            type: "POST",
            data
        });
    },

    ls(data) {
        return baseApi.send({
            url: `${u}/ls`,
            type: "POST",
            data: data
        });
    },

    download(data) {
        return baseApi.download({
            url: `${u}/download`,
            type: "GET",
            urlData: data
        });
    }
};