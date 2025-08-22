package net.bean;

import java.util.List;

public class WeatherInfo {

    private Integer Total;
    private DataDTO Data;
    private Integer Tag;
    private String Message;

    public Integer getTotal() {
        return Total;
    }

    public void setTotal(Integer Total) {
        this.Total = Total;
    }

    public DataDTO getData() {
        return Data;
    }

    public void setData(DataDTO Data) {
        this.Data = Data;
    }

    public Integer getTag() {
        return Tag;
    }

    public void setTag(Integer Tag) {
        this.Tag = Tag;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public static class DataDTO {
        private List<HoursListDTO> hoursList;
        private List<MonthListDTO> monthList;

        public List<HoursListDTO> getHoursList() {
            return hoursList;
        }

        public void setHoursList(List<HoursListDTO> hoursList) {
            this.hoursList = hoursList;
        }

        public List<MonthListDTO> getMonthList() {
            return monthList;
        }

        public void setMonthList(List<MonthListDTO> monthList) {
            this.monthList = monthList;
        }

        public static class HoursListDTO {
            private String server;
            private String FToDayId;
            private String FcloudCover;
            private String FdayOfWeek;
            private String FdayOrNight;
            private String Fhumidity;

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public String getFToDayId() {
                return FToDayId;
            }

            public void setFToDayId(String FToDayId) {
                this.FToDayId = FToDayId;
            }

            public String getFcloudCover() {
                return FcloudCover;
            }

            public void setFcloudCover(String FcloudCover) {
                this.FcloudCover = FcloudCover;
            }

            public String getFdayOfWeek() {
                return FdayOfWeek;
            }

            public void setFdayOfWeek(String FdayOfWeek) {
                this.FdayOfWeek = FdayOfWeek;
            }

            public String getFdayOrNight() {
                return FdayOrNight;
            }

            public void setFdayOrNight(String FdayOrNight) {
                this.FdayOrNight = FdayOrNight;
            }

            public String getFhumidity() {
                return Fhumidity;
            }

            public void setFhumidity(String Fhumidity) {
                this.Fhumidity = Fhumidity;
            }
        }

        public static class MonthListDTO {
            private String server;
            private String FToDayId;
            private String Fdate;
            private String FdateOfWeek;
            private String FmoonIcon;
            private String FmoonPhrase;
            private String Fmoonrise;
            private String Fmoonset;
            private String Fsunrise;
            private String Fsunset;
            private String FdayCloudPct;
            private String FdayNarrative;
            private String FdayPhrase;

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public String getFToDayId() {
                return FToDayId;
            }

            public void setFToDayId(String FToDayId) {
                this.FToDayId = FToDayId;
            }

            public String getFdate() {
                return Fdate;
            }

            public void setFdate(String Fdate) {
                this.Fdate = Fdate;
            }

            public String getFdateOfWeek() {
                return FdateOfWeek;
            }

            public void setFdateOfWeek(String FdateOfWeek) {
                this.FdateOfWeek = FdateOfWeek;
            }

            public String getFmoonIcon() {
                return FmoonIcon;
            }

            public void setFmoonIcon(String FmoonIcon) {
                this.FmoonIcon = FmoonIcon;
            }

            public String getFmoonPhrase() {
                return FmoonPhrase;
            }

            public void setFmoonPhrase(String FmoonPhrase) {
                this.FmoonPhrase = FmoonPhrase;
            }

            public String getFmoonrise() {
                return Fmoonrise;
            }

            public void setFmoonrise(String Fmoonrise) {
                this.Fmoonrise = Fmoonrise;
            }

            public String getFmoonset() {
                return Fmoonset;
            }

            public void setFmoonset(String Fmoonset) {
                this.Fmoonset = Fmoonset;
            }

            public String getFsunrise() {
                return Fsunrise;
            }

            public void setFsunrise(String Fsunrise) {
                this.Fsunrise = Fsunrise;
            }

            public String getFsunset() {
                return Fsunset;
            }

            public void setFsunset(String Fsunset) {
                this.Fsunset = Fsunset;
            }

            public String getFdayCloudPct() {
                return FdayCloudPct;
            }

            public void setFdayCloudPct(String FdayCloudPct) {
                this.FdayCloudPct = FdayCloudPct;
            }

            public String getFdayNarrative() {
                return FdayNarrative;
            }

            public void setFdayNarrative(String FdayNarrative) {
                this.FdayNarrative = FdayNarrative;
            }

            public String getFdayPhrase() {
                return FdayPhrase;
            }

            public void setFdayPhrase(String FdayPhrase) {
                this.FdayPhrase = FdayPhrase;
            }
        }
    }
}
