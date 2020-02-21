package com.gsww.baselibs.net.upload;

public class FileUploadModel {


    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * relationId : 190225142406440a8b4a0cbb89be0f53
         * bizId : 5eed5bf5959d4702a3288346049ebd48
         * bizType : 6902
         * fileName : _0ServerSendToService.txt
         * fileMime : txt
         * fileSize : 54486
         * fileUrl : http://10.18.28.20:9093/hdfp-basic/fileRelation/download?rid=190225142406440a8b4a0cbb89be0f53
         * filePath :
         * orgId :
         * createTime : 2019-11-27 12:04:06
         * userId :
         * exdId : null
         * dataYear : null
         */

        private String relationId;
        private String bizId;
        private String bizType;
        private String fileName;
        private String fileMime;
        private int fileSize;
        private String fileUrl;
        private String filePath;
        private String orgId;
        private String createTime;
        private String userId;
        private Object exdId;
        private Object dataYear;

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public String getBizId() {
            return bizId;
        }

        public void setBizId(String bizId) {
            this.bizId = bizId;
        }

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileMime() {
            return fileMime;
        }

        public void setFileMime(String fileMime) {
            this.fileMime = fileMime;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getExdId() {
            return exdId;
        }

        public void setExdId(Object exdId) {
            this.exdId = exdId;
        }

        public Object getDataYear() {
            return dataYear;
        }

        public void setDataYear(Object dataYear) {
            this.dataYear = dataYear;
        }
    }
}
