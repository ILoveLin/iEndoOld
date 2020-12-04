package org.company.iendo.bean;

import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 获取当前机器的userid
 */
public class LiveConnectIDBean {


    private List<DsDTO> ds;

    public List<DsDTO> getDs() {
        return ds;
    }

    public void setDs(List<DsDTO> ds) {
        this.ds = ds;
    }

    public static class DsDTO {
        /**
         * ID : 616
         * Name : selectRecored
         * Param : 4045
         * CreatedAt : 0
         * StartedAt : 0
         * EndedAt : 0
         * ReturnValue :
         * WaitStartTimeSec : 5
         * WaitExecTimeSec : 5
         * Progress : 0
         * Result : 1
         * Message : 命令执行成功！
         * ExecuteTimeMs : 0
         */

        private String ID;
        private String Name;
        private String Param;
        private String CreatedAt;
        private String StartedAt;
        private String EndedAt;
        private String ReturnValue;
        private String WaitStartTimeSec;
        private String WaitExecTimeSec;
        private String Progress;
        private String Result;
        private String Message;
        private String ExecuteTimeMs;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getParam() {
            return Param;
        }

        public void setParam(String Param) {
            this.Param = Param;
        }

        public String getCreatedAt() {
            return CreatedAt;
        }

        public void setCreatedAt(String CreatedAt) {
            this.CreatedAt = CreatedAt;
        }

        public String getStartedAt() {
            return StartedAt;
        }

        public void setStartedAt(String StartedAt) {
            this.StartedAt = StartedAt;
        }

        public String getEndedAt() {
            return EndedAt;
        }

        public void setEndedAt(String EndedAt) {
            this.EndedAt = EndedAt;
        }

        public String getReturnValue() {
            return ReturnValue;
        }

        public void setReturnValue(String ReturnValue) {
            this.ReturnValue = ReturnValue;
        }

        public String getWaitStartTimeSec() {
            return WaitStartTimeSec;
        }

        public void setWaitStartTimeSec(String WaitStartTimeSec) {
            this.WaitStartTimeSec = WaitStartTimeSec;
        }

        public String getWaitExecTimeSec() {
            return WaitExecTimeSec;
        }

        public void setWaitExecTimeSec(String WaitExecTimeSec) {
            this.WaitExecTimeSec = WaitExecTimeSec;
        }

        public String getProgress() {
            return Progress;
        }

        public void setProgress(String Progress) {
            this.Progress = Progress;
        }

        public String getResult() {
            return Result;
        }

        public void setResult(String Result) {
            this.Result = Result;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getExecuteTimeMs() {
            return ExecuteTimeMs;
        }

        public void setExecuteTimeMs(String ExecuteTimeMs) {
            this.ExecuteTimeMs = ExecuteTimeMs;
        }
    }
}
