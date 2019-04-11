package edu.bsu.cs;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.stream.Stream;

//sorts parsed data into a result
public class WeatherFormatter {
    /*
    takes a HashMap of one forecast and returns a readable string foremat
     */
    public String simpleFormat(HashMap<String, String> forecast) {
        String result;
        result = forecast.get("name") + "\n";
        result += "Current Weather\n";
        result += forecast.get("temperature") + forecast.get("temperatureUnit") + "\n";
        result += forecast.get("shortForecast");

        return result;
    }

    public String titleCaseConversion(String inputString)
    {
        if (StringUtils.isBlank(inputString)) {
            return "";
        }

        if (StringUtils.length(inputString) == 1) {
            return inputString.toUpperCase();
        }

        StringBuffer resultPlaceHolder = new StringBuffer(inputString.length());

        Stream.of(inputString.split(" ")).forEach(stringPart ->
        {
            if (stringPart.length() > 1)
                resultPlaceHolder.append(stringPart.substring(0, 1)
                        .toUpperCase())
                        .append(stringPart.substring(1)
                                .toLowerCase());
            else
                resultPlaceHolder.append(stringPart.toUpperCase());

            resultPlaceHolder.append(" ");
        });
        return StringUtils.trim(resultPlaceHolder.toString());
    }
}
