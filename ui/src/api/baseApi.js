import commonUtil from "../util/commonUtil.js";
import baseConfig from "../config/baseConfig.js";

export default {
    send(data) {
        return new Promise((successCallback, errorCallback) => {
            data.url = baseConfig.server + data.url;
            if (data.type === "POST") {
                if (data.header === undefined) {
                    data.header = {};
                }
                data.header["content-type"] = "application/json";
            }
            commonUtil.http.send(data).then((response) => {
                let responseJson = JSON.parse(response.toString());
                let flag = responseJson.flag;
                // let errorCode = responseJson.errorCode;
                let errorMessage = responseJson.errorMessage;
                let data = responseJson.data;

                if (flag) {
                    successCallback(data);
                } else {
                    errorCallback(errorMessage);
                }
            });
        });
    },

    download(data) {
        return new Promise((successCallback, errorCallback) => {
            commonUtil.http.download(data).then(() => {
                successCallback();
            }).catch((response) => {
                let responseJson = JSON.parse(response.toString());
                let errorMessage = responseJson.errorMessage;
                errorCallback(errorMessage);
            });
        });
    }
};