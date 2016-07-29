package com.example.mayarfareed.sunshineapp;

import java.util.List;

/**
 * Created by Mohammed Fareed on 7/30/2016.
 */
public class OpenWeatherBean {

    /**
     * id : 5375480
     * name : Mountain View
     * coord : {"lon":-122.083847,"lat":37.386051}
     * country : US
     * population : 0
     */

    private CityBean city;
    /**
     * city : {"id":5375480,"name":"Mountain View","coord":{"lon":-122.083847,"lat":37.386051},"country":"US","population":0}
     * cod : 200
     * message : 0.008
     * cnt : 7
     * list : [{"dt":1469822400,"temp":{"day":28.74,"min":14.05,"max":28.74,"night":14.05,"eve":22.76,"morn":25.41},"pressure":986.22,"humidity":65,"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"speed":1.43,"deg":230,"clouds":0},{"dt":1469908800,"temp":{"day":27,"min":11.54,"max":27,"night":11.54,"eve":20.56,"morn":15.75},"pressure":985.75,"humidity":66,"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"speed":1.51,"deg":222,"clouds":0},{"dt":1469995200,"temp":{"day":24.33,"min":11.43,"max":24.34,"night":11.43,"eve":19.3,"morn":13.25},"pressure":987.9,"humidity":69,"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"speed":1.51,"deg":231,"clouds":0},{"dt":1470081600,"temp":{"day":24.69,"min":11.5,"max":24.73,"night":11.5,"eve":19.79,"morn":13.65},"pressure":989.21,"humidity":70,"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"speed":1.51,"deg":238,"clouds":0},{"dt":1470168000,"temp":{"day":18.45,"min":11.69,"max":21.42,"night":14.36,"eve":21.42,"morn":11.69},"pressure":1009.59,"humidity":0,"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"speed":1.43,"deg":263,"clouds":1},{"dt":1470254400,"temp":{"day":18.32,"min":11.21,"max":20.58,"night":13.15,"eve":20.58,"morn":11.21},"pressure":1008.82,"humidity":0,"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"speed":1.64,"deg":264,"clouds":0},{"dt":1470340800,"temp":{"day":17.08,"min":9.96,"max":20.27,"night":13.36,"eve":20.27,"morn":9.96},"pressure":1007.69,"humidity":0,"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"speed":2.36,"deg":298,"clouds":40}]
     */

    private String cod;
    private double message;
    private int cnt;
    /**
     * dt : 1469822400
     * temp : {"day":28.74,"min":14.05,"max":28.74,"night":14.05,"eve":22.76,"morn":25.41}
     * pressure : 986.22
     * humidity : 65
     * weather : [{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}]
     * speed : 1.43
     * deg : 230
     * clouds : 0
     */

    private List<ListBean> list;

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class CityBean {
        private int id;
        private String name;
        /**
         * lon : -122.083847
         * lat : 37.386051
         */

        private CoordBean coord;
        private String country;
        private int population;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public CoordBean getCoord() {
            return coord;
        }

        public void setCoord(CoordBean coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public static class CoordBean {
            private double lon;
            private double lat;

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }
    }

    public static class ListBean {
        private int dt;
        /**
         * day : 28.74
         * min : 14.05
         * max : 28.74
         * night : 14.05
         * eve : 22.76
         * morn : 25.41
         */

        private TempBean temp;
        private double pressure;
        private int humidity;
        private double speed;
        private int deg;
        private int clouds;
        /**
         * id : 800
         * main : Clear
         * description : clear sky
         * icon : 01d
         */

        private List<WeatherBean> weather;

        public int getDt() {
            return dt;
        }

        public void setDt(int dt) {
            this.dt = dt;
        }

        public TempBean getTemp() {
            return temp;
        }

        public void setTemp(TempBean temp) {
            this.temp = temp;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public int getClouds() {
            return clouds;
        }

        public void setClouds(int clouds) {
            this.clouds = clouds;
        }

        public List<WeatherBean> getWeather() {
            return weather;
        }

        public void setWeather(List<WeatherBean> weather) {
            this.weather = weather;
        }

        public static class TempBean {
            private double day;
            private double min;
            private double max;
            private double night;
            private double eve;
            private double morn;

            public double getDay() {
                return day;
            }

            public void setDay(double day) {
                this.day = day;
            }

            public double getMin() {
                return min;
            }

            public void setMin(double min) {
                this.min = min;
            }

            public double getMax() {
                return max;
            }

            public void setMax(double max) {
                this.max = max;
            }

            public double getNight() {
                return night;
            }

            public void setNight(double night) {
                this.night = night;
            }

            public double getEve() {
                return eve;
            }

            public void setEve(double eve) {
                this.eve = eve;
            }

            public double getMorn() {
                return morn;
            }

            public void setMorn(double morn) {
                this.morn = morn;
            }
        }

        public static class WeatherBean {
            private int id;
            private String main;
            private String description;
            private String icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}
