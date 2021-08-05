import baseApi from "./baseApi.js";

const u = "/cusResult";

export default {
    uploadCustomsDeclaration(data) {
        return baseApi.send({
            url: `${u}/uploadCustomsDeclaration`,
            type: "POST",
            data: data,
            check: {
                data: {
                    customsDeclarationNumber: String,
                    tongXunCusResult: {
                        code: String,
                        note: String
                    },
                    yeWuCusResult: {
                        code: String,
                        note: String
                    }
                }
            }
        });
    },

    uploadAgentResult(data) {
        return baseApi.send({
            url: `${u}/uploadAgentResult`,
            type: "POST",
            data: data,
            check: {
                data: {
                    customsDeclarationNumber: String,
                    cusResult: {
                        code: String,
                        note: String
                    }
                }
            }
        });
    },

    uploadTransResult(data) {
        return baseApi.send({
            url: `${u}/uploadTransResult`,
            type: "POST",
            data: data,
            check: {
                data: {
                    copSeqNo: String,
                    tongXun: {
                        code: String,
                        note: String
                    },
                    yeWu: {
                        code: String,
                        note: String
                    }
                }
            }
        });
    }
};