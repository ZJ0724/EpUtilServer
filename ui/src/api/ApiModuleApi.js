import baseApi from "./baseApi.js";

const u = "/apiModule";

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
            data: data,
            check: {
                data: {
                    id: Number,
                    name: String,
                    apiUrl: String,
                    apiType: String
                }
            }
        });
    },

    delete(data) {
        return baseApi.send({
            url: `${u}/delete`,
            type: "POST",
            data: data,
            check: {
                data: {
                    id: Number
                }
            }
        });
    }
};