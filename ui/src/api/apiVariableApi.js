import baseApi from "./baseApi.js";

const u = "/apiVariable";

export default {
    query(data) {
        return baseApi.send({
            url: `${u}/query`,
            type: "POST",
            data: data
        });
    },

    save(urlData, data) {
        return baseApi.send({
            url: `${u}/save`,
            type: "POST",
            urlData: urlData,
            data: data
        });
    }
};