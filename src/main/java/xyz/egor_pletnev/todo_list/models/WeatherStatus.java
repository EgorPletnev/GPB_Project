package xyz.egor_pletnev.todo_list.models;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WeatherStatus { //TODO: добавить коды
    CODE_0(0, "Ясно"),
    CODE_1("Преимущественно ясно"),
    CODE_2("Переменная облачность"),
    CODE_3("Пасмурно"),
    CODE_45("Туман"),
    CODE_48("Изморозь"),
    CODE_51("Слабая морось"),
    CODE_53("Умеренная морось"),
    CODE_55("Плотная морось"),
    CODE_56("Слабая ледяная морось"),
    CODE_57("Плотная ледяная морось"),
    CODE_61("Слабый дождь"),
    CODE_63("Умеренный дождь"),
    CODE_65("Сильный дождь"),
    CODE_66("Слабый ледяной дождь"),
    CODE_67("Сильный ледяной дождь"),
    CODE_71("Слабый снегопад"),
    CODE_73("Умеренный снегопад"),
    CODE_75("Сильный снегопад"),
    CODE_77("Снежные зерна"),
    CODE_80("Слабый ливень"),
    CODE_81("Умеренный ливень"),
    CODE_82("Сильный ливень"),
    CODE_85("Слабый снежный ливень"),
    CODE_86("Сильный снежный ливень"),
    CODE_95("Гроза"),
    CODE_96("Гроза с небольшим градом"),
    CODE_99("Гроза с сильным градом"),
    UNKNOWN("Неизвестно");

    private final int code;
    private final String description;


    @JsonValue
    public String getDescription() {return description;}

    public static WeatherStatus weatherStatusFromCode(int code) {
        for (WeatherStatus value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return WeatherStatus.UNKNOWN;

//        return switch (code) { // TODO: сравнить быстродействие
//            case 0 -> CODE_0;
//            case 1 -> CODE_1;
//            case 2 -> CODE_2;
//            case 3 -> CODE_3;
//            case 45 -> CODE_45;
//            case 48 -> CODE_48;
//            case 51 -> CODE_51;
//            case 53 -> CODE_53;
//            case 55 -> CODE_55;
//            case 56 -> CODE_56;
//            case 57 -> CODE_57;
//            case 61 -> CODE_61;
//            case 63 -> CODE_63;
//            case 65 -> CODE_65;
//            case 66 -> CODE_66;
//            case 67 -> CODE_67;
//            case 71 -> CODE_71;
//            case 73 -> CODE_73;
//            case 75 -> CODE_75;
//            case 77 -> CODE_77;
//            case 80 -> CODE_80;
//            case 81 -> CODE_81;
//            case 82 -> CODE_82;
//            case 85 -> CODE_85;
//            case 86 -> CODE_86;
//            case 95 -> CODE_95;
//            case 96 -> CODE_96;
//            case 99 -> CODE_99;
//            default -> UNKNOWN;
//        };
    }
}