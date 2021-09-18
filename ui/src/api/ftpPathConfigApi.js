import baseApi from "./baseApi.js";

const u = "/ftpPathConfig";

export default {
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

    query(data) {
        return baseApi.send({
            url: `${u}/query`,
            type: "POST",
            data
        });
    }
};