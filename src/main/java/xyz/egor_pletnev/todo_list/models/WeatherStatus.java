package xyz.egor_pletnev.todo_list.models;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WeatherStatus {
    CODE_0(0, "Ясно"),
    CODE_1(1, "Преимущественно ясно"),
    CODE_2(2, "Переменная облачность"),
    CODE_3(3, "Пасмурно"),
    CODE_45(45, "Туман"),
    CODE_48(48, "Изморозь"),
    CODE_51(51, "Слабая морось"),
    CODE_53(53, "Умеренная морось"),
    CODE_55(55, "Плотная морось"),
    CODE_56(56, "Слабая ледяная морось"),
    CODE_57(57, "Плотная ледяная морось"),
    CODE_61(61, "Слабый дождь"),
    CODE_63(63, "Умеренный дождь"),
    CODE_65(65, "Сильный дождь"),
    CODE_66(66, "Слабый ледяной дождь"),
    CODE_67(67, "Сильный ледяной дождь"),
    CODE_71(71, "Слабый снегопад"),
    CODE_73(73, "Умеренный снегопад"),
    CODE_75(75, "Сильный снегопад"),
    CODE_77(77, "Снежные зерна"),
    CODE_80(80, "Слабый ливень"),
    CODE_81(81, "Умеренный ливень"),
    CODE_82(82, "Сильный ливень"),
    CODE_85(85, "Слабый снежный ливень"),
    CODE_86(86, "Сильный снежный ливень"),
    CODE_95(95, "Гроза"),
    CODE_96(96, "Гроза с небольшим градом"),
    CODE_99(99, "Гроза с сильным градом"),
    UNKNOWN(-1, "Неизвестно");

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