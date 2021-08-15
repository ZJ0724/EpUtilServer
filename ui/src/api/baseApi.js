import commonUtil from "../util/commonUtil.js";
import baseConfig from "../config/baseConfig.js";

export default {
    send(data) {
        return new Promise((successCallback, errorCallback) => {
            data.url = baseConfig.server + data.url;
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
        data.url = baseConfig.server + data.url;
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